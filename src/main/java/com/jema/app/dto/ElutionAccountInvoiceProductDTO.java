package com.jema.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class ElutionAccountInvoiceProductDTO {
	private String description;
	private int quantity;
	private double price;
	private double totalAmount;
	private double totalTax;
	private double totalPrice;
	private List<ElutionTaxInvoceDTO> taxInvoices; 
}
