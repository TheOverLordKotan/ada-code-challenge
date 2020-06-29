package org.adarchitecture.checkoutservice.repository;

import org.adarchitecture.checkoutservice.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderCheckOutRepository extends CrudRepository<OrderEntity, String> {

}
