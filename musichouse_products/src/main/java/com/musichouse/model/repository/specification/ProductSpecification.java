package com.musichouse.model.repository.specification;

import com.musichouse.model.domain.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> byModel(String m) {
        if (m == null || m.isEmpty()) {
            return null;
        }
        return (root, cq, cb) -> cb.equal(root.get("model"), m);
    }

    public static Specification<Product> byType(String t) {
        if (t == null || t.isEmpty()) {
            return null;
        }
        return (root, cq, cb) -> cb.equal(root.get("category"), t);
    }

    public static Specification<Product> byBrand(String b) {
        if (b == null || b.isEmpty()) {
            return null;
        }
        return (root, cq, cb) -> cb.equal(root.get("brand"), b);
    }

    public static Specification<Product> byStrings(Integer s) {
        if (s == null) {
            return null;
        }
        return (root, cq, cb) -> cb.equal(root.get("strings"), s);
    }

    public static Specification<Product> byActivePickup(Boolean ap) {
        if (ap == null || ap) {
            return null;
        }
        return (root, cq, cb) -> cb.equal(root.get("activePickup"), ap);
    }

    public static Specification<Product> byWatts(Integer w) {
        if (w == null) {
            return null;
        }
        return (root, cq, cb) -> cb.equal(root.get("watts"), w);
    }

    public static Specification<Product> bySpeakerInch(Integer sp) {
        if (sp == null) {
            return null;
        }
        return (root, cq, cb) -> cb.equal(root.get("speakerInch"), sp);
    }
}
