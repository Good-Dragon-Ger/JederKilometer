package de.gooddragon.jederkilometer.adapter.database.repository;

import de.gooddragon.jederkilometer.adapter.database.entity.Sport;
import de.gooddragon.jederkilometer.adapter.database.repository.crud.SpringDataSportRepository;
import de.gooddragon.jederkilometer.application.service.repository.SportRepository;
import de.gooddragon.jederkilometer.domain.model.Sportart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SportRepositoryImpl implements SportRepository {

    private final SpringDataSportRepository repository;

    public SportRepositoryImpl(SpringDataSportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Sportart> findAll() {
        return repository.findAll().stream().map(this::convertSport).toList();
    }

    @Override
    public Sportart findBySportart(String sportart) {
        return repository.findBySport(sportart).map(this::convertSport).orElse(null);
    }

    @Override
    public Sportart findByUuid(UUID uuid) {
        return repository.findByUuid(uuid).map(this::convertSport).orElse(null);
    }

    @Override
    public Sportart save(Sportart sportart) {
        Long id = repository.findByUuid(sportart.getId()).map(Sport::id).orElse(null);
        Sport sport = new Sport(id, sportart.getId(), sportart.getSport(), sportart.getPreis(), sportart.getKategorie(), sportart.getAktiv());
        Sport savedSport = repository.save(sport);
        return convertSport(savedSport);
    }

    private Sportart convertSport(Sport sport) {
        return new Sportart(sport.uuid(), sport.sport(), sport.price(), sport.category(), sport.active());
    }
}
