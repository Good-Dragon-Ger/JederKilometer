package de.gooddragon.jederkilometer.domain.model;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AggregateRoot
public class Team {
    private final UUID id;
    private String name;
    private final Set<AggregateReference<Sportler,UUID>> member = new HashSet<>();

    public Team(UUID id, String name, Set<UUID> members) {
        this.id = id;
        this.name = name;
        if (members != null) {
            for (UUID sportler : members) {
                this.member.add(AggregateReference.to(sportler));
            }
        }
    }

    public Team(String name, Set<UUID> members) {
        this(UUID.randomUUID(), name, members);
    }

    public Team(String name) {
        this(UUID.randomUUID(), name, new HashSet<>());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UUID> getMember() {
        Set<UUID> sportler = new HashSet<>();

        for(AggregateReference<Sportler, UUID> uuid : member) {
            sportler.add(uuid.getId());
        }

        return sportler;
    }

    public void setMember(Set<UUID> member) {
        if (member != null) {
            for (UUID sportler : member) {
                this.member.add(AggregateReference.to(sportler));
            }
        }
    }
}
