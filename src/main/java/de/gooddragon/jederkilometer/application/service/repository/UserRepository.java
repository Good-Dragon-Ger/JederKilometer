package de.gooddragon.jederkilometer.application.service.repository;

import de.gooddragon.jederkilometer.domain.model.Sportler;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    List<Sportler> findAll();

    Sportler findByUsername(String username);

    Sportler save(Sportler sportler);

    Sportler findByUuid(UUID id);
}
