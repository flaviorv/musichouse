package com.musichouse.model.repository.specification;

import com.musichouse.model.domain.Product;
import io.micrometer.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private String model;
    private String type;
    private String brand;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (model != null && !model.isEmpty()) {
            predicates.add(cb.like(root.get("model"), "%" + model + "%"));
        }
        if (type != null && !type.isEmpty()) {
            predicates.add(cb.like(root.get("type"), "%" + type + "%"));
        }
        if (brand != null && !brand.isEmpty()) {
            predicates.add(cb.like(root.get("brand"), "%" + brand + "%"));
        }

        if (predicates.isEmpty()) {
            return cb.conjunction();
        } else {
            return cb.and(predicates.toArray(new Predicate[0]));
        }
    }
}
