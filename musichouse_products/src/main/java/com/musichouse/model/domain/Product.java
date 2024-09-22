package com.musichouse.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Data@AllArgsConstructor@NoArgsConstructor@SuperBuilder
@Entity
@Slf4j
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    protected String model;
    protected String brand;
    protected float price;
    protected int quantity;

    public void calculateQuantity(Sale.Product product){
        this.quantity -= product.getQuantity();
        log.info("{} quantity was updated from : {} to {}", this.getModel(), this.getQuantity() + product.getQuantity(), this.getQuantity());
    }
}