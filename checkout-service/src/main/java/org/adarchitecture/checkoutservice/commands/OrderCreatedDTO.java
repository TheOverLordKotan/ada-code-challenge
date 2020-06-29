package org.adarchitecture.checkoutservice.commands;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class OrderCreatedDTO {
	
	
	private BigInteger clientId;
	
	private Date date;
	
	private String direction;
	
	private String currency;
	
	private List<ProductCreatedDTO> products;
	
	private String rejected = "false";
	
	private String rollback= "false";

	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public String getRejected() {
		return rejected;
	}

	public void setRejected(String rejected) {
		this.rejected = rejected;
	}

	public String getRollback() {
		return rollback;
	}

	public void setRollback(String rollback) {
		this.rollback = rollback;
	}
	
	
  }
