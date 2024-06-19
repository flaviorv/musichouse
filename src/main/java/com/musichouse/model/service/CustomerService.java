package com.musichouse.model.service;

import com.musichouse.model.domain.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);

    Customer update(String email, Customer customer);

    List<Customer> getAll();

    Optional<Customer> getByEmail(String email);

    void delete(String email);

}
