package com.jema.app.service;

import java.util.List;
import java.util.Optional;

import com.jema.app.dto.ElutionAccountInvoiceDTO;
import com.jema.app.entities.ElutionAccountInvoice;

public interface ElutionAccountInvoiceService {
	ElutionAccountInvoice addAccountInvoice(ElutionAccountInvoiceDTO accountInvoiceDTO);

	List<ElutionAccountInvoice> getAllAccountInvoices();

	ElutionAccountInvoice updateAccountInvoice(Long id, ElutionAccountInvoiceDTO accountInvoiceDTO);

	ElutionAccountInvoice getAccountInvoiceById(Long id);

	String generateCsvDataForInvoice(Long invoiceId);

	void deleteAccountInvoice(Long id);

	Optional<ElutionAccountInvoice> findById(Long id);

	long getTotalInvoiceCount();
	
	
}
