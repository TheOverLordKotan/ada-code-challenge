package org.adarchitecture.logisticservice.queryModel.finalView.shipping;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Shipping")
public class ShippingEntity {
    @Id
    private String shippingId;
    private String billId;
    private String orderId;
    private Date date;
    private String direction;
    private String item;
    private String status;

    public ShippingEntity() {
    }

    public String getShippingId() {
        return shippingId;
    }

    public void setShippingId(String shippingId) {
        this.shippingId = shippingId;
    }

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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public Date getDate() {
		return date;
	}

	public void setDate(Date date2) {
		this.date = date2;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
    
}
