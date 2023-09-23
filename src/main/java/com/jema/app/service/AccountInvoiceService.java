package com.jema.app.service;

import java.util.List;
import java.util.Optional;

import com.jema.app.dto.AccountInvoiceDTO;
import com.jema.app.entities.AccountInvoice;

public interface AccountInvoiceService {
	AccountInvoice addAccountInvoice(AccountInvoiceDTO accountInvoiceDTO);

	List<AccountInvoice> getAllAccountInvoices();

	AccountInvoice updateAccountInvoice(Long id, AccountInvoiceDTO accountInvoiceDTO);

	AccountInvoice getAccountInvoiceById(Long id);

	String generateCsvDataForInvoice(Long invoiceId);

	void deleteAccountInvoice(Long id);
	
    Optional<AccountInvoice> findById(Long id);

	long getTotalInvoiceCount();
}
