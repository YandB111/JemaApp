/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 22-Jul-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ElutionMachineServiceListView {

	@SerializedName("id")
	private String id;
	
	@SerializedName("name")
	String name;
	
	@SerializedName("image")
	String image;

	@SerializedName("service_date")
	private Date serviceDate;

	@SerializedName("maintenance_days")
	int maintenanceDays;

	@JsonIgnore
	private Long total;
}
