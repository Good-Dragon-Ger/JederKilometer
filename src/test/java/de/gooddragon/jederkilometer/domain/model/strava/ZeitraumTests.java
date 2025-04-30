package de.gooddragon.jederkilometer.domain.model.strava;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ZeitraumTests {

    @Test
    @DisplayName("Teste Konstruktor mit ID")
    void test_01() {
        UUID id = UUID.randomUUID();
        LocalDate startDate = LocalDate.of(2022, 4, 22);
        LocalDate endDate = LocalDate.of(2022, 4, 23);

        Zeitraum zeitraum  = new Zeitraum(id, startDate, endDate);

        assertThat(zeitraum.getId()).isEqualTo(id);
        assertThat(zeitraum.getStartDate()).isEqualTo(startDate);
        assertThat(zeitraum.getEndDate()).isEqualTo(endDate);
    }

    @Test
    @DisplayName("Teste Konstruktor ohne ID")
    void test_02() {
        LocalDate startDate = LocalDate.of(2022, 4, 22);
        LocalDate endDate = LocalDate.of(2022, 4, 23);

        Zeitraum zeitraum  = new Zeitraum(startDate, endDate);

        assertThat(zeitraum.getId()).isNotNull();
        assertThat(zeitraum.getStartDate()).isEqualTo(startDate);
        assertThat(zeitraum.getEndDate()).isEqualTo(endDate);
    }



}