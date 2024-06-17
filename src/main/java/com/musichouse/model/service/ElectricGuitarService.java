package com.musichouse.model.service;

import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.repository.ElectricGuitarRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ElectricGuitarService {

    private final ElectricGuitarRepository electricGuitarRepository;

    public ElectricGuitarService(ElectricGuitarRepository electricGuitarRepository) {
        this.electricGuitarRepository = electricGuitarRepository;
    }

    public ElectricGuitar save(ElectricGuitar electricGuitar) {
        if(getByModel(electricGuitar.getModel()).isPresent()) {
            throw new EntityExistsException("ElectricGuitar with Model "+electricGuitar.getModel()+" already exists");
        }
        return electricGuitarRepository.save(electricGuitar);
    }

    public List<ElectricGuitar> getAll() {
        return electricGuitarRepository.findAll();
    }

    public Optional<ElectricGuitar> getByModel(String model) {
        model = model.substring(0, 1).toUpperCase() + model.substring(1).toLowerCase();
        return electricGuitarRepository.findById(model);
    }

    public Optional<ElectricGuitar> update(String model, ElectricGuitar electricGuitar
    ) {
        if(getByModel(model).isPresent()){
            electricGuitar.setModel(model);
            electricGuitarRepository.save(electricGuitar);
            return Optional.of(electricGuitar);
        }
        return Optional.empty();
    }

    public Boolean delete(String model) {
        if (getByModel(model).isPresent()) {
            electricGuitarRepository.deleteById(model);
            return true;
        }
        return false;
    }
}
