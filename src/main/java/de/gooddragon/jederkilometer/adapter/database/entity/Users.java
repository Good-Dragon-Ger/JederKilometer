package de.gooddragon.jederkilometer.adapter.database.entity;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record Users(@Id Long id, UUID uuid, String username, String name, UUID team) {
}
