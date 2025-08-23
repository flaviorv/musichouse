package com.musichouse.model.domain;

import java.io.IOException;
import java.util.Map;

public class ProductFactory {
    public static Product createProduct(String type, Map<String, Object> attributes) throws IOException {
        String model = (String) attributes.get("model");
        String brand = (String) attributes.get("brand");
        float price = ((Number) attributes.get("price")).floatValue();
        int quantity = ((Number) attributes.get("quantity")).intValue();
        String image = (String) attributes.get("image");
        byte[] bImage = Product.loadImage(image);

        return switch (type.toLowerCase()) {
            case "guitar" -> {
                int strings = ((Number) attributes.get("strings")).intValue();
                boolean activePickup = (Boolean) attributes.get("activePickup");
                yield new ElectricGuitar(model, brand, price, quantity, bImage, strings, activePickup);
            }
            case "amplifier" -> {
                int watts = ((Number) attributes.get("watts")).intValue();
                int speakerInch = ((Number) attributes.get("speakerInch")).intValue();
                yield new Amplifier(model, brand, price, quantity, bImage, watts, speakerInch);
            }
            default -> throw new IllegalArgumentException("Unknown product type: " + type);
        };
    }
}
