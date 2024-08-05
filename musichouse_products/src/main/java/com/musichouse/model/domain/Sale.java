package com.musichouse.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.NumberFormat;
import java.util.*;

@Data@AllArgsConstructor@NoArgsConstructor
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date date;
    private String price;
    @ManyToOne
    private Customer customer;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
    }

    public String totalPrice(){
        float totalPrice = 0;
        for(Product p: products) {
            totalPrice += p.getPrice();
        }
        price = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPrice);
        return price;
    }


}
