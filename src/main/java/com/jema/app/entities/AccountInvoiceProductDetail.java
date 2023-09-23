package com.jema.app.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Account_Product_Invoice")
@Entity
@Data
@Getter
@Setter
public class AccountInvoiceProductDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id; // Adding a unique identifier field

	@Column(name = "productDescription")
	private String description;

	@Column(name = "productQuantity")
	private int quantity;

	@Column(name = "productPrice")
	private double price;

	@Column(name = "poductAmount")
	private double totalAmount;

	@Column(name = "producttotalTax")
	private double totalTax;

	@Column(name = "producttotalPrice")
	private double totalPrice;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaxInvoice> taxInvoices = new ArrayList<>();

}
