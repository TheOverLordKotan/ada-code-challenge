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
package org.adarchitecture.checkoutservice.comunicator;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.adarchitecture.checkoutservice.commands.ProductCreatedDTO;

/**
*
* <h1>OrderCommandService</h1>
*
* Description
* the interface to declare the behavior 
* of the business commands logic 
*
* @author THEOVERLORDKOTAN  (ADA)
* @version 1.0
* 
*/
public interface ComunicationService {
	
    /**
     * BillRest communication manager  
     * follow the axon saga pattern
     * @param shipping
     * @param Bill
     * @param order
     * @param type
     * @param status
     * @return
     */
    boolean putCommandVerifyOrden(String ordenId);
    /**
     * BillRest communication manager  
     * follow the axon saga pattern
     * @param billId
     * @param orderId
     * @param clientId
     * @param date
     * @param direction
     * @param currency
     * @param products
     * @param invoiceStatus
     * @return
     */
    String billRest(String billId, String orderId, BigInteger clientId, Date date, String direction,String currency,List<ProductCreatedDTO> products, String invoiceStatus);
    /**
     * BillRest communication manager  
     * follow the axon saga pattern
     * @param shipping
     * @param orderId
     * @param billId
     * @param clientId
     * @param date
     * @param direction
     * @param currency
     * @param products
     * @return
     */
    String putCommandShipping(String shipping,String orderId,String billId, BigInteger clientId, Date date, String direction,List<ProductCreatedDTO> products, String shippingStatus);
}
