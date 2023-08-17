/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 27-May-2023
*
*/

package com.jema.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CustomerListView {

	String id;

	String name;
	
	String address;
	
	String contact;
	
	String tax;
	
	@SerializedName("tax_id")
	Long taxId;

	int block;

	int status;

	@JsonIgnore
	private Long total;
}
