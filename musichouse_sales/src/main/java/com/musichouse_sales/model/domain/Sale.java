package com.musichouse_sales.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data@AllArgsConstructor@NoArgsConstructor

public class Sale {

    private int id;
    private Date date;
    private String price;
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
    }

    public float totalPrice(){
        float totalPrice = 0;
        for(Product p: products) {
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }

    public String setPrice(float totalPrice){
        price = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalPrice);
        return price;
    }

    public void setCurrentDate(){
        this.date =  new Date();
    }

    public Date getDate() {
        if(this.date == null) {
            throw new NullPointerException("Date is null");
        }
        return this.date;
    }

}
