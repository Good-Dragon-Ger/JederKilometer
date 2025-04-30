package de.gooddragon.jederkilometer.adapter.database.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

public record EventTime(@Id Long id, UUID uuid, LocalDate startDate, LocalDate endDate) {
}
