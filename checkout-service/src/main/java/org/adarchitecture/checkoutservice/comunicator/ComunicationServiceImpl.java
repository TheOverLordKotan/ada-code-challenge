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
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.adarchitecture.checkoutservice.commands.InvoiceCreateDTO;
import org.adarchitecture.checkoutservice.commands.ProductCreatedDTO;
import org.adarchitecture.checkoutservice.commands.ShippingCreateDTO;
import org.adarchitecture.checkoutservice.config.Constants;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
/**
*
* <h1>ComunicationServiceImpl</h1>
*
* Description
* the implementation of the behavior of the business
*
* @author THEOVERLORDKOTAN  (ADA)
* @version 1.0
* 
*/
@Service
public class ComunicationServiceImpl implements ComunicationService {
    private RestTemplate restTemplate;

    public ComunicationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    
	@Override
	public boolean putCommandVerifyOrden(String ordenId) {
	
       return true;
    }
    
	public String billRest(String billId, String orderId, BigInteger clientId, Date date, String direction,
			String currency, List<ProductCreatedDTO> products, String invoiceStatus) {
       
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        InvoiceCreateDTO invoiceCreateDTO = new InvoiceCreateDTO();
        invoiceCreateDTO.setBillId(billId);
        invoiceCreateDTO.setOrderId(orderId);
        invoiceCreateDTO.setCurrency(currency);
        invoiceCreateDTO.setDate(date);
        invoiceCreateDTO.setDirection(direction);
        invoiceCreateDTO.setProducts(products);
        invoiceCreateDTO.setClientId(clientId);
        HttpEntity<?> entity = new HttpEntity<>(invoiceCreateDTO, headers);

        ResponseEntity<String> response;
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String formatted_URL = "";
        try {
            if (invoiceStatus.equalsIgnoreCase(Constants.Bill_APPROVED)) {
                formatted_URL = MessageFormat.format(Constants.LINK_Bill_SERVICES, billId,  orderId,  clientId,  date,  direction, currency,  products);
            } else if (invoiceStatus.equalsIgnoreCase(Constants.Bill_REJECTED)) {
                formatted_URL = MessageFormat.format(Constants.LINK_Bill_REJECTED, billId,  orderId,  clientId,  date,  direction, currency,  products);
            } else if (invoiceStatus.equalsIgnoreCase(Constants.Bill_ROLLBACK)) {
                formatted_URL = MessageFormat.format(Constants.LINK_Bill_ROLLBACK, billId,  orderId,  clientId,  date,  direction, currency,  products);
            }
            response = restTemplate.exchange(formatted_URL, HttpMethod.POST, entity, String.class);
        }
        catch (HttpServerErrorException e) {
            e.printStackTrace();
            return null;
        }catch (RestClientException r) {
            r.printStackTrace();
            return null;
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                Gson gson = new Gson();
                String dsdsd = gson.toJson(response.getBody());
                return dsdsd;
            } catch (JSONException e) {
               e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("DIO UN NOOOOK------------NOOK "+response);
            return null;
        }
    }

	public String putCommandShipping(String shipping, String orderId, String billId, BigInteger clientId, Date date,
			String direction, List<ProductCreatedDTO> products, String shippingStatus) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        String formatted_URL = "";
        ShippingCreateDTO shippingCreateDTO = new ShippingCreateDTO();
        shippingCreateDTO.setShippingId(shipping);
        shippingCreateDTO.setBillId(billId);
        shippingCreateDTO.setOrderId(orderId);
        shippingCreateDTO.setClientId(clientId);
        shippingCreateDTO.setDate(date);
        shippingCreateDTO.setDirection(direction);
        shippingCreateDTO.setProducts(products);
        HttpEntity<?> entity = new HttpEntity<>(shippingCreateDTO, headers);

        ResponseEntity<String> response;
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        try {
            //todo call to discovery to obtain Link Schemma
            if (shippingStatus.equalsIgnoreCase(Constants.SHIPPING_APPROVED)) {
                formatted_URL = MessageFormat.format(Constants.LINK_SHIPPING_SERVICES, shipping,billId,  orderId,  clientId,  date,  direction,  products);
            } else if (shippingStatus.equalsIgnoreCase(Constants.SHIPPING_ROLLBACK)) {
                formatted_URL = MessageFormat.format(Constants.LINK_SHIPPING_ROLLBACK, shipping,billId,  orderId,  clientId,  date,  direction,  products);
            } else if (shippingStatus.equalsIgnoreCase(Constants.SHIPPING_REJECTED)) {
                formatted_URL = MessageFormat.format(Constants.LINK_SHIPPING_REJECTED, shipping ,billId,  orderId,  clientId,  date,  direction,  products);
            }
            response = restTemplate.exchange(formatted_URL, HttpMethod.POST, entity, String.class);
        }
        catch (HttpServerErrorException e) {
            e.printStackTrace();
            return null;
        }catch (RestClientException r) {
            r.printStackTrace();
            return null;
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                Gson gson = new Gson();
                String dsdsd = gson.toJson(response.getBody());
                return dsdsd;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("DIO UN NOOOOK shipping------------NOOK "+response);
            return null;
        }
    }



}
