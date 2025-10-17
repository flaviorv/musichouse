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
        String noPunct = searchText.toLowerCase().replaceAll("[^a-z0-9\\s]", "");
        String[] words = noPunct.split("\\s+");
        ProductSpecification ps = new ProductSpecification();

        ps = searchTypeFilter(ps, words);
        ps = searchByModel(ps, words);
        ps = searchStringsFilter(ps, words);

        return ps;
    }

    private ProductSpecification searchTypeFilter(ProductSpecification ps, String[] words) {
        for (int i = 0; i < words.length; i++) {
            ProductType type = ProductType.search(words[i]);
            if (type != null) {
                ps.setType(type);
            }
        }
        return ps;
    }

    private ProductSpecification searchByModel(ProductSpecification ps, String[] words) {
        List<String> models;

        if (ps.getType() != null) {
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

    private static final int WORDS_MAX_RANGE = 3;

    private ProductSpecification searchStringsFilter(ProductSpecification ps, String[] words) {
        List<String> keys = List.of("string", "strings");

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
                    } catch (NumberFormatException e) {

                    }
                }
            }
        }
        return ps;
    }

}
