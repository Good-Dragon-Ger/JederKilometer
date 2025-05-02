package de.gooddragon.jederkilometer.domain.model.strava;

import java.util.Objects;

public record EventAufzeichnung(Athlete athlete,
                                String name,
                                double distance,
                                int moving_time,
                                int elapsed_time,
                                double total_elevation_gain,
                                String sport_type,
                                String workout_type) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventAufzeichnung(
                Athlete athlete1, String name1, double distance1, int movingTime, int elapsedTime,
                double totalElevationGain, String sportType, String workoutType
        ))) return false;
        return Double.compare(distance, distance1) == 0 && moving_time == movingTime && elapsed_time == elapsedTime && Double.compare(total_elevation_gain, totalElevationGain) == 0 && Objects.equals(name, name1) && Objects.equals(athlete, athlete1) && Objects.equals(sport_type, sportType) && Objects.equals(workout_type, workoutType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(athlete, name, distance, moving_time, elapsed_time, total_elevation_gain, sport_type, workout_type);
    }

    public int hashCode2() {
        return Objects.hash(athlete, distance, moving_time, elapsed_time, total_elevation_gain, sport_type);
    }
}
