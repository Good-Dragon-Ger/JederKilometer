package de.gooddragon.jederkilometer.adapter.database.repository.crud;

import de.gooddragon.jederkilometer.adapter.database.entity.Sport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataSportRepository extends CrudRepository<Sport, Long> {

    List<Sport> findAll();

    Optional<Sport> findByUuid(UUID uuid);

    Optional<Sport> findBySport(String sportart);

    List<Sport> findByCategory(String category);

    Sport save(Sport sport);
}
