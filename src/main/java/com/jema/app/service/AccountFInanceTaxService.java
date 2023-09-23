package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.AccountFinanceTax;

public interface AccountFInanceTaxService {
	
	
	AccountFinanceTax addInvoice(AccountFinanceTax invoice);

	AccountFinanceTax updateInvoice(Long id, AccountFinanceTax invoice);

	void deleteInvoice(Long id);

	List<AccountFinanceTax> getAllInvoices();
	
	 List<AccountFinanceTax> getAllInvoicesByTaxType(String taxType);

	AccountFinanceTax findById(Long id);
	 
	 
}
