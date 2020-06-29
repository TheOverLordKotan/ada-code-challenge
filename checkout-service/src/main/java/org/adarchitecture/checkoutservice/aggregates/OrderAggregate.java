package org.adarchitecture.checkoutservice.aggregates;

import org.adarchitecture.checkoutservice.commands.CreateOrderCommand;
import org.adarchitecture.checkoutservice.commands.ProductCreatedDTO;
import org.adarchitecture.checkoutservice.commands.RejectedOrderCommand;
import org.adarchitecture.checkoutservice.commands.RollbackOrderCommand;
import org.adarchitecture.checkoutservice.commands.UpdateOrderStatusCommand;
import org.adarchitecture.checkoutservice.events.OrderCreatedEvent;
import org.adarchitecture.checkoutservice.events.OrderUpdatedEvent;
import org.adarchitecture.checkoutservice.events.RejectedOrderEvent;
import org.adarchitecture.checkoutservice.events.RollbackOrderEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Aggregate
@SuppressWarnings("unused")
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private BigInteger clientId;
    private Date date;
    private String direction;
    private String currency;
    private List<ProductCreatedDTO> products;
    private OrderStatus orderStatus;    
    private String rejected;
    private String rollback;
    
    

    public OrderAggregate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderAggregate(String orderId, BigInteger clientId, Date date, String direction, String currency,
			List<ProductCreatedDTO> products, OrderStatus orderStatus, String rejected, String rollback) {
		super();
		this.orderId = orderId;
		this.clientId = clientId;
		this.date = date;
		this.direction = direction;
		this.currency = currency;
		this.products = products;
		this.orderStatus = orderStatus;
		this.rejected = rejected;
		this.rollback = rollback;
	}

	@CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        AggregateLifecycle.apply(new OrderCreatedEvent(createOrderCommand.orderId, createOrderCommand.clientId, createOrderCommand.date, createOrderCommand.direction, createOrderCommand.currency, createOrderCommand.products, createOrderCommand.orderStatus, createOrderCommand.rejected, createOrderCommand.rollback));
    }

    @EventSourcingHandler
    protected void on(OrderCreatedEvent orderCreatedEvent){
    	this.orderId = orderCreatedEvent.orderId;
    	this.clientId = orderCreatedEvent.clientId;
    	this.date = orderCreatedEvent.date;
    	this.direction =orderCreatedEvent.direction;
    	this.currency =orderCreatedEvent.currency;
    	this.products =orderCreatedEvent.products;
    	this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
        this.rejected = orderCreatedEvent.rejected;
        this.rollback = orderCreatedEvent.rollback;
    }

    @CommandHandler
    protected void on(UpdateOrderStatusCommand updateOrderStatusCommand){
        AggregateLifecycle.apply(new OrderUpdatedEvent(updateOrderStatusCommand.orderId, updateOrderStatusCommand.orderStatus));
    }

    @EventSourcingHandler
    protected void on(OrderUpdatedEvent orderUpdatedEvent){
    	this.orderId = orderUpdatedEvent.orderId;
        this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
    }

    @CommandHandler
    protected void on(RollbackOrderCommand rollbackOrderCommand){
        AggregateLifecycle.apply(new RollbackOrderEvent(rollbackOrderCommand.orderId, rollbackOrderCommand.orderStatus));
    }

    @EventSourcingHandler
    protected void on(RollbackOrderEvent rollbackOrderEvent){
    	this.orderId = rollbackOrderEvent.orderId;
        this.orderStatus = OrderStatus.valueOf(rollbackOrderEvent.orderStatus);
    }

    @CommandHandler
    protected void on(RejectedOrderCommand createOrderCommand){
        AggregateLifecycle.apply(new RejectedOrderEvent(createOrderCommand.orderId, createOrderCommand.clientId, createOrderCommand.date, createOrderCommand.direction, createOrderCommand.currency, createOrderCommand.products, createOrderCommand.orderStatus));
    }

    @EventSourcingHandler
    protected void on(RejectedOrderEvent rollbackOrderEvent){
    	this.orderId = rollbackOrderEvent.orderId;
    	this.clientId = rollbackOrderEvent.clientId;
    	this.date = rollbackOrderEvent.date;
    	this.direction =rollbackOrderEvent.direction;
    	this.currency =rollbackOrderEvent.currency;
    	this.products =rollbackOrderEvent.products;
    	 this.orderStatus = OrderStatus.valueOf(rollbackOrderEvent.orderStatus);
       
    }

}
