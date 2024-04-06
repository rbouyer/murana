package cl.muruna.ejercicio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.muruna.ejercicio.model.User;
import cl.muruna.ejercicio.repository.UserRepository;
import cl.muruna.ejercicio.util.JwtUtil;

@Service
public class LoginServiceImpl implements LoginService {
	private String secret;

	@Autowired private UserRepository userRepository;
	
	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secret = secret;
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
		newUser.setLastLogin(LocalDateTime.now());
		newUser.setToken(JwtUtil.generateToken(newUser.getEmail(), secret));

		if(newUser.getPhones() != null && newUser.getPhones().size() > 0) {
			newUser.getPhones().forEach(p -> {
				p.setUser(newUser);
			});
		}
		
		savedUser = userRepository.save(newUser);
				
		return savedUser;
	}

	public User login(String token) {
		User user = this.readUserByToken(token), savedUser = null;
		
		if(user != null) {
			//... el token debe cambiar al ejecutar por lo que se actualizará el token
			user.setToken(JwtUtil.generateToken(user.getEmail(), secret));
			savedUser = userRepository.save(user);
		}
		
		return savedUser;
	}

	/*
	 * Validaciones Sign-up
	 */
	public String validateSignUp(User user) {
		List<String> errores = new ArrayList<String>();
		
		// email obligatorio, formato sea el correcto. (aaaaaaa@undominio.algo)
		if(user.getEmail() == null || user.getEmail().isEmpty() || !validateEmail(user.getEmail())) errores.add("Correo invalido");
		
		// Se valida existencia de usuario por email solo si este es valido
		if(errores.size() == 0) {
			User exist = readUserByEmail(user.getEmail());
			if(exist != null) errores.add("El correo ya registrado");
		}
		
		// Debe tener solo una Mayúscula y solamente dos números (no necesariamente	consecutivos), en combinación de letras minúsculas, largo máximo de 12 y mínimo 8 
		if(user.getPassword() == null || user.getPassword().isEmpty() || !validatePassword(user.getPassword())) errores.add("Invalid password");
		
		return errores.toString();
	}
	
	private boolean validatePassword(String password) {
		boolean isValid = false;
		String re = "^[a-zA-Z0-9]+$"; // Sólo letras (min/may) y dígitos
		
		//Se valida formato:
		isValid = re.matches(re);
		
		//Se valida solo una mayúscula
		if(!isValid) isValid = password.chars().filter(ch -> ch >= 'A' && ch <= 'Z' ).count() == 1; 
		
		//Se valida solamente 2 dígitos:
		if(!isValid) isValid = password.chars().filter(ch -> ch >= '0' && ch <= '9' ).count() == 2; 
		
		return isValid;
	}
	
	private boolean validateEmail(String email) {
		boolean isValid = false;
		String re = "^[a-zA-Z]+@[a-zA-Z]+[.][a-zA-Z]+$"; //formato sea el correcto. (aaaaaaa@undominio.algo)
		
		//Se valida formato
		isValid = email.matches(re);
		
		return isValid;
	}

}
