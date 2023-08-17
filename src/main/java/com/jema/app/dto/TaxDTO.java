/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Apr-2023
*
*/

package com.jema.app.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TaxDTO {

	private Long id;

	@NotBlank(message = "Name is mandatory")
	String name;

	@NotBlank(message = "Description is mandatory")
	String description;

	String applicable;
	Double applicableValue;

	Long type;

	int status;

	int companyContribute;
	String companyApplicable;
	Double companyApplicableValue;

}
