package de.gooddragon.jederkilometer.application.service.repository;

import de.gooddragon.jederkilometer.domain.model.strava.Zeitraum;

import java.util.List;

public interface EventTimeRepository {
    List<Zeitraum> findAll();

    Zeitraum save(Zeitraum zeitraum);
}
