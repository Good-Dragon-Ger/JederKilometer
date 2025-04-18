package de.gooddragon.jederkilometer.domain.model;

import java.util.UUID;

@AggregateRoot
public class Sportart {

    private final UUID id;
    private final String sport;
    private double preis;
    private String kategorie;
    private boolean aktiv;

    public Sportart(UUID id, String sport, double preis, String kategorie, boolean aktiv) {
        this.id = id;
        this.sport = sport;
        this.preis = preis;
        this.kategorie = kategorie;
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

    public String getKategorie() {
        return kategorie;
    }

    public void setKategorie(String kategorie) {
        this.kategorie = kategorie;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }
}
