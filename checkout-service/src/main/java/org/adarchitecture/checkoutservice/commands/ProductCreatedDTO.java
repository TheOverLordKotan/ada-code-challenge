package org.adarchitecture.checkoutservice.commands;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ProductCreatedDTO {
	
	
	private String productId;
	
	private BigInteger productQuantity;
	
	private BigDecimal productCost;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


	public BigInteger getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(BigInteger productQuantity) {
		this.productQuantity = productQuantity;
	}

	public BigDecimal getProductCost() {
		return productCost;
	}

	public void setProductCost(BigDecimal productCost) {
		this.productCost = productCost;
	}
}
