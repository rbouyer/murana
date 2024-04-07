package cl.muruna.ejercicio.service;


import cl.muruna.ejercicio.model.User;

public interface LoginService {
	User saveNewUser(User newUser);
	String validarRegistro(User user);
}
