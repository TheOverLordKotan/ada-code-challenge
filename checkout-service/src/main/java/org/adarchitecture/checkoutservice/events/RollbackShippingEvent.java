package org.adarchitecture.checkoutservice.events;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.adarchitecture.checkoutservice.commands.ProductCreatedDTO;

public class RollbackShippingEvent {

    public final String shippingId;
    public final String billId;
    public final String orderId;
	public final BigInteger clientId;
	public final Date date;
	public final String direction;
	public final String currency;
	public final List<ProductCreatedDTO> products;  
	public final String rollback;


	public RollbackShippingEvent(String shippingId,String billId,String orderId, BigInteger clientId, Date date, String direction,String currency,List<ProductCreatedDTO> products,String rollback) {
		this.shippingId = shippingId;
		this.billId = billId;
		this.orderId = orderId;
		this.clientId = clientId;
		this.date = date;
		this.direction =direction;
		this.currency =currency;
		this.products =products;
		this.rollback = rollback;
	}
}
