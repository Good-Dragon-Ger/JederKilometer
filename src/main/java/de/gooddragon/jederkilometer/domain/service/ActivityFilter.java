package de.gooddragon.jederkilometer.domain.service;

import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;

import java.util.List;
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
}