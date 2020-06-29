package org.adarchitecture.logisticservice.aggregates;

import java.util.Date;
import java.util.List;

import org.adarchitecture.logisticservice.command.CreateShippingCommand;
import org.adarchitecture.logisticservice.dto.ProductCreatedDTO;
import org.adarchitecture.logisticservice.event.OrderShippedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@SuppressWarnings("unused")
public class ShippingAggregate {

    @AggregateIdentifier
    private String shippingId;

    
	private String orderId;

    private String billId;


    private String clientId;
   
    private Date date; 
   
    private String direction;
   
    private List<ProductCreatedDTO> products;

    public ShippingAggregate() {
    }

    @CommandHandler
    public ShippingAggregate(CreateShippingCommand createShippingCommand){
        AggregateLifecycle.apply(new OrderShippedEvent(createShippingCommand.shippingId, createShippingCommand.orderId, createShippingCommand.billId, createShippingCommand.clientId, createShippingCommand.date, createShippingCommand.direction, createShippingCommand.products));
    }

    @EventSourcingHandler
    protected void on(OrderShippedEvent orderShippedEvent){
        this.shippingId = orderShippedEvent.shippingId;
        this.orderId = orderShippedEvent.orderId;
        this.billId = orderShippedEvent.billId;
        this.clientId =orderShippedEvent.clientId;
        this.date =orderShippedEvent.date;
        this.direction =orderShippedEvent.direction;
        this.products =orderShippedEvent.products;
    }
}
