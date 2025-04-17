package de.gooddragon.jederkilometer.domain.model;

import java.util.UUID;

public class Sportart {

    private final UUID id;
    private final String sport;
    private double preis;
    private boolean aktiv;

    public Sportart(UUID id, String sport, double preis, boolean aktiv) {
        this.id = id;
        this.sport = sport;
        this.preis = preis;
        this.aktiv = aktiv;
    }

    public UUID getId() {
        return id;
    }

    public String getSport() {
        return sport;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }
}
