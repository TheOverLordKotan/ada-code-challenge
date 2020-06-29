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

import org.adarchitecture.checkoutservice.aggregates.OrderAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
*
* <h1>AxonConfig</h1>
*
* Description
* the config axon services 
*
* @author THEOVERLORDKOTAN  (ADA)
* @version 1.0
* 
*/
@Configuration
public class AxonConfig {

    @Bean
    EventSourcingRepository<OrderAggregate> orderAggregateEventSourcingRepository(EventStore eventStore){
        EventSourcingRepository<OrderAggregate> repository = EventSourcingRepository.builder(OrderAggregate.class).eventStore(eventStore).build();
        return repository;
    }
}
