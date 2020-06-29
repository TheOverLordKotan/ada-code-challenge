package org.adarchitecture.checkoutservice.services.commands;

import org.adarchitecture.checkoutservice.aggregates.OrderStatus;
import org.adarchitecture.checkoutservice.commands.CreateOrderCommand;
import org.adarchitecture.checkoutservice.commands.OrderCreatedDTO;
import org.adarchitecture.checkoutservice.commands.RejectedOrderCommand;
import org.adarchitecture.checkoutservice.commands.RollbackOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderCheckOutCommandServiceImpl implements OrderCheckOutCommandService {

    private final CommandGateway commandGateway;

    public OrderCheckOutCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> checkOutOrder(OrderCreatedDTO orderCreateDTO) {
        return commandGateway.send(new CreateOrderCommand(UUID.randomUUID().toString(), orderCreateDTO.getClientId(),
                orderCreateDTO.getDate(), orderCreateDTO.getDirection(), orderCreateDTO.getCurrency(), orderCreateDTO.getProducts(), String.valueOf(OrderStatus.CREATED),orderCreateDTO.getRejected(),orderCreateDTO.getRollback()));
    }
    @Override
    public CompletableFuture<String> rollbackCheckOutOrder(OrderCreatedDTO orderCreateDTO) {
        return commandGateway.send(new RollbackOrderCommand(UUID.randomUUID().toString(), orderCreateDTO.getClientId(),
                orderCreateDTO.getDate(), orderCreateDTO.getDirection(), orderCreateDTO.getCurrency(), orderCreateDTO.getProducts(), String.valueOf(OrderStatus.ROLLBACK)));
    }
    @Override
    public CompletableFuture<String> rejectedCheckOutOrder(OrderCreatedDTO orderCreateDTO) {
        return commandGateway.send(new RejectedOrderCommand(UUID.randomUUID().toString(), orderCreateDTO.getClientId(),
                orderCreateDTO.getDate(), orderCreateDTO.getDirection(), orderCreateDTO.getCurrency(), orderCreateDTO.getProducts(), String.valueOf(OrderStatus.REJECTED)));
    }
}
