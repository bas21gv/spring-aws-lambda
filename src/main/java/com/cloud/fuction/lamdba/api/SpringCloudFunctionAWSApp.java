package com.cloud.fuction.lamdba.api;

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
    public Function<String, List<Order>> findByName(){
        return (input) -> orderRepository.prepareOrders().stream().filter(order -> order.getName().equals(input)).collect(Collectors.toList());
    }
}
