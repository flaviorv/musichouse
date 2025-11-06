package com.musichouse_sales.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;



@Slf4j
@Document(collection = "sales")
@EqualsAndHashCode(callSuper = true)
@Data@NoArgsConstructor@SuperBuilder
public class Sale extends ShoppingCart {
    @Id
    private String id;
    private Date date;
    private Status status;

    public void setCurrentDate(){
        this.date =  new Date();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Total Price: ").append(super.getTotalPrice()).append("\n");
        sb.append("Products:\n");
        super.getItems().forEach((item)-> {
            sb.append("Model: ").append(item.model()).append("\n");
            sb.append("Price: ").append(item.price()).append("\n");
            sb.append("Stock Quantity: ").append(item.stockQuantity()).append("\n");
            sb.append("Quantity Chosen: ").append(item.quantityChosen()).append("\n");
        });
        return sb.toString();
    }

}
