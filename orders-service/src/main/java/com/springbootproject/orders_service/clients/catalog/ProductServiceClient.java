package com.springbootproject.orders_service.clients.catalog;

import java.util.Optional;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.micrometer.annotation.Timer;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {
  // private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);

  private final RestClient restClient;

  ProductServiceClient(RestClient restClient) {
    this.restClient = restClient;
  }

  @CircuitBreaker(name = "catalog-service")
  @Timer(name="catalog-service")
  @Retry(name = "catalog-service", fallbackMethod ="getProductByCodeFallback" )
  public Optional<Product> getProductByCode(String code) {
    // log.info("Fetching product for code :{}", code);
    var product = restClient.get().uri("/api/products/{code}", code).retrieve().body(Product.class);
    return Optional.ofNullable(product);
  }

  Optional<Product> getProductByCodeFallback(String code, Throwable t) {
        //log.info("catalog-service get product by code fallback: code:{}, Error: {} ", code, t.getMessage());
        return Optional.empty();
    }
}
