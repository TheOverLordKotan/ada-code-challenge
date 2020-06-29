package org.adarchitecture.checkoutservice.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Orderer")
public class OrderEntity {
	
    @Id
    private String orderId;
    private BigInteger clientId;
    private Date date;
    private String direction;
    private String currency;
    private String status;    
    private String rejected;
    private String rollback;
    
    @OneToMany(
            mappedBy = "orderId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private List<OrderItemEntity> ordenItems = new ArrayList<>();

    public OrderEntity() {
    }
    

	
    //Constructors, getters and setters removed for brevity
	 
    public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
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



	



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
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



	public List<OrderItemEntity> getOrdenItems() {
		return ordenItems;
	}



	public void setOrdenItems(List<OrderItemEntity> ordenItems) {
		this.ordenItems = ordenItems;
	}



	public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public void addOrdenItem(OrderItemEntity orderItemEntity) {
    	ordenItems.add(orderItemEntity);
    	orderItemEntity.setOrderId(this);
    }
 
    public void removeOrdenItem(OrderItemEntity orderItemEntity) {
    	ordenItems.remove(orderItemEntity);
    	orderItemEntity.setOrderId(null);
    }
	
    
}
