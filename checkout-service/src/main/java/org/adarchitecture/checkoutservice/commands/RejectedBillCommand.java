package org.adarchitecture.checkoutservice.commands;

import java.math.BigInteger;
import java.util.List;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RejectedBillCommand {

    @TargetAggregateIdentifier
    public final String billId;

    public final String orderId;
    
    public final BigInteger clientId;

    public final List<ProductCreatedDTO> products;

    public final String rejected;

    public RejectedBillCommand(String BillId, String orderId, BigInteger clientId, List<ProductCreatedDTO> products,String rejected) {
        this.billId = BillId;
        this.orderId = orderId;
        this.clientId =clientId;
        this.products = products;
        this.rejected =rejected;
    }
}
