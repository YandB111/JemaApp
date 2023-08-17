/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ElutionMachineListView {

	private String id;

	@SerializedName("name")
	String name;
	
	@SerializedName("image")
	String image;
	
	@SerializedName("raw")
	String raw;

	@SerializedName("break_status")
	Double breakStatus;

	@SerializedName("slot")
	Double slot;

	@SerializedName("maintenance_days")
	int maintenanceDays;
	
	@SerializedName("service_date")
	private Date serviceDate;

	@JsonIgnore
	private Long total;
}
