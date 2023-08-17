/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
*
*/

package com.jema.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ElutionMachineRawListView {

	private Long id;

	@SerializedName("name")
	String name;
	
	@SerializedName("description")
	String description;
	
	@SerializedName("status")
	int status;
	
	@JsonIgnore
	private Long total;
}
