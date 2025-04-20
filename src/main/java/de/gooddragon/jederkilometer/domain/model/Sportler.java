package de.gooddragon.jederkilometer.domain.model;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AggregateRoot
public class Sportler {

    private final UUID id;
    private String userName;
    private String name;
    private AggregateReference<Team, UUID> team;
    private final Set<AggregateReference<Aufzeichnung, UUID>> activities = new HashSet<>();

    public Sportler(UUID id, String userName, String name, UUID team, Set<UUID> activities) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        if (team != null) {
            this.team = AggregateReference.to(team);
        }
        if (activities != null) {
            for (UUID activity : activities) {
                this.activities.add(AggregateReference.to(activity));
            }
        }
    }

    public Sportler(String userName, String name, UUID team, Set<UUID> activities) {
        this(UUID.randomUUID(), userName, name, team, activities);
    }

    public Sportler(String userName, String name, UUID team) {
        this(UUID.randomUUID(), userName, name, team, new HashSet<>());
    }

    public Sportler(String userName, String name) {
        this(UUID.randomUUID(), userName, name, null, new HashSet<>());
    }

    public UUID getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getTeam() {
        if (team == null) {
            return null;
        }
        return team.getId();
    }

    public void setTeam(UUID team) {
        this.team = AggregateReference.to(team);
    }

    public Set<UUID> getActivities() {
        Set<UUID> aufzeichnungen = new HashSet<>();

        for(AggregateReference<Aufzeichnung, UUID> uuid : activities) {
            aufzeichnungen.add(uuid.getId());
        }

        return aufzeichnungen;
    }

    public void setActivities(Set<UUID> activities) {
        for (UUID activity : activities) {
            this.activities.add(AggregateReference.to(activity));
        }
    }
}
