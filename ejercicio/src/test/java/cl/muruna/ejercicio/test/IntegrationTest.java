package cl.muruna.ejercicio.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cl.muruna.ejercicio.App;
import cl.muruna.ejercicio.service.LoginService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
public class IntegrationTest {
	String userSignupOk = "{ \"name\": \"Ricardo Bouyer\", \"email\": \"rbouyer@gmail.com\", \"password\": \"a2asfGfdfdf4\", \"phones\": [ { \"number\": 93305329, \"citycode\": 9, \"contrycode\": \"56\" } ] }";
	String userSignupInvalidEmail = "{ \"name\": \"Ricardo Bouyer\", \"email\": \"rbouyer@gmailcom\", \"password\": \"a2asfGfdfdf4\", \"phones\": [ { \"number\": 93305329, \"citycode\": 9, \"contrycode\": \"56\" } ] }";
	String userSignupInvalidPassword = "{ \"name\": \"Ricardo Bouyer\", \"email\": \"rbouyer@gmail.com\", \"password\": \"abc123\", \"phones\": [ { \"number\": 93305329, \"citycode\": 9, \"contrycode\": \"56\" } ] }";
	String userSignupInvalidUserExist = "{ \"name\": \"John Smith\", \"email\": \"jsmith@gmail.com\", \"password\": \"a2asfGfdfdf4\", \"phones\": [ { \"number\": 93305329, \"citycode\": 9, \"contrycode\": \"56\" } ] }";

    @Autowired private MockMvc mvc;
    
    @Autowired private LoginService loginSvc;

    @Test
    public void helloTest() throws Exception {
        mvc.perform(get("/api/v1/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello Muruna!"));
    }

    @Test
    public void signupOkTest() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/registro")
				.accept(MediaType.APPLICATION_JSON).content(userSignupOk)
				.contentType(MediaType.APPLICATION_JSON);
    	
    	mvc.perform(requestBuilder)
        .andExpect(status().isOk());
    }

    @Test
    public void signupInvalidEmailTest() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/registro")
				.accept(MediaType.APPLICATION_JSON).content(userSignupInvalidEmail)
				.contentType(MediaType.APPLICATION_JSON);
    	
    	mvc.perform(requestBuilder)
        .andExpect(status().isBadRequest());
    }
    

    @Test
    public void signupInvalidPasswordTest() throws Exception {
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/v1/registro")
				.accept(MediaType.APPLICATION_JSON).content(userSignupInvalidPassword)
				.contentType(MediaType.APPLICATION_JSON);
    	
    	mvc.perform(requestBuilder)
        .andExpect(status().isBadRequest());
    }
 
}
