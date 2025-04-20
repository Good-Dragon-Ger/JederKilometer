package de.gooddragon.jederkilometer.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SportlerTests {

    @Test
    @DisplayName("Teste Constructor mit ID")
    void test_01() {
        UUID id = UUID.randomUUID();
        UUID team = UUID.randomUUID();

        Sportler sportler = new Sportler(id, "Markus K.", "Markus Kowalski", team, new HashSet<>());

        assertThat(sportler.getId()).isEqualTo(id);
        assertThat(sportler.getUserName()).isEqualTo("Markus K.");
        assertThat(sportler.getName()).isEqualTo("Markus Kowalski");
        assertThat(sportler.getTeam()).isEqualTo(team);
        assertThat(sportler.getActivities()).isEmpty();
    }

    @Test
    @DisplayName("Teste Constructor ohne ID")
    void test_02() {

        UUID team = UUID.randomUUID();

        Sportler sportler = new Sportler("Markus A.", "Markus Kovalski", team, new HashSet<>());

        sportler.setActivities(Set.of(UUID.randomUUID()));
        sportler.setUserName("Markus K.");
        sportler.setName("Markus Kowalski");

        assertThat(sportler.getId()).isNotNull();
        assertThat(sportler.getUserName()).isEqualTo("Markus K.");
        assertThat(sportler.getName()).isEqualTo("Markus Kowalski");
        assertThat(sportler.getTeam()).isEqualTo(team);
        assertThat(sportler.getActivities()).isNotEmpty();
    }

    @Test
    @DisplayName("Teste Constructor ohne ID und Set")
    void test_03() {

        UUID team = UUID.randomUUID();

        Sportler sportler = new Sportler("Markus K.", "Markus Kowalski", team);

        assertThat(sportler.getId()).isNotNull();
        assertThat(sportler.getUserName()).isEqualTo("Markus K.");
        assertThat(sportler.getName()).isEqualTo("Markus Kowalski");
        assertThat(sportler.getTeam()).isEqualTo(team);
        assertThat(sportler.getActivities()).isEmpty();
    }

    @Test
    @DisplayName("Teste Constructor ohne ID, Team und Set")
    void test_04() {
        Sportler sportler = new Sportler("Markus K.", "Markus Kowalski");

        assertThat(sportler.getId()).isNotNull();
        assertThat(sportler.getUserName()).isEqualTo("Markus K.");
        assertThat(sportler.getName()).isEqualTo("Markus Kowalski");
        assertThat(sportler.getTeam()).isNull();
        assertThat(sportler.getActivities()).isEmpty();
    }
}
