/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
*
*/

package com.jema.app.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import lombok.Data;

@Data
public class InventoryRequestItemDTO {

	private Long id;
	
	private Long chemicalId;
	
	private Long vendorId;
	
	private String billingAddress;
	
	private String shipingAddress;
	
	Double price;

	Long quantity;
	
	Date requiredDate;
	
	private List<InventoryRequestItemTaxDTO> tax;
	
	Double totalTax;
	
	Double totalPrice;
	
	int status;
	
	String paymentMode;
	
}
