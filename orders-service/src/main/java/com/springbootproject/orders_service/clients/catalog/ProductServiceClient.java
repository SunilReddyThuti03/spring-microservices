package com.springbootproject.orders_service.clients.catalog;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {
  // private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);

  private final RestClient restClient;

  ProductServiceClient(RestClient restClient) {
    this.restClient = restClient;
  }

  public Optional<Product> getProductByCode(String code) {
    // log.info("Fetching product for code :{}", code);
    var product = restClient.get().uri("/api/products/{code}", code).retrieve().body(Product.class);
    return Optional.ofNullable(product);
  }
}
