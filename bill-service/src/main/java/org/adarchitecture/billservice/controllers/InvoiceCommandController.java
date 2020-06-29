package org.adarchitecture.billservice.controllers;

import org.adarchitecture.billservice.commands.InvoiceCreateDTO;
import org.adarchitecture.billservice.commands.RejectedInvoiceDTO;
import org.adarchitecture.billservice.services.InvoiceCommandService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "api")
@Api(value = "Bill Commands", description = "Bill Commands Related Endpoints", tags = "Bill Commands")
public class InvoiceCommandController {

    private InvoiceCommandService invoiceCommandService;

    public InvoiceCommandController(InvoiceCommandService invoiceCommandService) {
        this.invoiceCommandService = invoiceCommandService;
    }

    @RequestMapping(value = "/Bill", method = RequestMethod.POST)
    public CompletableFuture<String> createBill(@RequestBody InvoiceCreateDTO invoiceCreateDTO){
        return invoiceCommandService.BillCreated(invoiceCreateDTO);
    }

    @RequestMapping(value = "/Bill-rejected", method = RequestMethod.POST)
    public CompletableFuture<String> rejectedOrder(@RequestBody RejectedInvoiceDTO invoiceCreateDTO) {
        return invoiceCommandService.rejectedOrder(invoiceCreateDTO);
    }

    @RequestMapping(value = "/Bill-rollback", method = RequestMethod.POST)
    public CompletableFuture<String> rollbackBill(@RequestBody RejectedInvoiceDTO invoiceCreateDTO) {
        return invoiceCommandService.rollbackBill(invoiceCreateDTO);
    }

}
