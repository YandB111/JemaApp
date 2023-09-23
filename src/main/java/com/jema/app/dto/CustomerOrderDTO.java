/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
*
*/

package com.jema.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomerOrderDTO {

	private String id;
	private String customerName;
    private String verification = "0";
	private List<CustomerOrderItemDTO> item;
}
