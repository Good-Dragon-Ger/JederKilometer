package de.gooddragon.jederkilometer.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SportartTest {

    @Test
    @DisplayName("Constructor mit ID")
    void test_01() {
        UUID id = UUID.randomUUID();

        Sportart sportart = new Sportart(id, "Laufen", 0.0, false);

        sportart.setPreis(0.5);
        sportart.setAktiv(true);

        assertThat(sportart.getId()).isEqualTo(id);
        assertThat(sportart.getSport()).isEqualTo("Laufen");
        assertThat(sportart.getPreis()).isEqualTo(0.5);
        assertThat(sportart.isAktiv()).isTrue();
    }
}
