package de.gooddragon.jederkilometer.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AufzeichnungTest {

    @Test
    @DisplayName("Teste Constructor mit ID")
    public void test_01() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.of(2022, 4, 22);

        Aufzeichnung aufzeichnung = new Aufzeichnung(id, "Laufen", 10.5, date, userId);

        assertThat(aufzeichnung.id()).isEqualTo(id);
        assertThat(aufzeichnung.sportart()).isEqualTo("Laufen");
        assertThat(aufzeichnung.km()).isEqualTo(10.5);
        assertThat(aufzeichnung.datum()).isEqualTo(date);
        assertThat(aufzeichnung.name()).isEqualTo(userId);
    }

    @Test
    @DisplayName("Teste Constructor ohne ID")
    public void test_02() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.of(2022, 4, 22);
        Aufzeichnung aufzeichnung = new Aufzeichnung("Radfahren", 20.3, date, userId);
        assertThat(aufzeichnung.id()).isNotNull();
        assertThat(aufzeichnung.sportart()).isEqualTo("Radfahren");
        assertThat(aufzeichnung.km()).isEqualTo(20.3);
        assertThat(aufzeichnung.datum()).isEqualTo(date);
        assertThat(aufzeichnung.name()).isEqualTo(userId);
    }
}
