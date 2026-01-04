package com.springbootproject.orders_service.domain;

import com.springbootproject.orders_service.domain.models.CreateOrderRequest;
import com.springbootproject.orders_service.domain.models.CreateOrderResponse;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    //private final logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest request){
        OrderEntity newOrder =  OrderMapper.convertToEntity(request);
        newOrder.setUserName(userName);
        OrderEntity saveOrder = this.orderRepository.save(newOrder);
       // log.info("Created Order with orderNumber: {}",saveOrder.getOrderNumber());
        return new CreateOrderResponse(saveOrder.getOrderNumber());

    }

}
