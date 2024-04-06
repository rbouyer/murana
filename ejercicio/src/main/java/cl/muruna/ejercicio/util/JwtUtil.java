package cl.muruna.ejercicio.util;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	private static final Duration JWT_TOKEN_VALIDITY = Duration.ofSeconds(1);
	private static final String JWT_ISSUER = "GlobalLogic";
	private static final String JWT_ID = "1";
	
//    public JwtUtil(@Value("${jwt.secret}") final String secret) {
//        hmac512 = Algorithm.HMAC512(secret);
//    }

    public static String generateToken(final String subject, final String secret) {
    	//The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

         byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(JWT_ID)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(JWT_ISSUER)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (JWT_TOKEN_VALIDITY.toMillis() >= 0) {
            long expMillis = nowMillis + JWT_TOKEN_VALIDITY.toMillis();
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

}
