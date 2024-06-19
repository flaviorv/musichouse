package com.musichouse;

import com.musichouse.model.domain.Customer;
import com.musichouse.model.service.CustomerServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerTests {

    @Autowired
    private CustomerServiceImp customerServiceImp;

    @Test
    @DisplayName("The insert test shoud returns a increased list size")
    void testInsert() {
        int initialList = customerServiceImp.getAll().size();
        Customer customer = new Customer("Larissa Silva", "ls@gmail.com", "123");
        customerServiceImp.save(customer);
        int updatedList = customerServiceImp.getAll().size();
        Assertions.assertEquals(initialList + 1, updatedList);
    }
}
