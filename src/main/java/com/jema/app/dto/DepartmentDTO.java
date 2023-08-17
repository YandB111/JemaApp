/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-Apr-2023
*
*/

package com.jema.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DepartmentDTO {

	private Long id;

	@NotBlank(message = "Name is mandatory")
	String name;

	@NotBlank(message = "Code is mandatory")
	String code;

	@NotNull(message = "ManagedBy is mandatory")
	Long managedBy;

	@NotNull(message = "Branch is mandatory")
	Long branch;

	Integer status;

}
