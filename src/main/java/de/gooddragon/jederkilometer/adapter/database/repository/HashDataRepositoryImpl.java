package de.gooddragon.jederkilometer.adapter.database.repository;

import de.gooddragon.jederkilometer.adapter.database.entity.HashData;
import de.gooddragon.jederkilometer.adapter.database.repository.crud.SpringDataHashDataRepository;
import de.gooddragon.jederkilometer.application.service.repository.HashDataRepository;
import de.gooddragon.jederkilometer.domain.model.strava.HashMapDaten;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HashDataRepositoryImpl implements HashDataRepository {

    private final SpringDataHashDataRepository repository;

    public HashDataRepositoryImpl(SpringDataHashDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<HashMapDaten> findAll() {
        List<HashData> activityHashes = repository.findAll();
        return activityHashes.stream().map(this::convertHashData).toList();
    }

    @Override
    public HashMapDaten speichen(HashMapDaten hash) {
        Long id = repository.findAll().stream().map(HashData::id).max(Long::compareTo).orElse(null);
        HashData data = repository.save(new HashData(id, hash.hash(), hash.value()));
        return convertHashData(data);
    }

    private HashMapDaten convertHashData(HashData hashData) {
        return new HashMapDaten(hashData.hash(), hashData.inserted());
    }
}
