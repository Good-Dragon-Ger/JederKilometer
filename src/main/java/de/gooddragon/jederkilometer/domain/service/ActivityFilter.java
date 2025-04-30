package de.gooddragon.jederkilometer.domain.service;

import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import de.gooddragon.jederkilometer.domain.model.Sportart;
import de.gooddragon.jederkilometer.domain.model.strava.Zeitraum;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ActivityFilter {

    public List<Aufzeichnung> filterActivitiesBySportler(List<Aufzeichnung> activities, UUID sportler) {
        return activities.stream()
                    .filter(activity -> activity.name().equals(sportler))
                    .toList();
    }

    public List<Aufzeichnung> filterActivitiesBySportart(List<Aufzeichnung> activities, UUID sportart) {
        return activities.stream()
                .filter(activity -> activity.sportart().equals(sportart))
                .collect(Collectors.toList());
    }

    public List<Aufzeichnung> filterActivitiesByActiveSportart(List<Aufzeichnung> activities, List<Sportart> sportarten) {
        Set<UUID> aktiveSportartIds = sportarten.stream()
                .filter(Sportart::getAktiv)
                .map(Sportart::getId)
                .collect(Collectors.toSet());

        return activities.stream()
                .filter(aufzeichnung -> aktiveSportartIds.contains(aufzeichnung.sportart()))
                .collect(Collectors.toList());
    }

    public List<Aufzeichnung> filterActivitiesByEventTime(List<Aufzeichnung> activities, Zeitraum zeitraum) {
        return activities.stream()
                .filter(aufzeichnung -> !aufzeichnung.datum().isBefore(zeitraum.getStartDate()) && !aufzeichnung.datum().isAfter(zeitraum.getEndDate()))
                .collect(Collectors.toList());
    }
}