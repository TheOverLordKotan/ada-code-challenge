package org.adarchitecture.logisticservice.queryModel.finalView.shipping.handler;

import org.adarchitecture.logisticservice.aggregates.ShippingAggregate;
import org.adarchitecture.logisticservice.event.OrderShippedEvent;
import org.adarchitecture.logisticservice.event.RejectedShippedEvent;
import org.adarchitecture.logisticservice.event.RollbackShippedEvent;
import org.adarchitecture.logisticservice.queryModel.finalView.shipping.ShippingEntity;
import org.adarchitecture.logisticservice.queryModel.finalView.shipping.repositories.ShippingRepository;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ShippingHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ShippingHandler.class);
	
    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    @Qualifier("shippingAggregateEventSourcingRepository")
    private EventSourcingRepository<ShippingAggregate> shippingAggregateEventSourcingRepository;

    @EventSourcingHandler
    void on(OrderShippedEvent event){
        persistInvoice(buildQueryInvoice(event));
    }

    @EventSourcingHandler
    void on(RollbackShippedEvent event){
        if (shippingRepository.findById(event.shippingId).isPresent()) {
            ShippingEntity shippingEntity = shippingRepository.findById(event.shippingId).get();
            shippingRepository.delete(shippingEntity);
        }
    }

    @EventSourcingHandler
    void on(RejectedShippedEvent event){
        ShippingEntity shippingEntity =  shippingRepository.findById(event.shippingId).isPresent() ? shippingRepository.findById(event.shippingId).get() : new ShippingEntity();
        log.info("============================== ");
        log.info("Start Saga Rejected Shipping !!!!  ");
        log.info("============================== ");
        log.info("Start create a sent order ");
        shippingEntity.setShippingId(event.shippingId);
        shippingEntity.setOrderId(event.orderId);
        shippingEntity.setBillId(event.billId);
        shippingEntity.setDate(event.date);
        shippingEntity.setDirection(event.direction);
        //shippingEntity.setItem(event.item);
        shippingEntity.setStatus("Rejected");
        shippingRepository.save(shippingEntity);
        log.info("============================== ");
        log.info("end create a sent order with date {} to direction {}  ",event.date,event.direction);
        log.info("============================== ");
       
    }

    private ShippingEntity findExistingOrCreateQueryAccount(String id){
        return shippingRepository.findById(id).isPresent() ? shippingRepository.findById(id).get() : new ShippingEntity();
    }

    private ShippingEntity buildQueryInvoice(OrderShippedEvent event){
        ShippingEntity accountQueryEntity = findExistingOrCreateQueryAccount(event.billId);
        log.info("============================== ");
        log.info("Start Saga build Shipping !!!!  ");
        log.info("============================== ");
        log.info("Start create a sent order ");
        accountQueryEntity.setBillId(event.billId);
        accountQueryEntity.setOrderId(event.orderId);
        accountQueryEntity.setShippingId(event.shippingId);
        //accountQueryEntity.setItem(event.item);
        accountQueryEntity.setStatus("CREATED");
        
        log.info("============================== ");
        log.info("end create a sent order with date {} to direction {}  ",event.date,event.direction);
        log.info("============================== ");
        return accountQueryEntity;
    }
    private void persistInvoice(ShippingEntity accountQueryEntity){
        shippingRepository.save(accountQueryEntity);
    }
}
