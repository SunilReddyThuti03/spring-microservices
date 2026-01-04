package com.springbootproject.orders_service.config;

import com.springbootproject.orders_service.ApplicationProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RabbitMQConfig {
  private final ApplicationProperties properties;

  RabbitMQConfig(ApplicationProperties properties) {
    this.properties = properties;
  }

  @Bean
  DirectExchange exchange() {
    return new DirectExchange(properties.orderEventsExchange(), true, false);
  }

  @Bean
  Queue newOrdersQueue() {
    return QueueBuilder.durable(properties.newOrdersQueue()).build();
  }

  @Bean
  Binding newOrdersQueueBinding() {
    return BindingBuilder.bind(newOrdersQueue()).to(exchange()).with(properties.newOrdersQueue());
  }

  @Bean
  Queue deliveredOrdersQueue() {
    return QueueBuilder.durable(properties.deliveredOrdersQueue()).build();
  }

  @Bean
  Binding deliveredOrdersQueueBinding() {
    return BindingBuilder.bind(deliveredOrdersQueue())
        .to(exchange())
        .with(properties.deliveredOrdersQueue());
  }

  @Bean
  Queue cancelledOrdersQueue() {
    return QueueBuilder.durable(properties.cancelledOrdersQueue()).build();
  }

  @Bean
  Binding cancelledOrdersQueueBinding() {
    return BindingBuilder.bind(cancelledOrdersQueue())
        .to(exchange())
        .with(properties.cancelledOrdersQueue());
  }

  @Bean
  Queue errorOrdersQueue() {
    return QueueBuilder.durable(properties.errorOrdersQueue()).build();
  }

  @Bean
  Binding errorOrdersQueueBinding() {
    return BindingBuilder.bind(errorOrdersQueue())
        .to(exchange())
        .with(properties.errorOrdersQueue());
  }

  @Bean
  JacksonJsonMessageConverter jacksonJsonMessageConverter() {
    return new JacksonJsonMessageConverter();
  }

  @Bean
  RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, JacksonJsonMessageConverter converter) {

    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter);
    return rabbitTemplate;
  }

  @Bean
  public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    RabbitAdmin admin = new RabbitAdmin(connectionFactory);
    admin.setAutoStartup(true);
    return admin;
  }

  @Bean
  ApplicationRunner rabbitMQInitializer(
      AmqpAdmin amqpAdmin,
      DirectExchange exchange,
      Queue newOrdersQueue,
      Queue deliveredOrdersQueue,
      Queue cancelledOrdersQueue,
      Queue errorOrdersQueue,
      Binding newOrdersQueueBinding,
      Binding deliveredOrdersQueueBinding,
      Binding cancelledOrdersQueueBinding,
      Binding errorOrdersQueueBinding) {
    return args -> {
      amqpAdmin.declareExchange(exchange);
      amqpAdmin.declareQueue(newOrdersQueue);
      amqpAdmin.declareQueue(deliveredOrdersQueue);
      amqpAdmin.declareQueue(cancelledOrdersQueue);
      amqpAdmin.declareQueue(errorOrdersQueue);
      amqpAdmin.declareBinding(newOrdersQueueBinding);
      amqpAdmin.declareBinding(deliveredOrdersQueueBinding);
      amqpAdmin.declareBinding(cancelledOrdersQueueBinding);
      amqpAdmin.declareBinding(errorOrdersQueueBinding);
    };
  }
}
