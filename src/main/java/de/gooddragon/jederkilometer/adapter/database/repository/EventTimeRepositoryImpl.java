package de.gooddragon.jederkilometer.adapter.database.repository;

import de.gooddragon.jederkilometer.adapter.database.entity.EventTime;
import de.gooddragon.jederkilometer.adapter.database.repository.crud.SpringDataEventTimeRepository;
import de.gooddragon.jederkilometer.application.service.repository.EventTimeRepository;
import de.gooddragon.jederkilometer.domain.model.strava.Zeitraum;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventTimeRepositoryImpl implements EventTimeRepository {

    private final SpringDataEventTimeRepository repository;

    public EventTimeRepositoryImpl(SpringDataEventTimeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Zeitraum> findAll() {
        return repository.findAll().stream().map(this::convertEventTime).toList();
    }

    @Override
    public Zeitraum save(Zeitraum zeitraum) {
        Long id = repository.findByUuid(zeitraum.getId()).map(EventTime::id).orElse(null);
        EventTime eventTime = new EventTime(id, zeitraum.getId(), zeitraum.getStartDate(), zeitraum.getEndDate());
        repository.save(eventTime);
        return convertEventTime(eventTime);
    }

    private Zeitraum convertEventTime(EventTime eventTime) {
        return new Zeitraum(eventTime.uuid(), eventTime.startDate(), eventTime.endDate());
    }
}
