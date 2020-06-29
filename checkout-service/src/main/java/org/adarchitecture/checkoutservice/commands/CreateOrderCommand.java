package org.adarchitecture.checkoutservice.commands;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateOrderCommand {

    @TargetAggregateIdentifier    
    public final String orderId;
    public final BigInteger clientId;
    public final Date date;
    public final String direction;
    public final String currency;
    public final List<ProductCreatedDTO> products;
    public final String orderStatus;    
    public final String rejected;
    public final String rollback;

    public CreateOrderCommand(String orderId, BigInteger clientId, Date date, String direction,String currency,List<ProductCreatedDTO> products, String orderStatus, String rejected, String rollback) {
    	this.orderId = orderId;
    	this.clientId = clientId;
    	this.date = date;
    	this.direction =direction;
    	this.currency =currency;
    	this.products =products;
    	this.orderStatus= orderStatus;
        this.rejected = rejected;
        this.rollback = rollback;
    }
}
