package de.gooddragon.jederkilometer.adapter.database.repository.crud;

import de.gooddragon.jederkilometer.adapter.database.entity.EventTime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataEventTimeRepository extends CrudRepository<EventTime, Long> {

    List<EventTime> findAll();

    Optional<EventTime> findByUuid(UUID uuid);

    EventTime save(EventTime eventTime);
}
