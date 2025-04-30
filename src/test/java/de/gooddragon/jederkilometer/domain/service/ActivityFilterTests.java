package de.gooddragon.jederkilometer.domain.service;

import de.gooddragon.jederkilometer.domain.model.Aufzeichnung;
import de.gooddragon.jederkilometer.domain.model.strava.Zeitraum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivityFilterTests {

    private final ActivityFilter activityFilter = new ActivityFilter();

    @Test
    @DisplayName("Filtert Aktivitäten nach Sportler-ID")
    void test_01() {
        UUID sportler1 = UUID.randomUUID();
        UUID sportler2 = UUID.randomUUID();

        List<Aufzeichnung> activities = Arrays.asList(
                new Aufzeichnung(UUID.randomUUID(), 10.0, LocalDate.now(), sportler1),
                new Aufzeichnung(UUID.randomUUID(), 5.0, LocalDate.now(), sportler2)
        );

        List<Aufzeichnung> result = activityFilter.filterActivitiesBySportler(activities, sportler1);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().name()).isEqualTo(sportler1);
    }

    @Test
    @DisplayName("Filtert Aktivitäten nach Sportart-ID")
    void test_02() {
        UUID sportart1 = UUID.randomUUID();
        UUID sportart2 = UUID.randomUUID();

        List<Aufzeichnung> activities = Arrays.asList(
                new Aufzeichnung(sportart1, 12.5, LocalDate.now(), UUID.randomUUID()),
                new Aufzeichnung(sportart2, 7.5, LocalDate.now(), UUID.randomUUID())
        );

        List<Aufzeichnung> result = activityFilter.filterActivitiesBySportart(activities, sportart1);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().sportart()).isEqualTo(sportart1);
    }

    @Test
    @DisplayName("Filtert Aktivitäten nach Zeitraum")
    void test_03() {
        UUID zeit1 = UUID.randomUUID();
        UUID zeit2 = UUID.randomUUID();

        Zeitraum zeitraum = new Zeitraum(LocalDate.now(), LocalDate.now().plusDays(1));

        List<Aufzeichnung> activities = Arrays.asList(
                new Aufzeichnung(zeit1, UUID.randomUUID(), 12.5, LocalDate.now(), UUID.randomUUID()),
                new Aufzeichnung(zeit2, UUID.randomUUID(),7.5, LocalDate.now().plusMonths(1), UUID.randomUUID())
        );

        List<Aufzeichnung> result = activityFilter.filterActivitiesByEventTime(activities, zeitraum);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().id()).isEqualTo(zeit1);
    }
}
