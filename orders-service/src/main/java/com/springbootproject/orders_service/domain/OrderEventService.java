package com.springbootproject.orders_service.domain;

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
}
