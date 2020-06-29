package org.adarchitecture.checkoutservice.aggregates;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.adarchitecture.checkoutservice.commands.CreateShippingCommand;
import org.adarchitecture.checkoutservice.commands.ProductCreatedDTO;
import org.adarchitecture.checkoutservice.commands.RejectedShippingCommand;
import org.adarchitecture.checkoutservice.commands.RollbackShippingCommand;
import org.adarchitecture.checkoutservice.events.OrderShippedEvent;
import org.adarchitecture.checkoutservice.events.RejectedShippingEvent;
import org.adarchitecture.checkoutservice.events.RollbackShippingEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ShippingAggregate {

    @AggregateIdentifier
    private String shippingId;
    private String orderId;
    private String billId;
    private BigInteger clientId;
    private Date date;
    private String direction;
    private String currency;
    private List<ProductCreatedDTO> products;
    private String rejected;
    private String rollback;
   

    public ShippingAggregate() {
    }

    @CommandHandler
    public ShippingAggregate(CreateShippingCommand createShippingCommand){
        AggregateLifecycle.apply(new OrderShippedEvent(createShippingCommand.shippingId, createShippingCommand.orderId, createShippingCommand.billId, createShippingCommand.clientId, createShippingCommand.date, createShippingCommand.direction, createShippingCommand.currency, createShippingCommand.products, createShippingCommand.rejected, createShippingCommand.rollback));
    }

    @EventSourcingHandler
    protected void on(OrderShippedEvent orderShippedEvent){
        this.shippingId = orderShippedEvent.shippingId;
        this.orderId = orderShippedEvent.orderId;
        this.billId = orderShippedEvent.billId;
        this.clientId = orderShippedEvent.clientId;
		this.date = orderShippedEvent.date;
		this.direction =orderShippedEvent.direction;
		this.currency =orderShippedEvent.currency;
		this.products =orderShippedEvent.products;
        this.rejected = orderShippedEvent.rejected;
        this.rollback = orderShippedEvent.rollback;
    }

    @CommandHandler
    protected void on(RejectedShippingCommand createOrderCommand){
        AggregateLifecycle.apply(new RejectedShippingEvent(createOrderCommand.shippingId, createOrderCommand.orderId, createOrderCommand.billId, createOrderCommand.clientId, createOrderCommand.date, createOrderCommand.direction, createOrderCommand.currency, createOrderCommand.products));
    }

    @EventSourcingHandler
    protected void on(RejectedShippingEvent rejectedShippingEvent){
        this.shippingId = rejectedShippingEvent.shippingId;
        this.billId = rejectedShippingEvent.billId;
        this.orderId = rejectedShippingEvent.orderId;
        this.clientId = rejectedShippingEvent.clientId;
		this.date = rejectedShippingEvent.date;
		this.direction =rejectedShippingEvent.direction;
		this.currency =rejectedShippingEvent.currency;
		this.products =rejectedShippingEvent.products;
    }

    @CommandHandler
    protected void on(RollbackShippingCommand rollbackShippingCommand){
        AggregateLifecycle.apply(new RollbackShippingEvent(rollbackShippingCommand.shippingId, rollbackShippingCommand.orderId, rollbackShippingCommand.billId, rollbackShippingCommand.clientId, rollbackShippingCommand.date, rollbackShippingCommand.direction, rollbackShippingCommand.currency, rollbackShippingCommand.products, rollbackShippingCommand.rollback));
    }

    @EventSourcingHandler
    protected void on(RollbackShippingEvent rollbackShippingEvent){
        this.shippingId = rollbackShippingEvent.shippingId;
        this.billId = rollbackShippingEvent.billId;
        this.orderId = rollbackShippingEvent.orderId;
        this.clientId = rollbackShippingEvent.clientId;
		this.date = rollbackShippingEvent.date;
		this.direction =rollbackShippingEvent.direction;
		this.currency =rollbackShippingEvent.currency;
		this.products =rollbackShippingEvent.products;
    }

}
