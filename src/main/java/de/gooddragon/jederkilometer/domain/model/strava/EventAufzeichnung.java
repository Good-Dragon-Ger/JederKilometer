package de.gooddragon.jederkilometer.domain.model.strava;

import java.util.Objects;

public record EventAufzeichnung(Athlete athlete,
                                String name,
                                double distance,
                                int moving_time,
                                int elapsed_time,
                                double total_elevation_gain,
                                String type,
                                String sport_type,
                                String workout_type) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventAufzeichnung that)) return false;
        return Double.compare(distance, that.distance) == 0 && moving_time == that.moving_time && elapsed_time == that.elapsed_time && Double.compare(total_elevation_gain, that.total_elevation_gain) == 0 && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(athlete, that.athlete) && Objects.equals(sport_type, that.sport_type) && Objects.equals(workout_type, that.workout_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(athlete, name, distance, moving_time, elapsed_time, total_elevation_gain, type, sport_type, workout_type);
    }
}
