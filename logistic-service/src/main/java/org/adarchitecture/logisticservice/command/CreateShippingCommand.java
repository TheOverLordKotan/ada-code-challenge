package org.adarchitecture.logisticservice.command;

import java.util.Date;
import java.util.List;

import org.adarchitecture.logisticservice.dto.ProductCreatedDTO;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateShippingCommand {

    @TargetAggregateIdentifier
    public final String shippingId;

    public final String orderId;

    public final String billId;


     public final String clientId;
    
     public final Date date; 
    
     public final String direction;
    
    
     public final List<ProductCreatedDTO> products;

	public CreateShippingCommand(String shippingId, String orderId, String billId, String clientId, Date date,
			String direction, List<ProductCreatedDTO> products) {
		super();
		this.shippingId = shippingId;
		this.orderId = orderId;
		this.billId = billId;
		this.clientId = clientId;
		this.date = date;
		this.direction = direction;
		this.products = products;
	}


}
