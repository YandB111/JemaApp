package com.jema.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jema.app.dto.ElutionAccountInvoiceDTO;
import com.jema.app.dto.ElutionAccountInvoiceProductDTO;
import com.jema.app.dto.ElutionTaxInvoceDTO;
import com.jema.app.dto.TaxInvoiceDTO;
import com.jema.app.entities.ElutionAccountInvoice;
import com.jema.app.entities.ElutionAccountInvoiceProductDetail;
import com.jema.app.entities.ElutionTaxInvoice;
import com.jema.app.entities.TaxInvoice;
import com.jema.app.exceptions.InvoiceConflictException;
import com.jema.app.repositories.ElutionAddInvoiceRepo;
import com.jema.app.service.ElutionAccountInvoiceService;

@Service
public class ElutionAccountInvoiceServiceImpl implements ElutionAccountInvoiceService {
	@Autowired
	ElutionAddInvoiceRepo elutionAddInvoiceRepo;

	@Override
	public ElutionAccountInvoice addAccountInvoice(ElutionAccountInvoiceDTO invoiceDTO) {
		// Convert the DTO to an entity, set fields, and save it.
		ElutionAccountInvoice invoice = convertToEntity(invoiceDTO);
		return elutionAddInvoiceRepo.save(invoice);
	}

	@Override
	public void deleteAccountInvoice(Long id) {
		// Check if the invoice with the given ID exists.
		ElutionAccountInvoice invoice = elutionAddInvoiceRepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.CONFLICT, "Invoice not found with ID: " + id));

		// Delete the invoice.
		elutionAddInvoiceRepo.delete(invoice);
	}
	
	@Override
	public long getTotalInvoiceCount() {
        return elutionAddInvoiceRepo.count(); // Calculate the total number of records
    }

	@Override
	public List<ElutionAccountInvoice> getAllAccountInvoices() {
		return elutionAddInvoiceRepo.findAllByOrderByIdDesc();
	}

	// Add other methods as needed...

	private ElutionAccountInvoice convertToEntity(ElutionAccountInvoiceDTO dto) {
		ElutionAccountInvoice accountInvoice = new ElutionAccountInvoice();
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

		List<ElutionAccountInvoiceProductDetail> productDetails = new ArrayList<>();
		for (ElutionAccountInvoiceProductDTO detailDTO : dto.getProductDetails()) {
			ElutionAccountInvoiceProductDetail productDetail = new ElutionAccountInvoiceProductDetail();
			productDetail.setDescription(detailDTO.getDescription());
			productDetail.setQuantity(detailDTO.getQuantity());
			productDetail.setPrice(detailDTO.getPrice());
			productDetail.setTotalAmount(detailDTO.getTotalAmount());
			productDetail.setTotalTax(detailDTO.getTotalTax());
			productDetail.setTotalPrice(detailDTO.getTotalPrice());

			List<ElutionTaxInvoice> taxInvoices = new ArrayList<>();
			for (ElutionTaxInvoceDTO taxDTO : detailDTO.getTaxInvoices()) {
				ElutionTaxInvoice taxInvoice = new ElutionTaxInvoice();
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
	public ElutionAccountInvoice updateAccountInvoice(Long id, ElutionAccountInvoiceDTO accountInvoiceDTO) {
		Optional<ElutionAccountInvoice> optionalInvoice = elutionAddInvoiceRepo.findById(id);

		if (optionalInvoice.isPresent()) {
			ElutionAccountInvoice existingInvoice = optionalInvoice.get();

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

			for (ElutionAccountInvoiceProductDTO productDetailDTO : accountInvoiceDTO.getProductDetails()) {
				ElutionAccountInvoiceProductDetail productDetail = new ElutionAccountInvoiceProductDetail();
				// Set details from DTO to productDetail
				productDetail.setDescription(productDetailDTO.getDescription());
				productDetail.setQuantity(productDetailDTO.getQuantity());
				productDetail.setPrice(productDetailDTO.getPrice());
				productDetail.setTotalAmount(productDetailDTO.getTotalAmount());
				productDetail.setTotalTax(productDetailDTO.getTotalTax());
				productDetail.setTotalPrice(productDetailDTO.getTotalPrice());

				List<ElutionTaxInvoice> taxInvoices = new ArrayList<>();
				for (ElutionTaxInvoceDTO taxDTO : productDetailDTO.getTaxInvoices()) {
					ElutionTaxInvoice taxInvoice = new ElutionTaxInvoice();
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
			elutionAddInvoiceRepo.save(existingInvoice);
			return existingInvoice;
		} else {
			throw new InvoiceConflictException("Invoice not found with ID: " + id);
		}

	}

	@Override
	public ElutionAccountInvoice getAccountInvoiceById(Long id) {
		return elutionAddInvoiceRepo.findById(id).orElse(null);
	}

	@Override
	public String generateCsvDataForInvoice(Long invoiceId) {
		ElutionAccountInvoice invoice = elutionAddInvoiceRepo.getAccountInvoiceById(invoiceId);

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
		for (ElutionAccountInvoiceProductDetail productDetail : invoice.getProductDetails()) {
			csvData.append("\"").append(productDetail.getDescription()).append("\",")
					.append(productDetail.getQuantity()).append(",").append(productDetail.getPrice()).append(",")
					.append(productDetail.getTotalAmount()).append(",").append(productDetail.getTotalTax()).append(",")
					.append(productDetail.getTotalPrice()).append("\n");
		}

		return csvData.toString();
	}

	@Override
	public Optional<ElutionAccountInvoice> findById(Long id) {
		return elutionAddInvoiceRepo.findById(id);
	}
}
