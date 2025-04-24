package de.gooddragon.jederkilometer.application.service.repository;

import de.gooddragon.jederkilometer.domain.model.strava.HashMapDaten;

import java.util.List;

public interface HashDataRepository {

    List<HashMapDaten> findAll();

    HashMapDaten save(HashMapDaten hash);
}
