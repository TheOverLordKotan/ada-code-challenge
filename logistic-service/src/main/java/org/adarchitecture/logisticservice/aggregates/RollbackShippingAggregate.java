package org.adarchitecture.logisticservice.aggregates;

import java.util.Date;
import java.util.List;

import org.adarchitecture.logisticservice.command.RollbackShippingCommand;
import org.adarchitecture.logisticservice.dto.ProductCreatedDTO;
import org.adarchitecture.logisticservice.event.RollbackShippedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@SuppressWarnings("unused")
public class RollbackShippingAggregate {

    @AggregateIdentifier
    private String shippingId;

    private String orderId;

    private String billId;


    private String clientId;
   
    private Date date; 
   
    private String direction;
   
   
    private List<ProductCreatedDTO> products;
    

    public RollbackShippingAggregate() {
    }

    @CommandHandler
    public RollbackShippingAggregate(RollbackShippingCommand rollbackShippingCommand){
        AggregateLifecycle.apply(new RollbackShippedEvent(rollbackShippingCommand.shippingId, rollbackShippingCommand.orderId, rollbackShippingCommand.billId, rollbackShippingCommand.clientId, rollbackShippingCommand.date, rollbackShippingCommand.direction, rollbackShippingCommand.products));
    }

    @EventSourcingHandler
    protected void on(RollbackShippedEvent orderShippedEvent){
        this.shippingId = orderShippedEvent.shippingId;
        this.orderId = orderShippedEvent.orderId;
        this.billId = orderShippedEvent.billId;
        this.clientId =orderShippedEvent.clientId;
        this.date =orderShippedEvent.date;
        this.direction =orderShippedEvent.direction;
        this.products =orderShippedEvent.products;
    }
}
