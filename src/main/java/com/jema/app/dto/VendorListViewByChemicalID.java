/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 04-Jun-2023
*
*/

package com.jema.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class VendorListViewByChemicalID {

	Long id;

	String name;
	
	String contact;
	
	String address;
	
	int status;
	
	@JsonIgnore
	private Long total;
}
