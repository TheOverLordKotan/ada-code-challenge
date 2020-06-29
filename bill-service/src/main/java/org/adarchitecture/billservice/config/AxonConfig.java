package org.adarchitecture.billservice.config;

import org.adarchitecture.billservice.aggregates.InvoiceAggregate;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    EventSourcingRepository<InvoiceAggregate> invoiceAggregateEventSourcingRepository(EventStore eventStore){
        EventSourcingRepository<InvoiceAggregate> repository = EventSourcingRepository.builder(InvoiceAggregate.class).eventStore(eventStore).build();
        return repository;
    }

}
