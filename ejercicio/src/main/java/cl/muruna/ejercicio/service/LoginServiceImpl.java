package cl.muruna.ejercicio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.muruna.ejercicio.model.User;
import cl.muruna.ejercicio.repository.PhoneRepository;
import cl.muruna.ejercicio.repository.UserRepository;
import cl.muruna.ejercicio.util.JwtUtil;

@Service
public class LoginServiceImpl implements LoginService {
	private String secret;
	private String reClave;

	@Autowired private UserRepository userRepository;
	@Autowired private PhoneRepository phoneRepository;
	
	
	
	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	@Value("${re.password}")
	public void setReClavet(String _reClave) {
		this.reClave = _reClave;
	}

	public User readUserByEmail(String email) {
		User user = null;
		
		user = userRepository.findByEmailIgnoreCase(email);
		
		return user;
	}


	public User readUserByToken(String token) {
		User user = null;
		
		user = userRepository.findByToken(token);
		
		return user;
	}
	
	
	public User saveNewUser(User newUser) {
		User savedUser = null;
		
		newUser.setIsActive(true);
		newUser.setCreated(LocalDateTime.now());
		newUser.setModified(newUser.getCreated());
		newUser.setToken(JwtUtil.generateToken(newUser.getEmail(), secret));
		
		savedUser = userRepository.save(newUser);

		if(newUser.getPhones() != null && newUser.getPhones().size() > 0) {
			newUser.getPhones().forEach(p -> {
				p.setUser(newUser);
				phoneRepository.save(p);
			});
		}
				
		return savedUser;
	}

	/*
	 * Validaciones Sign-up
	 */
	public String validarRegistro(User user) {
		List<String> errores = new ArrayList<String>();
		
		// email obligatorio, formato sea el correcto. (aaaaaaa@undominio.algo)
		if(user.getEmail() == null || user.getEmail().isEmpty() || !validarCorreo(user.getEmail())) errores.add("Correo invalido");
		
		// Formato clave es parametrizado por e.r. obtenida de archivo configuración application.properties 
		if(user.getPassword() == null || user.getPassword().isEmpty() || !validarClave(user.getPassword())) errores.add("Clave invalida");
		
		// Se valida existencia de usuario por email solo si este es valido
		if(errores.size() == 0) {
			User exist = readUserByEmail(user.getEmail());
			if(exist != null) errores.add("El correo ya registrado");
		}
		
		return errores.size() != 0? errores.toString(): null;
	}
	
	private boolean validarClave(String password) {
		boolean isValid = false;
		String re = reClave; // "^[a-zA-Z0-9]{8,12}$"; // Sólo letras (min/may) y dígitos (8-12)
		
		//Se valida formato:
		isValid = password.matches(re);
				
		return isValid;
	}
	
	private boolean validarCorreo(String email) {
		boolean isValid = false;
		String re = "^[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+$"; //formato sea el correcto. (aaaaaaa@undominio.algo)
		
		//Se valida formato
		isValid = email.matches(re);
		
		return isValid;
	}

}
