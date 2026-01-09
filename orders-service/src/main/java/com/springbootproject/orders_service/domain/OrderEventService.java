package com.springbootproject.orders_service.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springbootproject.orders_service.domain.models.OrderCreatedEvent;
import com.springbootproject.orders_service.domain.models.OrderEventType;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Transactional
public class OrderEventService {
    //private static final Logger log = LoggerFactory.getLogger(OrderEventService.class);

    private final OrderEventRepository orderEventRepository;
    private final OrderEventPublisher orderEventPublisher;
    private final ObjectMapper objectMapper;

    OrderEventService(OrderEventPublisher orderEventPublisher, OrderEventRepository orderEventRepository,ObjectMapper objectMapper){
        this.orderEventRepository = orderEventRepository;
        this.orderEventPublisher = orderEventPublisher;
        this.objectMapper = objectMapper;
    }

    void save(OrderCreatedEvent event){
        OrderEventEntity orderEvent = new OrderEventEntity();
        orderEvent.setEventId(event.eventId());
        orderEvent.setEventType(OrderEventType.ORDER_CREATED);
        orderEvent.setOrderNumber(event.orderNumber());
        orderEvent.setCreatedAt(event.createdAt());
        orderEvent.setPayload(toJsonPayload(event));
        this.orderEventRepository.save(orderEvent);

    }

    private String toJsonPayload(Object object){
        try{
            return  objectMapper.writeValueAsString(object);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private<T> T fromJsonPayload(String json, Class<T> type){
        try{
            return objectMapper.readValue(json, type);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
