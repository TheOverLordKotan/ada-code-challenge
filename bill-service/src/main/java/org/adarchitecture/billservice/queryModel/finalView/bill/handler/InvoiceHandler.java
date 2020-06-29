package org.adarchitecture.billservice.queryModel.finalView.bill.handler;

import java.math.BigDecimal;
import java.util.List;

import org.adarchitecture.billservice.aggregates.InvoiceAggregate;
import org.adarchitecture.billservice.commands.ProductCreatedDTO;
import org.adarchitecture.billservice.events.BillCreatedEvent;
import org.adarchitecture.billservice.events.RejectedBillEvent;
import org.adarchitecture.billservice.events.RollbackBillEvent;
import org.adarchitecture.billservice.queryModel.finalView.bill.InvoiceEntity;
import org.adarchitecture.billservice.queryModel.finalView.bill.repositories.InvoiceRepository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class InvoiceHandler {
	 private static final Logger log = LoggerFactory.getLogger(InvoiceHandler.class);
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    @Qualifier("invoiceAggregateEventSourcingRepository")
    private EventSourcingRepository<InvoiceAggregate> invoiceAggregateEventSourcingRepository;

    @EventSourcingHandler
    void on(BillCreatedEvent event){
        persistInvoice(buildQueryInvoice(event));
    }

    @EventSourcingHandler
    void on(RejectedBillEvent event1) {
        InvoiceEntity invoiceEntity =  invoiceRepository.findById(event1.billId).isPresent() ? invoiceRepository.findById(event1.billId).get() : new InvoiceEntity();
        invoiceEntity.setBillId(event1.billId);
        invoiceEntity.setOrderId(event1.orderId);
        invoiceEntity.setTotalInvoice(sumProductsQuatitys(event1.products));
        invoiceEntity.setInvoiceStatus("REJECTED");
        invoiceRepository.save(invoiceEntity);
    }


    @EventSourcingHandler
    void on(RollbackBillEvent rollbackInvoiceEvent) {
        if (invoiceRepository.findById(rollbackInvoiceEvent.billId).isPresent()) {
            InvoiceEntity invoiceEntity = invoiceRepository.findById(rollbackInvoiceEvent.billId).get();
            invoiceRepository.delete(invoiceEntity);
        }
    }

    private InvoiceEntity findExistingOrCreateQueryAccount(String id){
        return invoiceRepository.findById(id).isPresent() ? invoiceRepository.findById(id).get() : new InvoiceEntity();
    }

    private InvoiceEntity buildQueryInvoice(BillCreatedEvent event){
        InvoiceEntity accountQueryEntity = findExistingOrCreateQueryAccount(event.billId);
        accountQueryEntity.setBillId(event.billId);
        accountQueryEntity.setOrderId(event.orderId);
        accountQueryEntity.setTotalInvoice(sumProductsQuatitys(event.products));
        accountQueryEntity.setInvoiceStatus("PAID");
       // accountQueryEntity.setAmmount(event.ammount);
        return accountQueryEntity;
    }
    /**
	 * Description
	 * JavaDoc
	*/
	private BigDecimal sumProductsQuatitys(List<ProductCreatedDTO> products) {
        log.info("============================== ");
        log.info("Start Saga Invoice !!!!  ");
        log.info("============================== ");
        log.info("Start sum products ");
		/*
		 * =======================
		 * Declaracion de varibles 
		 * =======================
		*/
		BigDecimal resultInvoice= new BigDecimal("0");
		BigDecimal pivotInvoice= new BigDecimal("0");
		/*
		  * =====================================
		  * Este try catch se encarga de ...
		  * =====================================
		*/
		try {
	        /*
			  * =====================================
			  * this for sum all products with 
			  * his quantity´s
			  * =====================================
			*/
			for (ProductCreatedDTO productCreatedDTO : products) {
				pivotInvoice= new BigDecimal("0");
				/*
				  * =====================================
				  * this if is the validator of value object
				  * =====================================
				*/
				if (productCreatedDTO!=null) {
					/*
					  * =====================================
					  * this if is the validator of value attribute
					  * =====================================
					*/
					if (productCreatedDTO.getProductQuantity()!=null &&productCreatedDTO.getProductQuantity().doubleValue()!=0) {
						 log.info("Start mul product node {} quantity {} * cost {}  ",productCreatedDTO.getProductId(),productCreatedDTO.getProductQuantity(),productCreatedDTO.getProductCost());
						 pivotInvoice =  new BigDecimal(productCreatedDTO.getProductQuantity()).multiply(productCreatedDTO.getProductCost());
						 log.info("result mul product node {}",pivotInvoice);
					}
					
				}
				resultInvoice=resultInvoice.add(pivotInvoice);
				log.info("result sum product nodes {}",resultInvoice);
			}
			
		} catch (Exception e) {
			log.error("error sum product nodes {}" ,e);
			return resultInvoice;
		}
		log.info("result sum Invoice  {}",resultInvoice);

		//init logic
		return resultInvoice;
	}
    private void persistInvoice(InvoiceEntity accountQueryEntity){
        invoiceRepository.save(accountQueryEntity);
    }
}
