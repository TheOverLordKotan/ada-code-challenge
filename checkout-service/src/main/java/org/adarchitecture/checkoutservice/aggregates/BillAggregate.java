package org.adarchitecture.checkoutservice.aggregates;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.adarchitecture.checkoutservice.commands.CreateBillCommand;
import org.adarchitecture.checkoutservice.commands.ProductCreatedDTO;
import org.adarchitecture.checkoutservice.commands.RejectedBillCommand;
import org.adarchitecture.checkoutservice.commands.RollbackBillCommand;
import org.adarchitecture.checkoutservice.events.BillCreatedEvent;
import org.adarchitecture.checkoutservice.events.RejectedBillEvent;
import org.adarchitecture.checkoutservice.events.RollbackBillEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@SuppressWarnings("unused")
public class BillAggregate {

    @AggregateIdentifier
    private String billId;


	private String orderId;

    private BigInteger clientId;
    private Date date;
    private String direction;
    private String currency;
    private List<ProductCreatedDTO> products;
    private InvoiceStatus invoiceStatus;

    private String rejected;

    private String rollback;

    public BillAggregate() {
    }

    @CommandHandler
    public BillAggregate(CreateBillCommand createBillCommand){
        AggregateLifecycle.apply(new BillCreatedEvent(createBillCommand.billId, createBillCommand.orderId,createBillCommand.clientId,createBillCommand.date,createBillCommand.direction,createBillCommand.currency,createBillCommand.products,createBillCommand.invoiceStatus, createBillCommand.rejected, createBillCommand.rollback));
    }

    @EventSourcingHandler
    protected void on(BillCreatedEvent BillCreatedEvent){
        this.billId = BillCreatedEvent.billId;
        this.orderId = BillCreatedEvent.orderId;
        this.invoiceStatus = InvoiceStatus.PAID;
		this.clientId = BillCreatedEvent.clientId;
		this.date = BillCreatedEvent.date;
		this.direction =BillCreatedEvent.direction;
		this.currency =BillCreatedEvent.currency;
		this.products =BillCreatedEvent.products;
        this.rejected = BillCreatedEvent.rejected;
        this.rollback = BillCreatedEvent.rollback;
    }

    @CommandHandler
    protected void on(RejectedBillCommand rejectedBillCommand) {
        AggregateLifecycle.apply(new RejectedBillEvent(rejectedBillCommand.billId, rejectedBillCommand.orderId,rejectedBillCommand.clientId, rejectedBillCommand.products));
    }

    @EventSourcingHandler
    protected void on(RejectedBillEvent rejectedBillEvent){
        this.billId = rejectedBillEvent.billId;
        this.orderId = rejectedBillEvent.orderId;
		this.products =rejectedBillEvent.products;
    }

    @CommandHandler
    protected void on(RollbackBillCommand rollbackBillCommand){
        AggregateLifecycle.apply(new RollbackBillEvent(rollbackBillCommand.billId, rollbackBillCommand.orderId, rollbackBillCommand.clientId, rollbackBillCommand.products, rollbackBillCommand.rollback));
        
    }

    @EventSourcingHandler
    protected void on(RollbackBillEvent rollbackBillEvent){
        this.billId = rollbackBillEvent.billId;
        this.orderId = rollbackBillEvent.orderId;
		this.clientId = rollbackBillEvent.clientId;
		this.products =rollbackBillEvent.products;
    }
}
