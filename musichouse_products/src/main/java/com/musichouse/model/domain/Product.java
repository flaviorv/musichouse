package com.musichouse.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data@AllArgsConstructor@NoArgsConstructor@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    protected String model;
    protected String brand;
    protected float price;

}
