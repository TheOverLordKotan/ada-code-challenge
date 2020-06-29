package org.adarchitecture.billservice.queryModel.finalView.bill;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Invoice")
public class InvoiceEntity {
    @Id
    private String billId;
    private String orderId;
    private String invoiceStatus;
    private String ammount;
    private BigDecimal totalInvoice;

    public InvoiceEntity() {
    }

    public String getBillId() {
        return billId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setBillId(String BillId) {
        this.billId = BillId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

	public BigDecimal getTotalInvoice() {
		return totalInvoice;
	}

	public void setTotalInvoice(BigDecimal totalInvoice) {
		this.totalInvoice = totalInvoice;
	}
    
    
    
}
