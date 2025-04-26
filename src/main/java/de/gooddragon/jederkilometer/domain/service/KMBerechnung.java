package de.gooddragon.jederkilometer.domain.service;

import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import de.gooddragon.jederkilometer.domain.model.Sportart;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class KMBerechnung {

    private final ActivityFilter activityFilter = new ActivityFilter();

    public double berechneGesamtKm(List<Aufzeichnung> activities) {
        return activities.stream().mapToDouble(Aufzeichnung::km).sum();
    }

    public double berechneGesamtKmTeam(List<Aufzeichnung> activities, Set<UUID> teamSportler) {

        List<Aufzeichnung> teamActivities = teamSportler.stream().flatMap(sportler -> activityFilter.filterActivitiesBySportler(activities, sportler).stream()).toList();
        return teamActivities.stream()
                .mapToDouble(Aufzeichnung::km)
                .sum();
    }

    public double berechneGesamtKmProSportart(List<Aufzeichnung> activities, UUID sportart) {
        List<Aufzeichnung> sportActivities = activityFilter.filterActivitiesBySportart(activities, sportart);
        return sportActivities.stream()
                .mapToDouble(Aufzeichnung::km)
                .sum();
    }

    public double berechneGesamtKmProTag(List<Aufzeichnung> activities, LocalDate datum) {
        return activities.stream()
                .filter(aufzeichnung -> aufzeichnung.datum().equals(datum))
                .mapToDouble(Aufzeichnung::km)
                .sum();
    }

    public double berechneAktiveGesamtKm(List<Aufzeichnung> activities, List<Sportart> sportart) {
       List<Aufzeichnung> sportActivities = activityFilter.filterActivitiesByActiveSportart(activities, sportart);
       return sportActivities.stream().mapToDouble(Aufzeichnung::km).sum();
    }
}
