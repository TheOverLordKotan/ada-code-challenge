package org.adarchitecture.checkoutservice.repository;

import org.adarchitecture.checkoutservice.entity.OrderItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemCheckOutRepository extends CrudRepository<OrderItemEntity, String> {

}
