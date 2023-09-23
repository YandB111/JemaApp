package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.AccountInvoice;
import com.jema.app.entities.ElutionAccountInvoice;

@Repository
public interface ElutionAddInvoiceRepo extends CrudRepository<ElutionAccountInvoice, Long>{
	ElutionAccountInvoice getAccountInvoiceById(Long invoiceId);
	 long count();
	 
	 List<ElutionAccountInvoice> findAllByOrderByIdDesc();
	
}
