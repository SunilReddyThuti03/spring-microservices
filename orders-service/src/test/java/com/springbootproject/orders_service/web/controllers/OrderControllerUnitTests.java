package com.springbootproject.orders_service.web.controllers;

import com.springbootproject.orders_service.domain.OrderService;
import com.springbootproject.orders_service.domain.SecurityService;
import com.springbootproject.orders_service.domain.models.CreateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.MediaType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.util.stream.Stream;

import static com.springbootproject.orders_service.testdata.TestDataFactory.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Named.named;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;


@WebMvcTest(OrderController.class)
public class OrderControllerUnitTests {
    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private SecurityService securityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        given(securityService.getLoginUserName()).willReturn("sunil");
    }

    @ParameterizedTest(name = "[{index}]-{0}")
    @MethodSource("createOrderRequestProvider")
    void shouldReturnBadRequestWhenOrderPlayloadIsInvalid(CreateOrderRequest request) throws Exception {
        given(orderService.createOrder(eq("shiva"), any(CreateOrderRequest.class)))
                .willReturn(null);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());


    }

    static Stream<Arguments> createOrderRequestProvider() {
        return Stream.of(
                arguments(named("Order with Invalid Customer", createOrderRequestWithInvalidCustomer())),
                arguments(named("Order with Invalid Delivery Address", createOrderRequestWithInvalidDeliveryAddress())),
                arguments(named("Order with No Items", createOrderRequestWithNoItems())));
    }
}
