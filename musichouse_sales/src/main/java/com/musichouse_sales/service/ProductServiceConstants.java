package com.musichouse_sales.service;

public class ProductServiceConstants {

    public static final String REQUEST_ERROR ="The 'Product service' must be running and the required 'model' must exist in the database";

    public static final String REQUEST_SUCCESS = "Product service request completed successfully";

    public static final String SERVICE_URL = "http://localhost:8080/product/";

    public static final String SERVICE_URL (String model){
        return String.format(SERVICE_URL+model);
    }



}
