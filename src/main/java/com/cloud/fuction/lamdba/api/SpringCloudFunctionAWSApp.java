package com.cloud.fuction.lamdba.api;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.cloud.fuction.lamdba.api.model.Order;
import com.cloud.fuction.lamdba.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringCloudFunctionAWSApp {

    @Autowired
    private OrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFunctionAWSApp.class, args);
    }

    @Bean
    public Supplier<List<Order>> getOrders(){
        return () -> orderRepository.prepareOrders();
    }

    @Bean
    public Function<APIGatewayProxyRequestEvent, List<Order>> findByName(){
        return (requestEvent) -> orderRepository.prepareOrders()
                .stream()
                .filter(order -> order.getName().equals(requestEvent.getQueryStringParameters().get("orderName")))
                .collect(Collectors.toList());
    }
}
