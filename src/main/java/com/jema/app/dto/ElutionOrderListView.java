/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 22-Aug-2023
*
*/

package com.jema.app.dto;

import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ElutionOrderListView {

	String id;

	@SerializedName("date")
	Date date;

//	@SerializedName("time")
//	LocalTime time;

	@SerializedName("loaded_date")
	Date loadedDate;

//	@SerializedName("loaded_time")
//	LocalTime loadedTime;

	@SerializedName("customer_id")
	String customerId;

	@SerializedName("customer_name")
	String customerName;
	
	@SerializedName("carbon_details")
	String carbonDetails;

	int status;

	int cancel;

	@JsonIgnore
	private Long total;

}
