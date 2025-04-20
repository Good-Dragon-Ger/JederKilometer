package de.gooddragon.jederkilometer.domain.model;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDate;
import java.util.UUID;

@AggregateRoot
public class Aufzeichnung {

    private final UUID id;
    private final UUID sportart;
    private final double km;
    private final LocalDate datum;
    private final AggregateReference<Sportler, UUID> name;

    public Aufzeichnung(UUID id, UUID sportart, double km,LocalDate datum, UUID name) {
        this.id = id;
        this.sportart = sportart;
        this.km = km;
        this.datum = datum;
        this.name = AggregateReference.to(name);
    }

    public Aufzeichnung(UUID sportart, double km, LocalDate datum, UUID name) {
        this(UUID.randomUUID(), sportart, km, datum, name);
    }

    public UUID id() {
        return id;
    }

    public UUID sportart() {
        return sportart;
    }

    public double km() {
        return km;
    }

    public LocalDate datum() {
        return datum;
    }

    public UUID name() {
        return name.getId();
    }


}
