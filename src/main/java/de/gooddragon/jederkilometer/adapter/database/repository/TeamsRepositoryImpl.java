package de.gooddragon.jederkilometer.adapter.database.repository;

import de.gooddragon.jederkilometer.adapter.database.entity.Teams;
import de.gooddragon.jederkilometer.adapter.database.repository.crud.SpringDataTeamsRepository;
import de.gooddragon.jederkilometer.application.service.repository.TeamsRepository;
import de.gooddragon.jederkilometer.domain.model.Team;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Repository
public class TeamsRepositoryImpl implements TeamsRepository {

    SpringDataTeamsRepository repository;

    public TeamsRepositoryImpl(SpringDataTeamsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Team> findAll() {
        return repository.findAll().stream().map(this::convertTeam).toList();
    }

    @Override
    public Team findByTeamName(String teamName) {
        return repository.findByName(teamName).map(this::convertTeam).orElse(null);
    }

    @Override
    public Team save(Team team) {
        Long id = repository.findByUuid(team.getId()).map(Teams::id).orElse(null);
        Teams teams = new Teams(id, team.getId(), team.getName());
        Teams savedTeam = repository.save(teams);
        return convertTeam(savedTeam);
    }

    @Override
    public Team findAllByUuid(UUID id) {
        return repository.findByUuid(id).map(this::convertTeam).orElse(null);
    }

    private Team convertTeam(Teams team) {
        return new Team(team.uuid(), team.name(), new HashSet<>());
    }
}
