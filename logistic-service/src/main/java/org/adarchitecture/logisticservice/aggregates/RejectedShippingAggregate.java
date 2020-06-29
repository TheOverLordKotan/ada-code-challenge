package org.adarchitecture.logisticservice.aggregates;

import java.util.Date;
import java.util.List;

import org.adarchitecture.logisticservice.command.RejectedShippingCommand;
import org.adarchitecture.logisticservice.dto.ProductCreatedDTO;
import org.adarchitecture.logisticservice.event.RejectedShippedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@SuppressWarnings("unused")
public class RejectedShippingAggregate {

    @AggregateIdentifier
    private String shippingId;

    private String orderId;

    private String billId;


    private String clientId;
   
    private Date date; 
   
    private String direction;
   
   
    private List<ProductCreatedDTO> products;

    public RejectedShippingAggregate() {
    }

    @CommandHandler
    public RejectedShippingAggregate(RejectedShippingCommand rejectedShippingCommand){
        AggregateLifecycle.apply(new RejectedShippedEvent(rejectedShippingCommand.shippingId, rejectedShippingCommand.orderId, rejectedShippingCommand.billId, rejectedShippingCommand.clientId,rejectedShippingCommand.date,rejectedShippingCommand.direction,rejectedShippingCommand.products));
    }

    @EventSourcingHandler
    protected void on(RejectedShippedEvent rejectedShippedEvent){
        this.shippingId = rejectedShippedEvent.shippingId;
        this.orderId = rejectedShippedEvent.orderId;
        this.billId = rejectedShippedEvent.billId;
        this.clientId =rejectedShippedEvent.clientId;
        this.date =rejectedShippedEvent.date;
        this.direction =rejectedShippedEvent.direction;
        this.products =rejectedShippedEvent.products;
    }
}
