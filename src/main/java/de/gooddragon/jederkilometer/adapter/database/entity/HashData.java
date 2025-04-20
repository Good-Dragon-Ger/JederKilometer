package de.gooddragon.jederkilometer.adapter.database.entity;

import org.springframework.data.annotation.Id;

public record HashData(@Id Long id, Integer hash, boolean inserted) {
}
