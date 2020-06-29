package org.adarchitecture.billservice.commands;

import java.util.Date;
import java.util.List;

public class InvoiceCreateDTO {

    private String billId;
    private String orderId;
    private String clientId;
    private Date date; 
    private String direction;
    private String currency;
    private List<ProductCreatedDTO> products;
    

    public String getBillId() {
        return billId;
    }

    public void setBillId(String BillId) {
        this.billId = BillId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List<ProductCreatedDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductCreatedDTO> products) {
		this.products = products;
	}
    
}
