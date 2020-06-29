package org.adarchitecture.checkoutservice.events;

public class RollbackOrderEvent {

    public final String orderId;

    public final String orderStatus;

    public RollbackOrderEvent(String orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}
