package de.gooddragon.jederkilometer.application.service.repository;

import de.gooddragon.jederkilometer.domain.model.Sportart;

import java.util.List;
import java.util.UUID;

public interface SportRepository {

    List<Sportart> findAll();

    Sportart findBySportart(String sportart);

    Sportart findByUuid(UUID sportart);

    Sportart save(Sportart sportart);

    List<Sportart> findByCategory(String category);
}
