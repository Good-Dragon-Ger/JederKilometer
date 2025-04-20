package de.gooddragon.jederkilometer.adapter.database.repository;

import de.gooddragon.jederkilometer.adapter.database.entity.Users;
import de.gooddragon.jederkilometer.adapter.database.repository.crud.SpringDataUserRepository;
import de.gooddragon.jederkilometer.application.service.repository.UserRepository;
import de.gooddragon.jederkilometer.domain.model.Sportler;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository repository;

    public UserRepositoryImpl(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Sportler> findAll() {
        List<Users> users = repository.findAll();
        return users.stream().map(this::convertUser).toList();
    }

    @Override
    public Sportler findByUsername(String username) {
        return repository.findByUsername(username).map(this::convertUser).orElse(null);
    }

    @Override
    public Sportler save(Sportler sportler) {
        Long id = repository.findByUuid(sportler.getId()).map(Users::id).orElse(null);
        Users user = new Users(id, sportler.getId(), sportler.getUserName(), sportler.getName(), sportler.getTeam());
        Users savedUser = repository.save(user);
        return convertUser(savedUser);
    }

    @Override
    public Sportler findByUuid(UUID id) {
        return repository.findByUuid(id).map(this::convertUser).orElse(null);
    }

    private Sportler convertUser(Users users) {
        return new Sportler(users.uuid(), users.username(), users.name(), users.team(), new HashSet<>());
    }
}
