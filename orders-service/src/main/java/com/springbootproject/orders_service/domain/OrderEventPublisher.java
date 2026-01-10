package com.springbootproject.orders_service.domain;

import com.springbootproject.orders_service.ApplicationProperties;
import com.springbootproject.orders_service.domain.models.OrderCancelledEvent;
import com.springbootproject.orders_service.domain.models.OrderCreatedEvent;
import com.springbootproject.orders_service.domain.models.OrderDeliveredEvent;
import com.springbootproject.orders_service.domain.models.OrderErrorEvent;
import org.springframework.amqp.rabbit.core.RabbitAdminEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties properties){
        this.rabbitTemplate = rabbitTemplate;
        this.properties =properties;
    }

    public void publish(OrderCreatedEvent event){
        this.send(properties.newOrdersQueue(),event);
    }

    private void send(String routingKey, Object payload){
        rabbitTemplate.convertAndSend(properties.orderEventsExchange(), routingKey, payload);
    }
    public void publish(OrderDeliveredEvent event) {
        this.send(properties.deliveredOrdersQueue(), event);
    }

    public void publish(OrderCancelledEvent event) {
        this.send(properties.cancelledOrdersQueue(), event);
    }

    public void publish(OrderErrorEvent event) {
        this.send(properties.errorOrdersQueue(), event);
    }
}
