/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-Jun-2023
*
*/

package com.jema.app.dto;

import lombok.Data;

@Data
public class CustomerDTO {

	String id;
	
	String plantName;

	String plantLocation;

	String name;

	String contact;

	String alternateContact;

	String email;

	String address;

	String city;

	String state;

	String zip;

	String country;

	int status;

	Long type;

	Long tax;

	String accountNumber;

	String bankName;

	String branchName;

}
