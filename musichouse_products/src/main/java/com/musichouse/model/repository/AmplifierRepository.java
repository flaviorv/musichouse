package com.musichouse.model.repository;

import com.musichouse.model.domain.Amplifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmplifierRepository extends JpaRepository<Amplifier, String> {

}
