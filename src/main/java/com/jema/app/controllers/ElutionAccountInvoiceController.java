package com.jema.app.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.jema.app.dto.ElutionAccountInvoiceDTO;
import com.jema.app.entities.AccountInvoice;
import com.jema.app.entities.ElutionAccountInvoice;
import com.jema.app.exceptions.InvoiceConflictException;
import com.jema.app.response.ElutionInvoiceResponse;
import com.jema.app.service.ElutionAccountInvoiceService;

import io.swagger.annotations.Api;

@Api(value = "Elution Account Finance Invoice Controller")
@RestController
public class ElutionAccountInvoiceController extends ApiController {

	@Autowired
	private ElutionAccountInvoiceService elutionAccountInvoiceService;

	protected Logger logger = LoggerFactory.getLogger(ElutionAccountInvoiceController.class);

	@CrossOrigin
	@PostMapping(value = ELUTION_ADD_INVOICE, produces = "application/json")
	public ResponseEntity<?> addAccountInvoice(@RequestBody ElutionAccountInvoiceDTO accountInvoiceDTO) {
		ElutionAccountInvoice savedInvoice = elutionAccountInvoiceService.addAccountInvoice(accountInvoiceDTO);
		return ResponseEntity.ok(savedInvoice);
	}

	@CrossOrigin
	@GetMapping(value = ELUTION_ADD_INVOICE_FINDALL, produces = "application/json")
	public ResponseEntity<ElutionInvoiceResponse> getAllInvoices() {
		List<ElutionAccountInvoice> invoices = elutionAccountInvoiceService.getAllAccountInvoices();
		long totalCount = elutionAccountInvoiceService.getTotalInvoiceCount();

        // Create a custom response object that includes the invoices and the total count
        ElutionInvoiceResponse response = new ElutionInvoiceResponse(invoices, totalCount);

        return ResponseEntity.ok(response);
	}

	@CrossOrigin
	@PutMapping(value = ELUTION_ADD_INVOICE_UPDATE_BY_ID, produces = "application/json")
	public ResponseEntity<Object> updateAccountInvoice(@PathVariable Long id,
			@RequestBody ElutionAccountInvoiceDTO accountInvoiceDTO) {
		try {
			ElutionAccountInvoice updatedInvoice = elutionAccountInvoiceService.updateAccountInvoice(id,
					accountInvoiceDTO);
			return ResponseEntity.ok(updatedInvoice);
		} catch (InvoiceConflictException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	@CrossOrigin
	@GetMapping(value = ELUTION_ADD_INVOICE_DOWNLOAD, produces = "text/csv")
	public ResponseEntity<Resource> downloadDataForId(@PathVariable Long id) {
		ElutionAccountInvoice invoice = elutionAccountInvoiceService.getAccountInvoiceById(id);

		if (invoice == null) {
			// Handle case where invoice is not found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		String csvData = elutionAccountInvoiceService.generateCsvDataForInvoice(id);

		byte[] csvBytes = csvData.getBytes();
		ByteArrayResource resource = new ByteArrayResource(csvBytes);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + id + ".csv")
				.contentType(MediaType.parseMediaType("application/csv")).contentLength(csvBytes.length).body(resource);
	}

	@CrossOrigin
	@DeleteMapping(value =ELUTION_ADD_INVOICE_DELETE , produces = "application/json")
	public ResponseEntity<?> deleteAccountInvoice(@PathVariable Long id) {
		// Call a service method to delete the invoice by its ID.
		elutionAccountInvoiceService.deleteAccountInvoice(id);

		// Return a response indicating success or failure.
		return ResponseEntity.ok("Invoice deleted successfully");
	}

	
	@CrossOrigin
	@GetMapping(value = ELUTION_ADD_INVOICE_FINDBYID, produces = "application/json")
    public ResponseEntity<ElutionAccountInvoice> getAccountInvoiceById(@PathVariable Long id) {
        Optional<ElutionAccountInvoice> accountInvoice = elutionAccountInvoiceService.findById(id);

        if (accountInvoice.isPresent()) {
            return ResponseEntity.ok(accountInvoice.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
