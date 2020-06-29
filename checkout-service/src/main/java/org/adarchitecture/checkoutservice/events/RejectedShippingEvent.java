package org.adarchitecture.checkoutservice.events;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.adarchitecture.checkoutservice.commands.ProductCreatedDTO;

public class RejectedShippingEvent {

    public final String shippingId;
    public final String billId;
    public final String orderId;
	public final BigInteger clientId;
	public final Date date;
	public final String direction;
	public final String currency;
	public final List<ProductCreatedDTO> products;   


	public RejectedShippingEvent(String shippingId,String orderId, String billId, BigInteger clientId, Date date, String direction,String currency,List<ProductCreatedDTO> products) {
		this.shippingId = shippingId;
		this.orderId = orderId;
		this.billId = billId;
		this.clientId = clientId;
		this.date = date;
		this.direction =direction;
		this.currency =currency;
		this.products =products;
	}
}
