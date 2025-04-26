package de.gooddragon.jederkilometer.domain.model;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.UUID;

@AggregateRoot
public class Sportart {

    private final UUID id;
    private final String sport;
    @Positive(message = "Der Preis muss eine positive Zahl sein.")
    private double preis;
    @Size(max = 100, message = "Die Kategorie darf maximal 100 Zeichen lang sein.")
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

    public boolean getAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sportart sportart)) return false;
        return Double.compare(preis, sportart.preis) == 0 && aktiv == sportart.aktiv && Objects.equals(id, sportart.id) && Objects.equals(sport, sportart.sport) && Objects.equals(kategorie, sportart.kategorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sport, preis, kategorie, aktiv);
    }
}
