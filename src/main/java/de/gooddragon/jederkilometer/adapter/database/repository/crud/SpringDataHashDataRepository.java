package de.gooddragon.jederkilometer.adapter.database.repository.crud;

import de.gooddragon.jederkilometer.adapter.database.entity.HashData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpringDataHashDataRepository extends CrudRepository<HashData, Long> {

    List<HashData> findAll();
}
