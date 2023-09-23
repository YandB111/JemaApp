package com.jema.app.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Account_Finanace_Tax")
@Entity
@Data
@Getter
@Setter
public class AccountFinanceTax {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "taxType")
	private String taxType;

	@Column(name = "CustomerOrVendorId")
	private String customerOrVendorID;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "select_typeTax")
	private String selectTypeTax;
	
	@Column(name = "TotalTax")
	private double totalTax;
	
	@Column(name = "totalPrice")
	private double totalPrice;

	@Column(name = "select_tax_type")
	private String selectTaxType;
}
