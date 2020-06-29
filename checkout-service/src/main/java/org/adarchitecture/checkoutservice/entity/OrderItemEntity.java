package org.adarchitecture.checkoutservice.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OrdererItem")
public class OrderItemEntity {
	
    @Id
    private String orderItemId;
    private String productId;
    private String quantityId;
    private String productCost;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity orderId;

    public OrderItemEntity() {
    }

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getQuantityId() {
		return quantityId;
	}

	public void setQuantityId(String quantityId) {
		this.quantityId = quantityId;
	}

	public String getProductCost() {
		return productCost;
	}

	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}

	public OrderEntity getOrderId() {
		return orderId;
	}

	public void setOrderId(OrderEntity orderId) {
		this.orderId = orderId;
	}
}
