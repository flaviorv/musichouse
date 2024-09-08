package com.musichouse_sales.model.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Slf4j
@Document(collection = "sales")
public class Sale {
    @Id
    private String id;
    private Date date;
    private BigDecimal totalPrice = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
    private Status status;
    private List<Product> products = new ArrayList<>();

    public void setStatus(Status status){
        this.status = status;
    }

    public void addProduct(Product product){
        if(product.getQuantity() >= 1) {
            Boolean exists = addExistent(product);
            if (!exists) {
                product.setQuantity(1);
                products.add(product);
            }
            sumProductPrice(product.getPrice());
        }else {
            throw new RuntimeException("Insufficient quantity of the product in the stock.");
        }
    }

    public Boolean addExistent(Product productToAdd){
        for(Product product : products){
            if(product.getModel().equals(productToAdd.getModel())){
                product.setQuantity(product.getQuantity() + 1);
                return true;
            }
        }
        return false;
    }

    public void sumProductPrice(BigDecimal productPrice){
        productPrice = productPrice.setScale(2, RoundingMode.HALF_EVEN);
        totalPrice = totalPrice.add(productPrice);
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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Price: ").append(totalPrice).append("\n");
        sb.append("Products:\n");
        products.forEach(sb::append);
        return sb.toString();
    }

}
