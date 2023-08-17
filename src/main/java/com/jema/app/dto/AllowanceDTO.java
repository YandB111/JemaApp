/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Apr-2023
*
*/

package com.jema.app.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AllowanceDTO {

	private Long id;

	@NotBlank(message = "Name is mandatory")
	String name;

	@NotBlank(message = "Description is mandatory")
	String description;

	String applicable;
	Long applicableValue;

	@NotBlank(message = "Type is mandatory")
	String type;

	int status;

}
