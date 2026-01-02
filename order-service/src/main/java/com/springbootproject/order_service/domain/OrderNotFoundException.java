package com.springbootproject.order_service.domain;

import org.hibernate.query.Order;

public class OrderNotFoundException extends  RuntimeException{
    public OrderNotFoundException(String message){
        super(message);
    }

    public OrderNotFoundException forOrderNumber(String orderNumber){
        return  new OrderNotFoundException("Order with Number "+orderNumber +" is not found!!");
    }
}
