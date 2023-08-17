/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 25-May-2023
*
*/

package com.jema.app.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ElutionCustomerSettingTaxDTO {

	private Long id;

	@NotBlank(message = "Name is mandatory")
	String name;

	@NotBlank(message = "Description is mandatory")
	String description;

	String applicable;
	Double applicableValue;

	int status;

	int companyContribute;
	String companyApplicable;
	Double companyApplicableValue;
}
