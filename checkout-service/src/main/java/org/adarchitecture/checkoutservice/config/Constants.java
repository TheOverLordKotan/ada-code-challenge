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
package org.adarchitecture.checkoutservice.config;
/**
*
* <h1>AxonConfig</h1>
*
* Description
* the constants for the application
* it is must be a variables in a .properties or .yml
*
* @author THEOVERLORDKOTAN  (ADA)
* @version 1.0
* 
*/
public class Constants {
    public static final String LINK_Bill_SERVICES = "http://localhost:8081/api/Bill";
    public static final String LINK_Bill_REJECTED = "http://localhost:8081/api/Bill-rejected";
    public static final String LINK_Bill_ROLLBACK = "http://localhost:8081/api/Bill-rollback";

    public static final String LINK_SHIPPING_SERVICES = "http://localhost:8082/api/shipping";
    public static final String LINK_SHIPPING_ROLLBACK = "http://localhost:8082/api/shipping-rollback";
    public static final String LINK_SHIPPING_REJECTED = "http://localhost:8082/api/shipping-rejected";

    public static final String ORDER_CREATED_STATUS = "ORDER_CREATED";
    public static final String ORDER_REJECTED_STATUS = "ORDER_REJECTED";
    public static final String ORDER_ROLLBACK_STATUS = "ORDER_ROLLBACK";

    public static final String Bill_REJECTED = "Bill_REJECTED";
    public static final String Bill_APPROVED = "Bill_APPROVED";
    public static final String Bill_ROLLBACK = "Bill_ROLLBACK";

    public static final String SHIPPING_APPROVED = "SHIPPING_APPROVED";
    public static final String SHIPPING_ROLLBACK = "SHIPPING_ROLLBACK";
    public static final String SHIPPING_REJECTED = "SHIPPING_REJECTED";



}
