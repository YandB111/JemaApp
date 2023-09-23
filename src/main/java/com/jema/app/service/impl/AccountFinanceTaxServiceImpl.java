package com.jema.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.glue.model.EntityNotFoundException;
import com.jema.app.entities.AccountFinanceTax;
import com.jema.app.exceptions.InvoiceNotFoundException;
import com.jema.app.repositories.AccountFinanceTaxRepo;
import com.jema.app.service.AccountFInanceTaxService;

@Service
public class AccountFinanceTaxServiceImpl implements AccountFInanceTaxService {
	@Autowired
	private AccountFinanceTaxRepo invoiceRepository;

	@Override
	public AccountFinanceTax addInvoice(AccountFinanceTax invoice) {
		return invoiceRepository.save(invoice);
	}

	@Override
	public AccountFinanceTax updateInvoice(Long id, AccountFinanceTax updatedInvoice) {
		if (!invoiceRepository.existsById(id)) {
			// Handle case where invoice is not found
			throw new InvoiceNotFoundException("Invoice not found with ID: " + id);
		}
		updatedInvoice.setId(id);
		return invoiceRepository.save(updatedInvoice);
	}

	@Override
	public void deleteInvoice(Long id) {
		if (!invoiceRepository.existsById(id)) {
			// Handle case where invoice is not found
			throw new InvoiceNotFoundException("Invoice not found with ID: " + id);
		}
		invoiceRepository.deleteById(id);
	}

	@Override
	public List<AccountFinanceTax> getAllInvoices() {
		return (List<AccountFinanceTax>) invoiceRepository.findAllByOrderByIdDesc();
	}

	
	@Override
    public List<AccountFinanceTax> getAllInvoicesByTaxType(String taxType) {
        return invoiceRepository.findByTaxType(taxType);
    }

	
	@Override
    public AccountFinanceTax findById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountFinanceTax with id " + id + " not found"));
    }
	
}
