package com.jema.app.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

@Table(name = "Account_Invoice")
@Entity
@Data
@Getter
@Setter
public class AccountInvoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "due_date")
	private LocalDate dueDate;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "pinCode")
	private String pinCode;

	@Column(name = "billing_Name")
	private String billingName;

	@Column(name = "billingAddress")
	private String billingAddress;

	@Column(name = "shippingName")
	private String shippingName;

	@Column(name = "shippingAddress")
	private String shippingAddress;

	@Column(name = "shippingState")
	private String shippingState;

	@Column(name = "shippinCity")
	private String shippingCity;

	@Column(name = "shippingPincode")
	private String shippingPincode;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AccountInvoiceProductDetail> productDetails = new ArrayList<>();

	

}
