package de.gooddragon.jederkilometer.adapter.database.entity;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record Sport(@Id Long id, UUID uuid, String sport, Double price, String category, Boolean active) {
}
