package com.musichouse_sales.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data@AllArgsConstructor@NoArgsConstructor
@Document(collection = "sales")
public class Sale {
    @Id
    private String id;
    private Date date;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private List<String> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product.getModel());
        sumProductPrice(product.getPrice());
    }

    public void sumProductPrice(BigDecimal productPrice){
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

    public BigDecimal getTotalPrice() {
        return totalPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice.setScale(2, RoundingMode.HALF_EVEN);
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
