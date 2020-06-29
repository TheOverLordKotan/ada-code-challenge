package org.adarchitecture.billservice.aggregates;

import java.util.Date;
import java.util.List;

import org.adarchitecture.billservice.command.CreateBillCommand;
import org.adarchitecture.billservice.command.RejectedBillCommand;
import org.adarchitecture.billservice.command.RollbackBillCommand;
import org.adarchitecture.billservice.commands.ProductCreatedDTO;
import org.adarchitecture.billservice.events.BillCreatedEvent;
import org.adarchitecture.billservice.events.RejectedBillEvent;
import org.adarchitecture.billservice.events.RollbackBillEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@SuppressWarnings("unused")
public class InvoiceAggregate {

    @AggregateIdentifier
    private String billId;

  
	private String orderId;

     private String clientId;
    
     private Date date; 
    
     private String direction;
    
     private String currency;
    
     private List<ProductCreatedDTO> products;
    private InvoiceStatus invoiceStatus;

    public InvoiceAggregate() {
    }

    @CommandHandler
    public InvoiceAggregate(CreateBillCommand createInvoiceCommand){
        AggregateLifecycle.apply(new BillCreatedEvent(createInvoiceCommand.billId, createInvoiceCommand.orderId, createInvoiceCommand.clientId, createInvoiceCommand.date, createInvoiceCommand.direction, createInvoiceCommand.currency, createInvoiceCommand.products));
    }

    @EventSourcingHandler
    protected void on(BillCreatedEvent invoiceCreatedEvent){
        this.billId = invoiceCreatedEvent.billId;
        this.orderId = invoiceCreatedEvent.orderId;
		this.clientId = invoiceCreatedEvent.clientId;
		this.date = invoiceCreatedEvent.date;
		this.direction = invoiceCreatedEvent.direction;
		this.currency = invoiceCreatedEvent.currency;
		this.products = invoiceCreatedEvent.products;
        this.invoiceStatus = InvoiceStatus.PAID;

    }

    @CommandHandler
    public void on(RejectedBillCommand rejectedInvoiceCommand) {
        AggregateLifecycle.apply(new RejectedBillEvent(rejectedInvoiceCommand.billId, rejectedInvoiceCommand.orderId, rejectedInvoiceCommand.clientId, rejectedInvoiceCommand.date, rejectedInvoiceCommand.direction, rejectedInvoiceCommand.currency, rejectedInvoiceCommand.products));
    }

    @EventSourcingHandler
    protected void on(RejectedBillEvent invoiceCreatedEvent) {
        this.billId = invoiceCreatedEvent.billId;
        this.orderId = invoiceCreatedEvent.orderId;
		this.clientId = invoiceCreatedEvent.clientId;
		this.date = invoiceCreatedEvent.date;
		this.direction = invoiceCreatedEvent.direction;
		this.currency = invoiceCreatedEvent.currency;
		this.products = invoiceCreatedEvent.products;
		this.invoiceStatus = InvoiceStatus.Bill_REJECTED;
    }

    @CommandHandler
    public void on(RollbackBillCommand rollbackInvoiceCommand) {
        AggregateLifecycle.apply(new RollbackBillEvent(rollbackInvoiceCommand.billId, rollbackInvoiceCommand.orderId, rollbackInvoiceCommand.clientId, rollbackInvoiceCommand.date, rollbackInvoiceCommand.direction, rollbackInvoiceCommand.currency, rollbackInvoiceCommand.products));
    }

    @EventSourcingHandler
    protected void on(RollbackBillEvent invoiceCreatedEvent) {
        this.billId = invoiceCreatedEvent.billId;
        this.orderId = invoiceCreatedEvent.orderId;
		this.clientId = invoiceCreatedEvent.clientId;
		this.date = invoiceCreatedEvent.date;
		this.direction = invoiceCreatedEvent.direction;
		this.currency = invoiceCreatedEvent.currency;
		this.products = invoiceCreatedEvent.products;
        this.invoiceStatus = InvoiceStatus.Bill_REJECTED;
    }

}
