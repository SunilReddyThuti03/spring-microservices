package com.springbootproject.orders_service.clients.catalog;

import com.springbootproject.orders_service.ApplicationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class CatalogServiceClientConfig {
    @Bean
    RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
    @Bean
    RestClient restClient(RestClient.Builder builder,
                          ApplicationProperties properties) {

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        JdkClientHttpRequestFactory requestFactory =
                new JdkClientHttpRequestFactory(httpClient);

        requestFactory.setReadTimeout(Duration.ofSeconds(5));
        System.out.println("hi");
        return builder
                .baseUrl(properties.catalogServiceUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
