package de.gooddragon.jederkilometer.application.service.repository;

import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ActivityRepository {
    List<Aufzeichnung> findAll();

    List<Aufzeichnung> findByDateBetween(LocalDate startDate, LocalDate endDate);

    Aufzeichnung save(Aufzeichnung activity);

    Aufzeichnung findByUuid(UUID uuid);
}
