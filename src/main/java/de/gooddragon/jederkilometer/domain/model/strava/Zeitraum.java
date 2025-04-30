package de.gooddragon.jederkilometer.domain.model.strava;

import java.time.LocalDate;
import java.util.UUID;

public class Zeitraum {

    private final UUID id;
    private LocalDate startDate;
    private LocalDate endDate;

    public Zeitraum(UUID id, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Zeitraum(LocalDate startDate, LocalDate endDate) {
        this(UUID.randomUUID(), startDate, endDate);
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
