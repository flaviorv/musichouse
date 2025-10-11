package com.musichouse.dto;

public record ProductFilter(
    String model,
    String type,
    String brand,
    int strings,
    boolean activePickup,
    int watts,
    int speakerInch
    ){}
