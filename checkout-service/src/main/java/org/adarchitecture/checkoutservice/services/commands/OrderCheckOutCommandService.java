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
package org.adarchitecture.checkoutservice.services.commands;

import org.adarchitecture.checkoutservice.commands.OrderCreatedDTO;

import java.util.concurrent.CompletableFuture;
/**
*
* <h1>OrderCheckOutCommandService</h1>
*
* Description
* the interface to declare the behavior 
* of the business commands logic 
*
* @author THEOVERLORDKOTAN  (ADA)
* @version 1.0
* 
*/
public interface OrderCheckOutCommandService {
    /**
     * Order Commands to create the flow for orchestration 
     * follow the axon saga pattern
     * @param orderCreateDTO
     * @return @code{String} result operation
     */
    public CompletableFuture<String> checkOutOrder(OrderCreatedDTO orderCreateDTO);
    /**
     * Order Commands to rollback the flow if the process has a error 
     * follow the axon saga pattern
     * @param orderCreateDTO
     * @return @code{String} result operation
     */
    public CompletableFuture<String> rollbackCheckOutOrder(OrderCreatedDTO orderCreateDTO);
    /**
     * Order Commands to rejected the flow if the process has a error 
     * follow the axon saga pattern
     * @param orderCreateDTO
     * @return @code{String} result operation
     */
    public CompletableFuture<String> rejectedCheckOutOrder(OrderCreatedDTO orderCreateDTO);

}
