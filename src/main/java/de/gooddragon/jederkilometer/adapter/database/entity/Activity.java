package de.gooddragon.jederkilometer.adapter.database.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.UUID;

public record Activity(@Id Long id, UUID uuid, UUID sport, double km, LocalDate date, UUID name) {
}
