package com.jema.app.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jema.app.dto.AccountInvoiceDTO;
import com.jema.app.dto.AccountInvoiceProductDTO;
import com.jema.app.dto.TaxInvoiceDTO;
import com.jema.app.entities.AccountInvoice;
import com.jema.app.entities.AccountInvoiceProductDetail;
import com.jema.app.entities.TaxInvoice;
import com.jema.app.exceptions.InvoiceConflictException;
import com.jema.app.repositories.AccountInvoiceRepo;
import com.jema.app.service.AccountInvoiceService;

@Service
public class AccountInvoiceServiceImpl implements AccountInvoiceService {

	@Autowired
	AccountInvoiceRepo accoutAccountInvoiceRepo;

	@Override
	public AccountInvoice addAccountInvoice(AccountInvoiceDTO invoiceDTO) {
		// Convert the DTO to an entity, set fields, and save it.
		AccountInvoice invoice = convertToEntity(invoiceDTO);
		return accoutAccountInvoiceRepo.save(invoice);
	}

	@Override
	public List<AccountInvoice> getAllAccountInvoices() {
	    return accoutAccountInvoiceRepo.findAllByOrderByIdDesc();
	}


	@Override
	public long getTotalInvoiceCount() {
		return accoutAccountInvoiceRepo.count(); // Calculate the total number of records
	}

	// Add other methods as needed...

	private AccountInvoice convertToEntity(AccountInvoiceDTO dto) {
		AccountInvoice accountInvoice = new AccountInvoice();
		accountInvoice.setName(dto.getName());
		accountInvoice.setDate(dto.getDate());
		accountInvoice.setDueDate(dto.getDueDate());
		accountInvoice.setAddress(dto.getAddress());
		accountInvoice.setCity(dto.getCity());
		accountInvoice.setPinCode(dto.getPinCode());
		accountInvoice.setBillingName(dto.getBillingName());
		accountInvoice.setBillingAddress(dto.getBillingAddress());
		accountInvoice.setShippingName(dto.getShippingName());
		accountInvoice.setShippingAddress(dto.getShippingAddress());
		accountInvoice.setShippingState(dto.getShippingState());
		accountInvoice.setShippingCity(dto.getShippingCity());
		accountInvoice.setShippingPincode(dto.getShippingPincode());

		List<AccountInvoiceProductDetail> productDetails = new ArrayList<>();
		for (AccountInvoiceProductDTO detailDTO : dto.getProductDetails()) {
			AccountInvoiceProductDetail productDetail = new AccountInvoiceProductDetail();
			productDetail.setDescription(detailDTO.getDescription());
			productDetail.setQuantity(detailDTO.getQuantity());
			productDetail.setPrice(detailDTO.getPrice());
			productDetail.setTotalAmount(detailDTO.getTotalAmount());
			productDetail.setTotalTax(detailDTO.getTotalTax());
			productDetail.setTotalPrice(detailDTO.getTotalPrice());

			// Create TaxInvoice objects and add them to the product detail
			List<TaxInvoice> taxInvoices = new ArrayList<>();
			for (TaxInvoiceDTO taxDTO : detailDTO.getTaxInvoices()) {
				TaxInvoice taxInvoice = new TaxInvoice();
				taxInvoice.setName(taxDTO.getName());
				taxInvoice.setValue1(taxDTO.getValue1());
				taxInvoice.setValue2(taxDTO.getValue2());
				taxInvoices.add(taxInvoice);
			}
			productDetail.setTaxInvoices(taxInvoices);

			productDetails.add(productDetail);
		}
		accountInvoice.setProductDetails(productDetails);
		
		

		return accountInvoice;
	}

	@Override
	public AccountInvoice updateAccountInvoice(Long id, AccountInvoiceDTO accountInvoiceDTO) {
		Optional<AccountInvoice> optionalInvoice = accoutAccountInvoiceRepo.findById(id);

		if (optionalInvoice.isPresent()) {
			AccountInvoice existingInvoice = optionalInvoice.get();

			// Update the fields from the DTO
			existingInvoice.setName(accountInvoiceDTO.getName());
			existingInvoice.setDate(accountInvoiceDTO.getDate());
			existingInvoice.setDueDate(accountInvoiceDTO.getDueDate());
			existingInvoice.setAddress(accountInvoiceDTO.getAddress());
			existingInvoice.setCity(accountInvoiceDTO.getCity());
			existingInvoice.setPinCode(accountInvoiceDTO.getPinCode());
			existingInvoice.setBillingName(accountInvoiceDTO.getBillingName());
			existingInvoice.setBillingAddress(accountInvoiceDTO.getBillingAddress());
			existingInvoice.setShippingName(accountInvoiceDTO.getShippingName());
			existingInvoice.setShippingAddress(accountInvoiceDTO.getShippingAddress());
			existingInvoice.setShippingState(accountInvoiceDTO.getShippingState());
			existingInvoice.setShippingCity(accountInvoiceDTO.getShippingCity());
			existingInvoice.setShippingPincode(accountInvoiceDTO.getShippingPincode());

			// Clear the existing product details and update with new details
			existingInvoice.getProductDetails().clear();

			for (AccountInvoiceProductDTO productDetailDTO : accountInvoiceDTO.getProductDetails()) {
				AccountInvoiceProductDetail productDetail = new AccountInvoiceProductDetail();

				// Set details from DTO to productDetail
				productDetail.setDescription(productDetailDTO.getDescription());
				productDetail.setQuantity(productDetailDTO.getQuantity());
				productDetail.setPrice(productDetailDTO.getPrice());
				productDetail.setTotalAmount(productDetailDTO.getTotalAmount());
				productDetail.setTotalTax(productDetailDTO.getTotalTax());
				productDetail.setTotalPrice(productDetailDTO.getTotalPrice());

				// Create and set TaxInvoice objects based on TaxInvoiceDTOs
				List<TaxInvoice> taxInvoices = new ArrayList<>();
				for (TaxInvoiceDTO taxDTO : productDetailDTO.getTaxInvoices()) {
					TaxInvoice taxInvoice = new TaxInvoice();
					taxInvoice.setName(taxDTO.getName());
					taxInvoice.setValue1(taxDTO.getValue1());
					taxInvoice.setValue2(taxDTO.getValue2());

					// Set other tax invoice properties here
					taxInvoices.add(taxInvoice);
				}
				productDetail.setTaxInvoices(taxInvoices);

				existingInvoice.getProductDetails().add(productDetail);
			}

			// Save the updated invoice
			accoutAccountInvoiceRepo.save(existingInvoice);
			return existingInvoice;
		} else {
			throw new InvoiceConflictException("Invoice not found with ID: " + id);
		}

	}

	@Override
	public AccountInvoice getAccountInvoiceById(Long id) {
		return accoutAccountInvoiceRepo.findById(id).orElse(null);
	}

	@Override
	public void deleteAccountInvoice(Long id) {
		// Check if the invoice with the given ID exists.
		AccountInvoice invoice = accoutAccountInvoiceRepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.CONFLICT, "Invoice not found with ID: " + id));
		accoutAccountInvoiceRepo.delete(invoice);
	}

	@Override
	public String generateCsvDataForInvoice(Long invoiceId) {
		AccountInvoice invoice = accoutAccountInvoiceRepo.getAccountInvoiceById(invoiceId);

		StringBuilder csvData = new StringBuilder();

		// Append invoice details
		csvData.append("Invoice Details\n");
		csvData.append("Name,").append(invoice.getName()).append("\n");
		csvData.append("Date,").append(invoice.getDate()).append("\n");
		csvData.append("Due Date,").append(invoice.getDueDate()).append("\n");
		csvData.append("Address,").append(invoice.getAddress()).append("\n");
		csvData.append("City,").append(invoice.getCity()).append("\n");
		csvData.append("Pin Code,").append(invoice.getPinCode()).append("\n");
		csvData.append("Billing Name,").append(invoice.getBillingName()).append("\n");
		csvData.append("Billing Address,").append(invoice.getBillingAddress()).append("\n");
		csvData.append("Shipping Name,").append(invoice.getShippingName()).append("\n");
		csvData.append("Shipping Address,").append(invoice.getShippingAddress()).append("\n");
		csvData.append("Shipping State,").append(invoice.getShippingState()).append("\n");
		csvData.append("Shipping City,").append(invoice.getShippingCity()).append("\n");
		csvData.append("Shipping Pincode,").append(invoice.getShippingPincode()).append("\n\n");

		// Append CSV header
		csvData.append("Product Description,Quantity,Price,Total Amount,Total Tax,Total Price\n");

		// Iterate through product details and append CSV rows
		for (AccountInvoiceProductDetail productDetail : invoice.getProductDetails()) {
			csvData.append("\"").append(productDetail.getDescription()).append("\",")
					.append(productDetail.getQuantity()).append(",").append(productDetail.getPrice()).append(",")
					.append(productDetail.getTotalAmount()).append(",").append(productDetail.getTotalTax()).append(",")
					.append(productDetail.getTotalPrice()).append("\n");
		}

		return csvData.toString();
	}

	@Override
	public Optional<AccountInvoice> findById(Long id) {
		return accoutAccountInvoiceRepo.findById(id);
	}

}
