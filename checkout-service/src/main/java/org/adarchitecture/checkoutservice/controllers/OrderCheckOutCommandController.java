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
package org.adarchitecture.checkoutservice.controllers;

import org.adarchitecture.checkoutservice.commands.OrderCreatedDTO;
import org.adarchitecture.checkoutservice.services.commands.OrderCheckOutCommandService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
/**
*
* <h1>OrderCheckOutCommandController</h1>
*
* Description
* the interface implementation to expose a services to
* Different clients or implementations 
*
* @author THEOVERLORDKOTAN  (ADA)
* @version 1.0
* 
*/
@RestController
@RequestMapping(value = "api")
@Api(value = "Order Commands", description = "Order Commands Related Endpoints", tags = "Order Commands")
public class OrderCheckOutCommandController {

    private OrderCheckOutCommandService orderCommandService;

    public OrderCheckOutCommandController(OrderCheckOutCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    /**
     * Order Commands to create the flow for orchestration 
     * follow the axon saga pattern
     * @param orderCreatedDTO
     * @return @code{CompletableFuture<String>} result operation
     */
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public CompletableFuture<String> checkOutOrder(@RequestBody OrderCreatedDTO orderCreatedDTO){
        return orderCommandService.checkOutOrder(orderCreatedDTO);
    }
    /**
     * Order Commands to rollback the flow if the process has a error 
     * follow the axon saga pattern
     * @param orderCreatedDTO
     * @return @code{CompletableFuture<String>} result operation
     */
    @RequestMapping(value = "/orders-rollback", method = RequestMethod.POST)
    public CompletableFuture<String> rollbackCheckOutOrder(@RequestBody OrderCreatedDTO orderCreatedDTO){
        return orderCommandService.rollbackCheckOutOrder(orderCreatedDTO);
    }
    /**
     * Order Commands to rejected the flow if the process has a error 
     * follow the axon saga pattern
     * @param orderCreatedDTO
     * @return @code{CompletableFuture<String>} result operation
     */
    @RequestMapping(value = "/orders-rejected", method = RequestMethod.POST)
    public CompletableFuture<String> rejectedCheckOutOrder(@RequestBody OrderCreatedDTO orderCreatedDTO){
        return orderCommandService.rollbackCheckOutOrder(orderCreatedDTO);
    }
}
