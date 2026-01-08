package com.springbootproject.orders_service.domain;

import com.springbootproject.orders_service.clients.catalog.Product;
import com.springbootproject.orders_service.clients.catalog.ProductServiceClient;
import com.springbootproject.orders_service.domain.models.CreateOrderRequest;
import com.springbootproject.orders_service.domain.models.OrderItem;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {
  // private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

  private final ProductServiceClient client;

  OrderValidator(ProductServiceClient client) {
    this.client = client;
  }

  void validate(CreateOrderRequest request) {
    Set<OrderItem> items = request.orderItems();

    for (OrderItem item : items) {
      Product product =
          client
              .getProductByCode(item.code())
              .orElseThrow(() -> new InvalidOrderException("Invalid product code"));
      if (item.price().compareTo(product.price()) != 0) {
        // log.error("Product price not matching.Actual price:{}, Received price:{}",
        // product.price(), item.price());
        throw new InvalidOrderException("Product price not matching");
      }
    }
  }
}
