package com.musichouse.model.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.musichouse.model.domain.ProductType;
import com.musichouse.model.repository.ProductRepository;
import com.musichouse.model.repository.specification.ProductSpecification;

@Service
public class QueryParser {

    private final ProductRepository productRepository;

    public QueryParser(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductSpecification matchPredicates(String searchText) {
        String noPunct = searchText.toLowerCase().replaceAll("[^a-z0-9\\s\"]", "");
        String[] words = noPunct.split("\\s+");
        ProductSpecification ps = new ProductSpecification();

        ps = typeSearch(ps, words);
        ps = brandSearch(ps, words);
        ps = modelSearch(ps, words);
        ps = stringsSearch(ps, words);
        ps = activePickupSearch(ps, words);
        ps = wattsSearch(ps, words);
        ps = speakerInchSearch(ps, words);

        return ps;
    }

    private ProductSpecification typeSearch(ProductSpecification ps, String[] words) {
        for (int i = 0; i < words.length; i++) {
            ProductType type = ProductType.search(words[i]);
            if (type != null) {
                ps.setType(type);
            }
        }
        return ps;
    }

    private ProductSpecification brandSearch(ProductSpecification ps, String[] words) {
        List<String> brands;

        if (ps.getType() != null) {
            brands = productRepository.findBrandsByType(ps.getType());
        } else {
            brands = productRepository.findAllBrands();
        }

        List<String> lowerCaseBrands = brands.stream().map(String::toLowerCase).collect(Collectors.toList());

        for (String word : words) {
            if (lowerCaseBrands.contains(word)) {
                ps.setBrand(word);
            }
        }
        return ps;
    }

    private ProductSpecification modelSearch(ProductSpecification ps, String[] words) {
        List<String> models;

        if (ps.getBrand() != null) {
            models = productRepository.findModelsByBrand(ps.getBrand());
        } else if (ps.getType() != null) {
            models = productRepository.findModelsByType(ps.getType());
        } else {
            models = productRepository.findAllModels();
        }

        List<String> lowerCaseModels = models.stream().map(String::toLowerCase).collect(Collectors.toList());

        for (String word : words) {
            if (lowerCaseModels.contains(word)) {
                ps.setModel(word);
            }
        }
        return ps;
    }

    private ProductSpecification stringsSearch(ProductSpecification ps, String[] words) {
        if (ps.getType() == null || ps.getType() == ProductType.GUITAR) {
            List<String> keys = List.of("string", "strings");
            final int WORDS_MAX_RANGE = 3;

            for (int i = 0; i < words.length; i++) {
                if (keys.contains(words[i])) {
                    int start = i - WORDS_MAX_RANGE;
                    start = start >= 0 ? start : 0;

                    int end = i + WORDS_MAX_RANGE;
                    end = end < words.length ? end : words.length - 1;

                    for (int j = start; j <= end; j++) {
                        try {
                            Integer strings = Integer.parseInt(words[j]);
                            ps.setStrings(strings);
                            return ps;
                        } catch (NumberFormatException e) {

                        }
                    }
                }
            }
        }
        return ps;
    }

    private ProductSpecification activePickupSearch(ProductSpecification ps, String[] words) {
        if (ps.getType() == null || ps.getType() == ProductType.GUITAR) {
            List<String> aKeys = List.of("active", "actives");
            List<String> pKeys = List.of("passive", "passives");

            for (int i = 0; i < words.length; i++) {
                if (aKeys.contains(words[i])) {
                    ps.setActivePickup(true);
                    return ps;
                } else if (pKeys.contains(words[i])) {
                    ps.setActivePickup(false);
                    return ps;
                }
            }
        }
        return ps;
    }

    private ProductSpecification wattsSearch(ProductSpecification ps, String[] words) {
        if (ps.getType() == null || ps.getType() == ProductType.AMPLIFIER) {
            List<String> keys = List.of("watts", "w");
            final int WORDS_MAX_RANGE = 3;

            for (int i = 0; i < words.length; i++) {
                if (keys.contains(words[i]) || words[i].endsWith("watts") || words[i].endsWith("w")) {
                    int start = i - WORDS_MAX_RANGE;
                    start = start >= 0 ? start : 0;

                    int end = i + WORDS_MAX_RANGE;
                    end = end < words.length ? end : words.length - 1;

                    for (int j = start; j <= end; j++) {
                        try {
                            words[j] = words[j].replace("watts", "");
                            words[j] = words[j].replace("w", "");
                            Integer watts = Integer.parseInt(words[j]);
                            ps.setWatts(watts);
                            return ps;
                        } catch (NumberFormatException e) {

                        }
                    }
                }
            }
        }
        return ps;
    }

    private ProductSpecification speakerInchSearch(ProductSpecification ps, String[] words) {
        if (ps.getType() == null || ps.getType() == ProductType.AMPLIFIER) {
            List<String> keys = List.of("speaker", "inch", "inches", "in");
            final int WORDS_MAX_RANGE = 3;

            for (int i = 0; i < words.length; i++) {
                int len = words[i].length() - 1;
                if (keys.contains(words[i]) || words[i].charAt(len) == '\"') {
                    int start = i - WORDS_MAX_RANGE;
                    start = start >= 0 ? start : 0;

                    int end = i + WORDS_MAX_RANGE;
                    end = end < words.length ? end : words.length - 1;

                    for (int j = start; j <= end; j++) {
                        try {
                            words[j] = words[j].replace("\"", "");
                            words[j] = words[j].replace("in", "");
                            Integer speakerInch = Integer.parseInt(words[j]);
                            ps.setSpeakerInch(speakerInch);
                            return ps;
                        } catch (NumberFormatException e) {

                        }
                    }
                }
            }
        }
        return ps;
    }

}
