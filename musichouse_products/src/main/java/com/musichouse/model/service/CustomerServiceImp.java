package com.musichouse.model.service;

import com.musichouse.model.domain.Customer;
import com.musichouse.model.repository.CustomerRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        if(getByEmail(customer.getEmail()).isPresent()) {
            throw new EntityExistsException("Customer already exists");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(String email, Customer customer) {
        if(getByEmail(email).isPresent()) {
            customer.setEmail(email);
            return customerRepository.save(customer);
        }
        throw new EntityNotFoundException("Customer not found to update");
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getByEmail(String email) {
       return customerRepository.findById(email);
    }

    @Override
    public void delete(String email) {
        if(getByEmail(email).isPresent()) {
            customerRepository.deleteById(email);
        }else{
            throw new EntityNotFoundException("Customer not found to delete");
        }
    }
}
