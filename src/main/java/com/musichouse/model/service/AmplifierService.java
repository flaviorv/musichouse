package com.musichouse.model.service;

import com.musichouse.model.domain.Amplifier;
import com.musichouse.model.domain.Product;

import java.util.List;
import java.util.Optional;

public interface AmplifierService {
    Amplifier save(Amplifier amplifier);

    Amplifier update(String model, Amplifier amplifier);

    List<Amplifier> getAll();

    Optional<Amplifier> getByModel(String model);

    void delete(String model);
}
