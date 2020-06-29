package org.adarchitecture.checkoutservice.events;

import java.math.BigInteger;
import java.util.List;

import org.adarchitecture.checkoutservice.commands.ProductCreatedDTO;

public class RollbackBillEvent {

    public final String billId;

    public final String orderId;
    public final BigInteger clientId;
    public final List<ProductCreatedDTO> products;    
    public final String rejected;


    public RollbackBillEvent(String billId,String orderId, BigInteger clientId,List<ProductCreatedDTO> products, String rejected) {
    	this.billId = billId;
    	this.orderId = orderId;
    	this.clientId = clientId;
    	this.products =products;
        this.rejected = rejected;
    }
}
