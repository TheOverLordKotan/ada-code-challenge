package org.adarchitecture.billservice.services;

import org.adarchitecture.billservice.commands.InvoiceCreateDTO;
import org.adarchitecture.billservice.commands.RejectedInvoiceDTO;

import java.util.concurrent.CompletableFuture;

public interface InvoiceCommandService {

    CompletableFuture<String> BillCreated(InvoiceCreateDTO orderCreateDTO);

    CompletableFuture<String> rejectedOrder(RejectedInvoiceDTO orderCreateDTO);

    CompletableFuture<String> rollbackBill(RejectedInvoiceDTO orderCreateDTO);

}
