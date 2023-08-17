/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.dto;

import lombok.Data;

@Data
public class CustomerOrderPriceNegotiationHistoryDTO {

	private Long id;

	private String customerOrderId;

	private Long chemicalId;

	Double price;

	Long quantity;

	Double totalTax;

	Double totalPrice;

	String comment;

}
