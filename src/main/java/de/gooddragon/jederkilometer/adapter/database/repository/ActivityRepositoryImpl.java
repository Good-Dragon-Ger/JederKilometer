package de.gooddragon.jederkilometer.adapter.database.repository;

import de.gooddragon.jederkilometer.adapter.database.entity.Activity;
import de.gooddragon.jederkilometer.adapter.database.repository.crud.SpringDataActivityRepository;
import de.gooddragon.jederkilometer.application.service.repository.ActivityRepository;
import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {

    private final SpringDataActivityRepository repository;

    public ActivityRepositoryImpl(SpringDataActivityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Aufzeichnung> findAll() {
        List<Activity> activities = repository.findAll();
        return activities.stream().map(this::convertActivity).toList();
    }

    @Override
    public List<Aufzeichnung> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        List<Activity> activities = repository.findByDateBetween(startDate, endDate);
        return activities.stream().map(this::convertActivity).toList();
    }

    @Override
    public Aufzeichnung save(Aufzeichnung aufzeichnung) {
        Long id = repository.findByUuid(aufzeichnung.id()).map(Activity::id).orElse(null);
        Activity activity = new Activity(id, aufzeichnung.id(), aufzeichnung.sportart(), aufzeichnung.km(), aufzeichnung.datum(), aufzeichnung.name());
        Activity saved = repository.save(activity);
        return convertActivity(saved);
    }

    @Override
    public Aufzeichnung findByUuid(UUID uuid) {
        return  repository.findByUuid(uuid).map(this::convertActivity).orElse(null);
    }

    private Aufzeichnung convertActivity(Activity activity) {
        return new Aufzeichnung(activity.uuid(), activity.sport(), activity.km(), activity.date(), activity.name());
    }
}
