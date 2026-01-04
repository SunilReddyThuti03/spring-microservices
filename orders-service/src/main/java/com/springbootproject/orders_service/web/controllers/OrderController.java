package com.springbootproject.orders_service.web.controllers;

import com.springbootproject.orders_service.domain.OrderService;
import com.springbootproject.orders_service.domain.SecurityService;
import com.springbootproject.orders_service.domain.models.CreateOrderRequest;
import com.springbootproject.orders_service.domain.models.CreateOrderResponse;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
   // private static final logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    OrderController(OrderService orderService, SecurityService securityService){
        this.orderService=orderService;
        this.securityService= securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request){
        String userName = securityService.getLoginUserName();
       // log.info("creating order for user: {}", userName);
        return orderService.createOrder(userName, request);
    }
}
