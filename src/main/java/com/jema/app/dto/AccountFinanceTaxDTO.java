package com.jema.app.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AccountFinanceTaxDTO {

	private String taxType;
	private String customerOrVendorId;
	private String name;
	private String description;
	private LocalDate date;
	private double amount;
	private String selectTypeTax;
	private double totalTax;
	private double totalPrice;

}
