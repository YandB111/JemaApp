package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.AccountInvoice;
@Repository
public interface AccountInvoiceRepo extends CrudRepository<AccountInvoice, Long> {

	AccountInvoice getAccountInvoiceById(Long invoiceId);
	
	 long count(); 
	 
	 List<AccountInvoice> findAllByOrderByIdDesc();
	
}