/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 19-Aug-2023
*
*/

package com.jema.app.dto;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.jema.app.entities.ElutionOrderItemTax;
import com.jema.app.entities.ElutionOrderWorkingSlots;

import lombok.Data;

@Data
public class ElutionOrderDTO {

	private String id;
	
	private String customerId;
	
	Date date;
	
	private LocalTime time;

	Date loadedDate;
	
	private LocalTime loadedTime;

	private Long columnNumber;
	
	private String carbonDetails;
	
	private String machine;

	private String salesPersonnel;
	
	private String comments;
	
	int status;
	
	int cancel;
	
	private List<ElutionOrderWorkingSlots> slots;
		
	Double price;

	private List<ElutionOrderItemTax> tax;

	Double totalTax;

	Double totalPrice;

	String paymentMode;

	private String shipingAddress;

	
}
