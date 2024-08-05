package com.musichouse.model.service;

import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.repository.ElectricGuitarRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ElectricGuitarServiceImp implements ElectricGuitarService {

    private final ElectricGuitarRepository electricGuitarRepository;

    public ElectricGuitarServiceImp(ElectricGuitarRepository electricGuitarRepository) {
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
        return electricGuitarRepository.findById(model);
    }

    public ElectricGuitar update(String model, ElectricGuitar electricGuitar) {
        if(getByModel(model).isPresent()){
            electricGuitar.setModel(model);
            electricGuitarRepository.save(electricGuitar);
            return electricGuitar;
        }
        throw new EntityNotFoundException("ElectricGuitar with Model "+model+" not found to update");
    }

    @Override
    public void delete(String model) {
        if (getByModel(model).isPresent()) {
            electricGuitarRepository.deleteById(model);
        }else {
            throw new EntityNotFoundException("ElectricGuitar with Model "+model+" not found to delete");
        }
    }
}
