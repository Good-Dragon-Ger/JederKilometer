package de.gooddragon.jederkilometer.application.service.repository;

import de.gooddragon.jederkilometer.domain.model.Team;

import java.util.List;
import java.util.UUID;

public interface TeamsRepository {

    List<Team> findAll();

    Team findByTeamName(String teamName);

    Team save(Team team);

    Team findAllByUuid(UUID id);
}
