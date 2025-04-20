package de.gooddragon.jederkilometer.adapter.database.repository.crud;

import de.gooddragon.jederkilometer.adapter.database.entity.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends CrudRepository<Users, Long> {

    List<Users> findAll();

    Optional<Users> findByUsername(String username);

    Optional<Users> findByUuid(UUID id);
}
