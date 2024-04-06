package cl.muruna.ejercicio.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.muruna.ejercicio.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

	User findByEmailIgnoreCase(String email);

	User findByToken(String token);

}
