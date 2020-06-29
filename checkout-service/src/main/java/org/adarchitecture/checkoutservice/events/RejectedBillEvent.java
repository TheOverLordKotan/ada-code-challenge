package org.adarchitecture.checkoutservice.events;

import java.math.BigInteger;
import java.util.List;

import org.adarchitecture.checkoutservice.commands.ProductCreatedDTO;

public class RejectedBillEvent {

    public final String billId;

    public final String orderId;
    
    public final BigInteger clientId;

    public final List<ProductCreatedDTO> products;

    public RejectedBillEvent(String BillId, String orderId,BigInteger clientId, List<ProductCreatedDTO> products) {
        this.billId = BillId;
        this.orderId = orderId;
        this.products = products;
        this.clientId = clientId;
    }
}
