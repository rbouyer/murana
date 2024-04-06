package cl.muruna.ejercicio.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.muruna.ejercicio.model.Phone;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, UUID> {

}
