package com.musichouse.model.service;

import com.musichouse.filters.SaleFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service
//@RequiredArgsConstructor
//public class SaleServiceImp implements SaleService{
//
//    private final SaleRepository saleRepository;
//    private final EntityManager entityManager;
//
//    @Override
//    public List<Sale> findWithFilters(SaleFilter filters) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Sale> cq = cb.createQuery(Sale.class);
//        Root<Sale> root = cq.from(Sale.class);
//        List<Predicate> predicates = new ArrayList<>();
//
//        if (filters.getPrice().isPresent()) {
//            Predicate price = cb.equal(root.get("price"), filters.getPrice().get());
//            predicates.add(price);
//        }
//
//        cq.where(predicates.toArray(Predicate[]::new));
//
//        return entityManager.createQuery(cq).getResultList();
//    }
//
//    public Sale save(Sale sale){
//        return saleRepository.save(sale);
//    }
//
//    public List<Sale> findByCustomer(String email){
//        return saleRepository.findByCustomer(email);
//    }
//
//    @Override
//    public Optional<Sale> findById(int id) {
//        return saleRepository.findById(id);
//
//    }
//}
