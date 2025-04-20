package de.gooddragon.jederkilometer.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamTests {

    @Test
    @DisplayName("Constructor mit ID")
    void test_01() {
        UUID id = UUID.randomUUID();

        Team team = new Team(id, "YokoRaiders", new HashSet<>());

        assertThat(team.getId()).isEqualTo(id);
        assertThat(team.getName()).isEqualTo("YokoRaiders");
        assertThat(team.getMember()).isEmpty();
    }

    @Test
    @DisplayName("Constructor ohne ID")
    void test_02() {

        Team team = new Team("YokoRaiders", new HashSet<>());
        team.setMember(Set.of(UUID.randomUUID()));

        assertThat(team.getId()).isNotNull();
        assertThat(team.getName()).isEqualTo("YokoRaiders");
        assertThat(team.getMember()).isNotEmpty();
    }

    @Test
    @DisplayName("Constructor ohne ID und Mitglieder")
    void test_03() {

        Team team = new Team("YokoRaiders");

        assertThat(team.getId()).isNotNull();
        assertThat(team.getName()).isEqualTo("YokoRaiders");
        assertThat(team.getMember()).isEmpty();
    }
}
