package com.musichouse.model.service;

import com.musichouse.model.domain.ElectricGuitar;

import java.util.List;
import java.util.Optional;

public interface ElectricGuitarService {
    ElectricGuitar save(ElectricGuitar electricGuitar);

    ElectricGuitar update(String model, ElectricGuitar electricGuitar);

    List<ElectricGuitar> getAll();

    Optional<ElectricGuitar> getByModel(String model);

    void delete(String model);
}
