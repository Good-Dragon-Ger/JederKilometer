package de.gooddragon.jederkilometer.adapter.database.repository.crud;

import de.gooddragon.jederkilometer.adapter.database.entity.Teams;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataTeamsRepository extends CrudRepository<Teams, Long> {

    List<Teams> findAll();

    Optional<Teams> findByUuid(UUID uuid);

    Teams save(Teams team);

    Optional<Teams> findByName(String teamName);
}
