package org.adarchitecture.checkoutservice.commands;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class ShippingCreateDTO {

    private String shippingId;

    private String orderId;

    private String billId;


     private BigInteger clientId;
    
     private Date date; 
    
     private String direction;
    
    
     private List<ProductCreatedDTO> products;

    public String getShippingId() {
        return shippingId;
    }

    public void setShippingId(String shippingId) {
        this.shippingId = shippingId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String BillId) {
        this.billId = BillId;
    }

	public BigInteger getClientId() {
		return clientId;
	}

	public void setClientId(BigInteger clientId) {
		this.clientId = clientId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}


	public List<ProductCreatedDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductCreatedDTO> products) {
		this.products = products;
	}


}
