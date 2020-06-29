package org.adarchitecture.billservice.queryModel.finalView.bill.repositories;

import org.adarchitecture.billservice.queryModel.finalView.bill.InvoiceEntity;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<InvoiceEntity, String> {

}
