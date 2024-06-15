package com.musichouse.model.service;

import com.musichouse.model.domain.Amplifier;
import com.musichouse.model.repository.AmplifierRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import java.util.List;
import java.util.Optional;

@Service
public class AmplifierService {
    private final AmplifierRepository amplifierRepository;

    public AmplifierService(AmplifierRepository amplifierRepository) {
        this.amplifierRepository = amplifierRepository;
    }

    public Optional<Amplifier> save(Amplifier amplifier) {
        try {
            amplifierRepository.save(amplifier);
            return Optional.of(amplifier);
        }
        catch (MethodArgumentConversionNotSupportedException e) {
            return Optional.empty();
        }
    }

    public List<Amplifier> getAll() {
        return amplifierRepository.findAll();
    }

    public Optional<Amplifier> getByModel(String model) {
        return amplifierRepository.findById(model);
    }

    public Optional<Amplifier> update(String model, Amplifier amplifier
    ) {
        if (getByModel(model).isPresent()) {
            amplifier.setModel(model);
            amplifierRepository.save(amplifier);
            return Optional.of(amplifier);
        }
        return Optional.empty();

    }

    public Boolean delete(String model) {
        if (getByModel(model).isPresent()) {
            amplifierRepository.deleteById(model);
            return true;
        }
        return false;
    }
}
