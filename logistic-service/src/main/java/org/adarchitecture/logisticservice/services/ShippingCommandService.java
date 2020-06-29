package org.adarchitecture.logisticservice.services;



import org.adarchitecture.logisticservice.dto.ShippingCreateDTO;

import java.util.concurrent.CompletableFuture;

public interface ShippingCommandService {

    public CompletableFuture<String> shippingOrder(ShippingCreateDTO orderCreateDTO);

    public CompletableFuture<String> shippingRollbackOrder(ShippingCreateDTO orderCreateDTO);

    public CompletableFuture<String> shippingRejectedOrder(ShippingCreateDTO orderCreateDTO);

}
