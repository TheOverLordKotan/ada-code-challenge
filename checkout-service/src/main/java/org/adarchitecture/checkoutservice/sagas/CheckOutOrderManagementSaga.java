/*
 * MIT License 
 * 
 * Copyright (c) 2018 ADA
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * Kotan is a Japanese word.
 * The literal meaning of this word is 'elegant simplicity'.
 * You have to admire a culture that actually has its own word mean
 * for this type of concept.
 *
 */
package org.adarchitecture.checkoutservice.sagas;

import java.util.UUID;

import javax.inject.Inject;

import org.adarchitecture.checkoutservice.aggregates.OrderStatus;
import org.adarchitecture.checkoutservice.commands.CreateBillCommand;
import org.adarchitecture.checkoutservice.commands.CreateShippingCommand;
import org.adarchitecture.checkoutservice.commands.RejectedBillCommand;
import org.adarchitecture.checkoutservice.commands.RejectedOrderCommand;
import org.adarchitecture.checkoutservice.commands.RejectedShippingCommand;
import org.adarchitecture.checkoutservice.commands.RollbackBillCommand;
import org.adarchitecture.checkoutservice.commands.RollbackOrderCommand;
import org.adarchitecture.checkoutservice.commands.RollbackShippingCommand;
import org.adarchitecture.checkoutservice.commands.UpdateOrderStatusCommand;
import org.adarchitecture.checkoutservice.comunicator.ComunicationService;
import org.adarchitecture.checkoutservice.config.Constants;
import org.adarchitecture.checkoutservice.entity.OrderEntity;
import org.adarchitecture.checkoutservice.events.BillCreatedEvent;
import org.adarchitecture.checkoutservice.events.OrderCreatedEvent;
import org.adarchitecture.checkoutservice.events.OrderShippedEvent;
import org.adarchitecture.checkoutservice.events.OrderUpdatedEvent;
import org.adarchitecture.checkoutservice.events.RejectedBillEvent;
import org.adarchitecture.checkoutservice.events.RejectedOrderEvent;
import org.adarchitecture.checkoutservice.events.RejectedShippingEvent;
import org.adarchitecture.checkoutservice.events.RollbackBillEvent;
import org.adarchitecture.checkoutservice.events.RollbackOrderEvent;
import org.adarchitecture.checkoutservice.events.RollbackShippingEvent;
import org.adarchitecture.checkoutservice.repository.OrderCheckOutRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* <h1>OrderManagementSaga</h1>
*
* Description
* the implementation of axon saga pattern to orchestration services
*
* @author THEOVERLORDKOTAN  (ADA)
* @version 1.0
* 
*/
@Saga
public class CheckOutOrderManagementSaga {
    private static final Logger log = LoggerFactory.getLogger(CheckOutOrderManagementSaga.class);
    @Inject
    private transient CommandGateway commandGateway;
    @Inject
    private transient ComunicationService comunicationService;
    @Inject
    private transient OrderCheckOutRepository orderRepository;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        String billId = UUID.randomUUID().toString();
        String orderStatus;
        log.info("============================== ");
        log.info("Start Saga !!!!  ");
        log.info("============================== ");
        log.info("Start Order checkout ");
        boolean isOrdenValid = comunicationService.putCommandVerifyOrden(orderCreatedEvent.orderId);
		if (isOrdenValid) {
	        OrderEntity orderCreateEntity =  orderRepository.findById(orderCreatedEvent.orderId).isPresent() ? orderRepository.findById(orderCreatedEvent.orderId).get() : new OrderEntity();
	        if (orderCreateEntity != null) {
	        	orderCreateEntity.setClientId(orderCreatedEvent.clientId);
	            orderCreateEntity.setOrderId( orderCreatedEvent.orderId);
	            orderCreateEntity.setDate(orderCreatedEvent.date);
	            orderCreateEntity.setDirection(orderCreatedEvent.direction);
	            orderCreateEntity.setCurrency(orderCreatedEvent.currency);
	            orderCreateEntity.setStatus(Constants.ORDER_CREATED_STATUS);
	            orderRepository.save(orderCreateEntity);
	            log.info("Order created Id " + orderCreatedEvent.orderId);
	        }
	        orderCreateEntity =  orderRepository.findById(orderCreatedEvent.orderId).isPresent() ? orderRepository.findById(orderCreatedEvent.orderId).get() : new OrderEntity();
	        if (!orderCreatedEvent.rejected.isEmpty() && orderCreatedEvent.rejected.equalsIgnoreCase(Constants.ORDER_REJECTED_STATUS)){
	          orderStatus = Constants.ORDER_REJECTED_STATUS;
	        } else if (!orderCreatedEvent.rollback.isEmpty() && orderCreatedEvent.rollback.equalsIgnoreCase(Constants.ORDER_ROLLBACK_STATUS)){
	          orderStatus = Constants.ORDER_ROLLBACK_STATUS;
	        } else  {
	          orderStatus = Constants.ORDER_CREATED_STATUS;
	        }
	        if (orderStatus.equalsIgnoreCase(Constants.ORDER_CREATED_STATUS)) {
	            SagaLifecycle.associateWith("billId", billId);
	            commandGateway.send(new CreateBillCommand( billId,orderCreatedEvent.orderId, orderCreatedEvent.clientId, orderCreatedEvent.date,orderCreatedEvent.direction,orderCreatedEvent.currency
	                   , orderCreatedEvent.products, String.valueOf(OrderStatus.CREATED),orderCreatedEvent.rejected,orderCreatedEvent.rollback));
	        } else if (orderStatus.equalsIgnoreCase(Constants.ORDER_REJECTED_STATUS)) {
	            log.info("Rejected ...  ");
	            commandGateway.send(new RejectedOrderCommand(orderCreatedEvent.orderId, orderCreatedEvent.clientId, orderCreatedEvent.date,orderCreatedEvent.direction,orderCreatedEvent.currency
		                   , orderCreatedEvent.products, orderCreatedEvent.orderStatus));
	        } else if (orderStatus.equalsIgnoreCase(Constants.ORDER_ROLLBACK_STATUS)) {
	            log.info("Rollback ...  ");
	            commandGateway.send(new RollbackOrderCommand(orderCreatedEvent.orderId, orderCreatedEvent.clientId, orderCreatedEvent.date, orderCreatedEvent.direction,orderCreatedEvent.currency
		                   , orderCreatedEvent.products, orderCreatedEvent.orderStatus));
	        }
		}else {
            log.info("Rollback ...  ");
            commandGateway.send(new RollbackOrderCommand(orderCreatedEvent.orderId, orderCreatedEvent.clientId, orderCreatedEvent.date,orderCreatedEvent.currency
	                   , orderCreatedEvent.direction, orderCreatedEvent.products, orderCreatedEvent.orderStatus));
		}

    }

    @SagaEventHandler(associationProperty = "billId")
    public void handle(BillCreatedEvent BillCreatedEvent) {
        String shippingId = UUID.randomUUID().toString();
        String paymmentStatus;
        if (!BillCreatedEvent.rejected.isEmpty() && BillCreatedEvent.rejected.equalsIgnoreCase(Constants.Bill_REJECTED)){
            paymmentStatus = Constants.Bill_REJECTED;
        }else if (!BillCreatedEvent.rollback.isEmpty() && BillCreatedEvent.rollback.equalsIgnoreCase(Constants.Bill_ROLLBACK)){
            paymmentStatus = Constants.Bill_ROLLBACK;
        }else {
            paymmentStatus = Constants.Bill_APPROVED;
        }
        if (paymmentStatus.equalsIgnoreCase(Constants.Bill_APPROVED)) {
            String BillMSId = comunicationService.billRest(BillCreatedEvent.billId, BillCreatedEvent.orderId,BillCreatedEvent.clientId,BillCreatedEvent.date,BillCreatedEvent.direction,BillCreatedEvent.currency,BillCreatedEvent.products, paymmentStatus);
            SagaLifecycle.associateWith("shippingId", shippingId);
            log.info("Result OK!!!, to call << Bill >> Micro Service: "+BillMSId);
            commandGateway.send(new CreateShippingCommand(shippingId, BillCreatedEvent.orderId, BillCreatedEvent.billId, BillCreatedEvent.clientId, BillCreatedEvent.date, BillCreatedEvent.direction, BillCreatedEvent.currency, BillCreatedEvent.products, BillCreatedEvent.rejected, BillCreatedEvent.rollback));
        } else if (paymmentStatus.equalsIgnoreCase(Constants.Bill_REJECTED)) {
            log.info("Rejected ...  ");
            commandGateway.send(new RejectedBillCommand(BillCreatedEvent.billId, BillCreatedEvent.orderId,BillCreatedEvent.clientId, BillCreatedEvent.products,BillCreatedEvent.rejected));
        } else if (paymmentStatus.equalsIgnoreCase(Constants.Bill_ROLLBACK)) {
            log.info("Rollback ...  ");
            commandGateway.send(new RollbackBillCommand(BillCreatedEvent.billId, BillCreatedEvent.orderId,BillCreatedEvent.clientId, BillCreatedEvent.products,BillCreatedEvent.rollback));
        }
    }

    @SagaEventHandler(associationProperty = "shippingId")
    public void handle(OrderShippedEvent orderShippedEvent) {
        String statusShipping;

        if (!orderShippedEvent.rejected.isEmpty() && orderShippedEvent.rejected.equalsIgnoreCase(Constants.SHIPPING_REJECTED)){
            statusShipping = Constants.SHIPPING_REJECTED;
        }else if (!orderShippedEvent.rollback.isEmpty() && orderShippedEvent.rollback.equalsIgnoreCase(Constants.SHIPPING_ROLLBACK)){
            statusShipping = Constants.SHIPPING_ROLLBACK;
        }else {
            statusShipping = Constants.SHIPPING_APPROVED;
        }
        if (statusShipping.equalsIgnoreCase(Constants.SHIPPING_APPROVED)) {
            String shippingMSId = comunicationService.putCommandShipping(orderShippedEvent.shippingId, orderShippedEvent.billId, orderShippedEvent.orderId,orderShippedEvent.clientId,orderShippedEvent.date,orderShippedEvent.direction,orderShippedEvent.products, statusShipping);
            log.info("Result OK!!!, to call << Shipping >> Micro Service: "+shippingMSId);
            commandGateway.send(new UpdateOrderStatusCommand(orderShippedEvent.orderId, String.valueOf(OrderStatus.SHIPPED)));
        } else if (statusShipping.equalsIgnoreCase(Constants.SHIPPING_REJECTED)) {
            log.info("Rejected ...  ");
            commandGateway.send(new RejectedShippingCommand(orderShippedEvent.shippingId, orderShippedEvent.orderId, orderShippedEvent.billId, orderShippedEvent.clientId, orderShippedEvent.date, orderShippedEvent.direction, orderShippedEvent.currency, orderShippedEvent.products,orderShippedEvent.rejected));
        } else if (statusShipping.equalsIgnoreCase(Constants.SHIPPING_ROLLBACK)) {
            log.info("Rollback ...  ");
            commandGateway.send(new RollbackShippingCommand(orderShippedEvent.shippingId, orderShippedEvent.orderId, orderShippedEvent.billId, orderShippedEvent.clientId, orderShippedEvent.date, orderShippedEvent.direction, orderShippedEvent.currency, orderShippedEvent.products, orderShippedEvent.rollback));
        }
        log.info("Start Update Order delivered with Id " + orderShippedEvent.orderId);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderUpdatedEvent orderUpdatedEvent) {
        log.info("============================== ");
        log.info("End Saga, bye  !!! ");
        log.info("============================== ");
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(RejectedOrderEvent rejectedOrderEvent) {
        log.info("============================== ");
        log.info("Rejected Order !!!!  ");
        log.info("============================== ");
        updateOrderRepositoryWithRejectStatus(rejectedOrderEvent.orderId);
        SagaLifecycle.removeAssociationWith("orderId", rejectedOrderEvent.orderId);
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(RollbackOrderEvent rollbackOrderEvent) {
        log.info("============================== ");
        log.info("Rollback Order !!!!  ");
        log.info("============================== ");
        deleteOrderRepository(rollbackOrderEvent.orderId);
        SagaLifecycle.removeAssociationWith("orderId", rollbackOrderEvent.orderId);
        SagaLifecycle.end();
    }


    @SagaEventHandler(associationProperty = "billId")
    public void handle(RejectedBillEvent rejectedOrderEvent) {
        log.info("============================== ");
        log.info("Rejected Bill !!!!  ");
        log.info("============================== ");
        String paymmentStatus = Constants.Bill_REJECTED;
        comunicationService.billRest(rejectedOrderEvent.billId, rejectedOrderEvent.orderId,rejectedOrderEvent.clientId,null,null,null,rejectedOrderEvent.products, paymmentStatus);
        SagaLifecycle.removeAssociationWith("billId", rejectedOrderEvent.billId);
        updateOrderRepositoryWithRejectStatus(rejectedOrderEvent.orderId);
        SagaLifecycle.removeAssociationWith("orderId", rejectedOrderEvent.orderId);
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "billId")
    public void handle(RollbackBillEvent rollbackBillEvent) {
        log.info("============================== ");
        log.info("Rollback Bill !!!!  ");
        log.info("============================== ");
        String paymmentStatus = Constants.Bill_ROLLBACK;
        comunicationService.billRest(rollbackBillEvent.billId, rollbackBillEvent.orderId,rollbackBillEvent.clientId,null,null,null,rollbackBillEvent.products, paymmentStatus);
        SagaLifecycle.removeAssociationWith("billId", rollbackBillEvent.billId);
        deleteOrderRepository(rollbackBillEvent.orderId);
        SagaLifecycle.removeAssociationWith("orderId", rollbackBillEvent.orderId);
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "shippingId")
    public void handle(RejectedShippingEvent rejectedOrderEvent) {
        log.info("============================== ");
        log.info("Rejected Shipping !!!!  ");
        log.info("============================== ");
        String shippingStatus = Constants.SHIPPING_REJECTED;
        comunicationService.putCommandShipping(rejectedOrderEvent.shippingId, rejectedOrderEvent.billId, rejectedOrderEvent.orderId,rejectedOrderEvent.clientId,rejectedOrderEvent.date,rejectedOrderEvent.direction,rejectedOrderEvent.products, shippingStatus);
        SagaLifecycle.removeAssociationWith("shippingId", rejectedOrderEvent.shippingId);

        String paymmentStatus = Constants.Bill_REJECTED;
        comunicationService.billRest(rejectedOrderEvent.billId, rejectedOrderEvent.orderId,rejectedOrderEvent.clientId,null,null,null,rejectedOrderEvent.products, paymmentStatus);
        SagaLifecycle.removeAssociationWith("billId", rejectedOrderEvent.billId);
        updateOrderRepositoryWithRejectStatus(rejectedOrderEvent.orderId);
        SagaLifecycle.removeAssociationWith("orderId", rejectedOrderEvent.orderId);
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "shippingId")
    public void handle(RollbackShippingEvent rollbackShippingEvent) {
        log.info("============================== ");
        log.info("Rollback Shipping !!!!  ");
        log.info("============================== ");
        String shippingStatus = Constants.SHIPPING_ROLLBACK;
        comunicationService.putCommandShipping(rollbackShippingEvent.shippingId, rollbackShippingEvent.billId, rollbackShippingEvent.orderId,rollbackShippingEvent.clientId,rollbackShippingEvent.date,rollbackShippingEvent.direction,rollbackShippingEvent.products, shippingStatus);
        SagaLifecycle.removeAssociationWith("shippingId", rollbackShippingEvent.shippingId);
        String paymmentStatus = Constants.Bill_ROLLBACK;
        comunicationService.billRest(rollbackShippingEvent.billId, rollbackShippingEvent.orderId,rollbackShippingEvent.clientId,null,null,null,rollbackShippingEvent.products, paymmentStatus);
        SagaLifecycle.removeAssociationWith("billId", rollbackShippingEvent.billId);
        deleteOrderRepository(rollbackShippingEvent.orderId);
        SagaLifecycle.removeAssociationWith("orderId", rollbackShippingEvent.orderId);
        SagaLifecycle.end();
    }

    private void deleteOrderRepository(String orderId) {
        if (orderRepository.findById(orderId).isPresent()) {
            OrderEntity shippingEntity = orderRepository.findById(orderId).get();
            orderRepository.delete(shippingEntity);
        }
    }

    private void updateOrderRepositoryWithRejectStatus(String orderId) {
        OrderEntity orderRejected =  orderRepository.findById(orderId).isPresent() ? orderRepository.findById(orderId).get() : new OrderEntity();
        if (orderRejected != null) {
            orderRejected.setStatus(Constants.ORDER_REJECTED_STATUS);
            orderRepository.save(orderRejected);
        }
    }


}
