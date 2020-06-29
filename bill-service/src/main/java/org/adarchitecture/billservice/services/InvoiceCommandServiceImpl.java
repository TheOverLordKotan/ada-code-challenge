package org.adarchitecture.billservice.services;

import org.adarchitecture.billservice.command.CreateBillCommand;
import org.adarchitecture.billservice.command.RejectedBillCommand;
import org.adarchitecture.billservice.command.RollbackBillCommand;
import org.adarchitecture.billservice.commands.InvoiceCreateDTO;
import org.adarchitecture.billservice.commands.RejectedInvoiceDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {

    private final CommandGateway commandGateway;

    public InvoiceCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> BillCreated(InvoiceCreateDTO invoiceCreateDTO) {
        return commandGateway.send(new CreateBillCommand(invoiceCreateDTO.getBillId(), invoiceCreateDTO.getOrderId(), invoiceCreateDTO.getClientId(), invoiceCreateDTO.getDate(), invoiceCreateDTO.getDirection(), invoiceCreateDTO.getCurrency(),invoiceCreateDTO.getProducts()));
    }

    @Override
    public CompletableFuture<String> rejectedOrder(RejectedInvoiceDTO invoiceCreateDTO) {
        return commandGateway.send(new RejectedBillCommand(invoiceCreateDTO.getBillId(), invoiceCreateDTO.getOrderId(), invoiceCreateDTO.getClientId(), invoiceCreateDTO.getDate(), invoiceCreateDTO.getDirection(), invoiceCreateDTO.getCurrency(),invoiceCreateDTO.getProducts()));
    }

    @Override
    public CompletableFuture<String> rollbackBill(RejectedInvoiceDTO invoiceCreateDTO) {
        return commandGateway.send(new RollbackBillCommand(invoiceCreateDTO.getBillId(), invoiceCreateDTO.getOrderId(), invoiceCreateDTO.getClientId(), invoiceCreateDTO.getDate(), invoiceCreateDTO.getDirection(), invoiceCreateDTO.getCurrency(),invoiceCreateDTO.getProducts()));
    }
}
