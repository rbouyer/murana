package cl.muruna.ejercicio.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cl.muruna.ejercicio.App;
import cl.muruna.ejercicio.model.User;
import cl.muruna.ejercicio.service.LoginServiceImpl;

@SpringBootTest(classes = App.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UnitTest {

	@Autowired LoginServiceImpl loginSvc;
	
	@Test
	public void testValida_Ok() {
		User usr = new User("Ricardo Bouyer", "rbouyer@gmail.com", "12345678", null, LocalDateTime.now(), true);
		String res = loginSvc.validarRegistro(usr);
		
		assertNull(res);
	}
	
	
	@Test
	public void testValida_ErrorClaveCorta() {
		User usr = new User("Juan Rodriguez", "juan@rodriguez.org", "123", null, LocalDateTime.now(), true);
		String res = loginSvc.validarRegistro(usr);
		
		assertNotNull(res);
		assertEquals(res, "[Clave invalida]");
	}
	
	@Test
	public void testValida_ErrorClaveLarga() {
		User usr = new User("Juan Rodriguez", "juan@rodriguez.org", "1234567890123", null, LocalDateTime.now(), true);
		String res = loginSvc.validarRegistro(usr);
		
		assertNotNull(res);
		assertEquals(res, "[Clave invalida]");
	}
	
	@Test
	public void testValida_ErrorClaveCaracteres() {
		User usr = new User("Juan Rodriguez", "juan@rodriguez.org", "12#456789", null, LocalDateTime.now(), true);
		String res = loginSvc.validarRegistro(usr);
		
		assertNotNull(res);
		assertEquals(res, "[Clave invalida]");
	}
	
	@Test
	public void testValida_ErrorEmail() {
		User usr = new User("Juan Rodriguez", "juan&rodriguez.org", "123456789", null, LocalDateTime.now(), true);
		String res = loginSvc.validarRegistro(usr);
		
		assertNotNull(res);
		assertEquals(res, "[Correo invalido]");
	}
	
	@Test
	public void testValida_ErrorUsuarioExiste() {
		User usr = new User("Ricardo Bouyer", "rbouyer@gmail.com", "12345678", null, LocalDateTime.now(), true);
		User newUsr = loginSvc.saveNewUser(usr);
		String res = loginSvc.validarRegistro(usr); //se validan datos de registro paa un usuario existente
		
		assertNotNull(newUsr);
		assertNotNull(res);
		assertEquals(res, "[El correo ya registrado]");
		
	}

}
