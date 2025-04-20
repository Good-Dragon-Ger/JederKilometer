package de.gooddragon.jederkilometer.adapter.database.repository.crud;

import de.gooddragon.jederkilometer.adapter.database.entity.Activity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataActivityRepository extends CrudRepository<Activity, Long>{

    List<Activity> findAll();

    List<Activity> findByDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<Activity> findByUuid(UUID id);

    Activity save(Activity activity);
}
