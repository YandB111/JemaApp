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
public class VendorListView {

	Long id;

	String name;
	
	String email;
	
	String address;
	
	String contact;

	String description;

	int status;

	@JsonIgnore
	private Long total;
}
