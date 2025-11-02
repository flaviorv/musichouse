package com.musichouse.domain.product;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ProductType {
    GUITAR("guitar", "guitars"),
    AMPLIFIER("amp", "amps", "amplifier", "amplifiers");

    private final List<String> types;

    ProductType(String... types) {
        this.types = Arrays.asList(types);
    }

    @JsonCreator
    public static ProductType search(String term) {
        String termLowerCase = term.toLowerCase();

        for (ProductType type : ProductType.values()) {
            if (type.types.contains(termLowerCase)) {
                return type;
            }
        }
        return null;
    }
}
