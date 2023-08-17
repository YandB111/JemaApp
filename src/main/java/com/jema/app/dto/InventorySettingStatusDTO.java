/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
*
*/

package com.jema.app.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class InventorySettingStatusDTO {

	
	private Long id;

	@NotBlank(message = "Name is mandatory")
	String name;

	@NotBlank(message = "Description is mandatory")
	String description;
	
	int referToDepartment;
	
	Long department;

	int canAddDocument;
	
	int canAddComment;	

}
