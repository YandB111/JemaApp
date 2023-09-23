package com.jema.app.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.entities.AccountFinanceTax;
import com.jema.app.service.AccountFInanceTaxService;

import io.swagger.annotations.Api;

@Api(value = "Account Finance Tax Controller")
@RestController
public class AccountFinanaceTaxController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(AccountFinanaceTaxController.class);

	@Autowired
	private AccountFInanceTaxService invoiceService;

	@CrossOrigin
	@PostMapping(value = ACCOUNT_FINANCE_ADD, produces = "application/json")
	public ResponseEntity<AccountFinanceTax> addInvoice(@RequestBody AccountFinanceTax invoice) {
		AccountFinanceTax savedInvoice = invoiceService.addInvoice(invoice);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedInvoice);
	}

	@CrossOrigin
	@PutMapping(value = ACCOUNT_FINANCE_UPDATE_BY_ID, produces = "application/json")
	public ResponseEntity<AccountFinanceTax> updateInvoice(@PathVariable Long id,
			@RequestBody AccountFinanceTax updatedInvoice) {
		AccountFinanceTax invoice = invoiceService.updateInvoice(id, updatedInvoice);
		return ResponseEntity.ok(invoice);
	}

	@CrossOrigin
	@DeleteMapping(value = ACCOUNT_FINANCE_DELETE_BY_ID, produces = "application/json")
	public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
		invoiceService.deleteInvoice(id);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@GetMapping(value = ACCOUNT_FINANCE_GETALL, produces = "application/json")
	public ResponseEntity<List<AccountFinanceTax>> getAllInvoices() {
		List<AccountFinanceTax> invoices = invoiceService.getAllInvoices();
		return ResponseEntity.ok(invoices);
	}

	@CrossOrigin
	@GetMapping(value = ACCOUNT_FINANCE_GETALL_BY_TAXTPE, produces = "application/json")
	public ResponseEntity<List<AccountFinanceTax>> getAllInvoicesByTaxType(@PathVariable String taxType) {
		logger.info("Request:In Board University Controller for Add Board University :{} ", taxType);
		List<AccountFinanceTax> invoices = invoiceService.getAllInvoicesByTaxType(taxType);
		return ResponseEntity.ok(invoices);
	}

	@CrossOrigin
	@GetMapping(value = ACCOUNT_FINANCE_GETALL_BY_ID, produces = "application/json")
	public ResponseEntity<AccountFinanceTax> getAccountFinanceTaxById(@PathVariable Long id) {
		AccountFinanceTax accountFinanceTax = invoiceService.findById(id);
		return ResponseEntity.ok(accountFinanceTax);
	}

}
