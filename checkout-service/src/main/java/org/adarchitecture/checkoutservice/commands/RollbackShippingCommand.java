package org.adarchitecture.checkoutservice.commands;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RollbackShippingCommand {

    @TargetAggregateIdentifier
    public final String shippingId;
    public final String orderId;
    public final String billId;
    public final BigInteger clientId;
    public final Date date;
    public final String direction;
    public final String currency;
    public final List<ProductCreatedDTO> products;    
    public final String rollback;

    public RollbackShippingCommand(String shippingId, String orderId, String BillId, BigInteger clientId, Date date, String direction, String currency,List<ProductCreatedDTO> products, String rollback) {
        this.shippingId = shippingId;
        this.orderId = orderId;
        this.billId = BillId;
    	this.clientId = clientId;
    	this.date = date;
    	this.direction =direction;
    	this.currency =currency;
    	this.products =products;
    	this.rollback = rollback;
    }
}
