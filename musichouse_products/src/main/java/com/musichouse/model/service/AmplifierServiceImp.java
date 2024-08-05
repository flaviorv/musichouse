package com.musichouse.model.service;

import com.musichouse.model.domain.Amplifier;
import com.musichouse.model.repository.AmplifierRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AmplifierServiceImp implements AmplifierService {
    private final AmplifierRepository amplifierRepository;

    public AmplifierServiceImp(AmplifierRepository amplifierRepository) {
        this.amplifierRepository = amplifierRepository;
    }

    @Override
    public Amplifier save(Amplifier amplifier) {
        if(getByModel(amplifier.getModel()).isPresent()) {
            throw new EntityExistsException("Amplifier with Model "+amplifier.getModel()+" already exists");
        }
        return amplifierRepository.save(amplifier);
    }

    @Override
    public List<Amplifier> getAll() {
        return amplifierRepository.findAll();
    }

    @Override
    public Optional<Amplifier> getByModel(String model) {
        return amplifierRepository.findById(model);
    }

    @Override
    public Amplifier update(String model, Amplifier amplifier) {
        if (getByModel(model).isPresent()) {
            amplifier.setModel(model);
            amplifierRepository.save(amplifier);
            return amplifier;
        }
        throw new EntityNotFoundException("Amplifier with Model "+model+" not found to update");

    }

    @Override
    public void delete(String model) {
        if (getByModel(model).isPresent()) {
            amplifierRepository.deleteById(model);
        }else{
            throw new EntityNotFoundException("Amplifier with Model "+model+" not found to delete");
        }
    }
}
