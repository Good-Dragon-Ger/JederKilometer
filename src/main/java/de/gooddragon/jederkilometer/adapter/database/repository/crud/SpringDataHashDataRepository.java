package de.gooddragon.jederkilometer.adapter.database.repository.crud;

import de.gooddragon.jederkilometer.adapter.database.entity.HashData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataHashDataRepository extends CrudRepository<HashData, Long> {

    List<HashData> findAll();

    Optional<HashData> findByHash(int hash);

    HashData save(HashData hashData);
}
