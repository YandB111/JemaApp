/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 27-May-2023
*
*/

package com.jema.app.dto;

import java.util.List;

import com.jema.app.entities.VendorChemical;

import lombok.Data;

@Data
public class VendorDTO {

	private Long id;

	String name;
	
	String contact;

	String email;
	
	int status;
	
	String description;

	String address;
	
	Long type;

	String accountNumber;

	String bankName;

	String branchName;

	private List<VendorChemical> chemicals;

}
