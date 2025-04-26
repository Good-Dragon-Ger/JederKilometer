package de.gooddragon.jederkilometer.domain.service;

import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KMBerechnungTests {

    private KMBerechnung kmBerechnung;
    private ActivityFilter activityFilterMock;

    @BeforeEach
    void setUp() {
        activityFilterMock = mock(ActivityFilter.class);
        kmBerechnung = new KMBerechnung();
    }

    @Test
    @DisplayName("Berechnung der Gesamt-Kilometer")
    void test_01() {
        List<Aufzeichnung> activities = Arrays.asList(
                new Aufzeichnung(UUID.randomUUID(), 10.0, LocalDate.now(), UUID.randomUUID()),
                new Aufzeichnung(UUID.randomUUID(), 15.0, LocalDate.now(), UUID.randomUUID())
        );

        double result = kmBerechnung.berechneGesamtKm(activities);

        assertThat(result).isEqualTo(25.0);
    }

    @Test
    @DisplayName("Berechnung der Gesamt-Kilometer für ein Team")
    void test_02() {

        UUID sportler1 = UUID.randomUUID();
        UUID sportler2 = UUID.randomUUID();
        UUID sportler3 = UUID.randomUUID();

        Set<UUID> team = Set.of(sportler1, sportler3);


        List<Aufzeichnung> activities = Arrays.asList(
                new Aufzeichnung(UUID.randomUUID(), 10.0, LocalDate.now(), sportler1),
                new Aufzeichnung(UUID.randomUUID(), 15.0, LocalDate.now(), sportler2),
                new Aufzeichnung(UUID.randomUUID(), 20.0, LocalDate.now(), sportler3)
        );

        when(activityFilterMock.filterActivitiesBySportler(activities, team.iterator().next()))
                .thenReturn(Collections.singletonList(activities.getFirst()));

        double result = kmBerechnung.berechneGesamtKmTeam(activities, team);

        assertThat(result).isEqualTo(30.0);
    }

    @Test
    @DisplayName("Berechnung der Gesamt-Kilometer pro Sportart")
    void test_03() {
        UUID sportart = UUID.randomUUID();
        List<Aufzeichnung> activities = Arrays.asList(
                new Aufzeichnung(sportart, 10.0, LocalDate.now(), UUID.randomUUID()),
                new Aufzeichnung(UUID.randomUUID(), 15.0, LocalDate.now(), UUID.randomUUID())
        );

        when(activityFilterMock.filterActivitiesBySportart(activities, sportart))
                .thenReturn(Collections.singletonList(activities.getFirst()));

        double result = kmBerechnung.berechneGesamtKmProSportart(activities, sportart);

        assertThat(result).isEqualTo(10.0);
    }

    @Test
    @DisplayName("Berechnung der Gesamt-Kilometer pro Tag")
    void test_04() {
        LocalDate datum = LocalDate.of(2025, 4, 24);
        List<Aufzeichnung> activities = Arrays.asList(
                new Aufzeichnung(UUID.randomUUID(), 10.0, datum, UUID.randomUUID()),
                new Aufzeichnung(UUID.randomUUID(), 15.0, datum.plusDays(1), UUID.randomUUID())
        );

        double result = kmBerechnung.berechneGesamtKmProTag(activities, datum);

        assertThat(result).isEqualTo(10.0);
    }
}
