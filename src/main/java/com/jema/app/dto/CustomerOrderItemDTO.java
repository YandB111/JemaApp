/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
*
*/

package com.jema.app.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class CustomerOrderItemDTO {

	private Long id;

	private Long chemicalId;

	private String customerId;

	Double price;

	Long quantity;

	Date orderDate;

	private List<CustomerOrderItemTaxDTO> tax;

	Double totalTax;

	Double totalPrice;

	int status;

	String paymentMode;

	private String billingAddress;

	private String shipingAddress;

	private String salesName;

}
