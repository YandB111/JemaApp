package com.jema.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class AccountInvoiceProductDTO {

	   private String description;
	    private int quantity;
	    private double price;
	    private double totalAmount;
	    private List<TaxInvoiceDTO> taxInvoices; 
	    private double totalTax;
	    private double totalPrice;
		
	
}
