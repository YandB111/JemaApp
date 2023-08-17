/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 27-May-2023
*
*/

package com.jema.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ElutionCustomerListView {

	String id;

	String name;
	
	String address;
	
	String contact;
	
	String email;
	
	int block;

	int status;

	@JsonIgnore
	private Long total;
}
