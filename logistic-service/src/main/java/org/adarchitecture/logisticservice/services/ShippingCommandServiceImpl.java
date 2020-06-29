package org.adarchitecture.logisticservice.services;

import org.adarchitecture.logisticservice.command.CreateShippingCommand;
import org.adarchitecture.logisticservice.command.RejectedShippingCommand;
import org.adarchitecture.logisticservice.command.RollbackShippingCommand;
import org.adarchitecture.logisticservice.dto.ShippingCreateDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ShippingCommandServiceImpl implements ShippingCommandService {

    private final CommandGateway commandGateway;

    public ShippingCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> shippingOrder(ShippingCreateDTO invoiceCreateDTO) {
        return commandGateway.send(new CreateShippingCommand(invoiceCreateDTO.getShippingId(), invoiceCreateDTO.getOrderId(), invoiceCreateDTO.getBillId(), invoiceCreateDTO.getClientId(), invoiceCreateDTO.getDate(), invoiceCreateDTO.getDirection(), invoiceCreateDTO.getProducts()));
    }

    @Override
    public CompletableFuture<String> shippingRollbackOrder(ShippingCreateDTO invoiceCreateDTO) {
        return commandGateway.send(new RollbackShippingCommand(invoiceCreateDTO.getShippingId(), invoiceCreateDTO.getOrderId(), invoiceCreateDTO.getBillId(), invoiceCreateDTO.getClientId(), invoiceCreateDTO.getDate(), invoiceCreateDTO.getDirection(), invoiceCreateDTO.getProducts()));
    }

    @Override
    public CompletableFuture<String> shippingRejectedOrder(ShippingCreateDTO invoiceCreateDTO) {
        return commandGateway.send(new RejectedShippingCommand(invoiceCreateDTO.getShippingId(), invoiceCreateDTO.getOrderId(), invoiceCreateDTO.getBillId(), invoiceCreateDTO.getClientId(), invoiceCreateDTO.getDate(), invoiceCreateDTO.getDirection(), invoiceCreateDTO.getProducts()));
    }
}
