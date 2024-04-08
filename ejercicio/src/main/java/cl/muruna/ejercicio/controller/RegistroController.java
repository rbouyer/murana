package cl.muruna.ejercicio.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.muruna.ejercicio.dto.ErrorDTO;
import cl.muruna.ejercicio.model.User;
import cl.muruna.ejercicio.service.LoginService;

@RestController
public class RegistroController {

	@Autowired LoginService loginSvc;

	@GetMapping("/api/v1/hello")
	public String hello() {
		return "Hello Muruna!";
	}   

	@PostMapping("/api/v1/registro")
	public ResponseEntity<Object> registrar(@RequestBody User newUser) {
		Object result = null;
		HttpStatus status = HttpStatus.OK;
		
		try {
			String error = loginSvc.validarRegistro(newUser);
			User createdUser = null;
			
			if(error == null) {
				createdUser = loginSvc.saveNewUser(newUser);
				result = createdUser;
			} else {
				status = HttpStatus.BAD_REQUEST;
				result = new ErrorDTO(error);
			}
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			result = new ErrorDTO(status.getReasonPhrase());
		}
	   
		return ResponseEntity.status(status).body(result);
	}

}
