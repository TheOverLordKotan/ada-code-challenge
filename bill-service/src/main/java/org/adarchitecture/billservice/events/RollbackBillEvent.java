package org.adarchitecture.billservice.events;

import java.util.Date;
import java.util.List;

import org.adarchitecture.billservice.commands.ProductCreatedDTO;

public class RollbackBillEvent {

    public final String billId;

    public final String orderId;

     public final String clientId;
    
     public final Date date; 
    
     public final String direction;
    
     public final String currency;
    
     public final List<ProductCreatedDTO> products;

	public RollbackBillEvent(String billId, String orderId, String clientId, Date date, String direction,
			String currency, List<ProductCreatedDTO> products) {
		super();
		this.billId = billId;
		this.orderId = orderId;
		this.clientId = clientId;
		this.date = date;
		this.direction = direction;
		this.currency = currency;
		this.products = products;
	}
     
     
	
}
