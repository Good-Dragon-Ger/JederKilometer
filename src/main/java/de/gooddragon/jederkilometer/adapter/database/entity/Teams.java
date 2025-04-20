package de.gooddragon.jederkilometer.adapter.database.entity;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record Teams(@Id Long id, UUID uuid, String name) {
}
