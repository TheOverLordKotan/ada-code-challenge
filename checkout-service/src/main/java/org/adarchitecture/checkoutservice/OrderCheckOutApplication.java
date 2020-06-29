package org.adarchitecture.checkoutservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderCheckOutApplication {
	//orderAggregateEventSourcingRepository
	public static void main(String[] args) {
		SpringApplication.run(OrderCheckOutApplication.class, args);
	}

}
