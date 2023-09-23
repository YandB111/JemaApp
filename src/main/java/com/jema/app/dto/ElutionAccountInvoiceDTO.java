package com.jema.app.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
@Data 
public class ElutionAccountInvoiceDTO {
	private String name;
	private LocalDate date;
	private LocalDate dueDate;
	private String address;
	private String city;
	private String pinCode;
	private String billingName;
	private String billingAddress;
	private String shippingName;
	private String shippingAddress;
	private String shippingState;
	private String shippingCity;
	private String shippingPincode;
	private List<ElutionAccountInvoiceProductDTO> productDetails;
	
}
