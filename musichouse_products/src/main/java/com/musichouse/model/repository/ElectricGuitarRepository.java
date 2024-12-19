package com.musichouse.model.repository;

import com.musichouse.model.domain.ElectricGuitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricGuitarRepository extends JpaRepository<ElectricGuitar, String> {
}
