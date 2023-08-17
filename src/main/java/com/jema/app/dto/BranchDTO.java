/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Mar-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BranchDTO {

	Long id;
	String image;

	@NotBlank(message = "Name is mandatory")
	String name;

	String description;

	@NotBlank(message = "Location is mandatory")
	String location;

	@NotBlank(message = "Email is mandatory")
	String email;

	@NotNull(message = "Leader is mandatory")
	Long leader;

	String contact;

	Long department;
	Long totalEmployee;
	Integer status;

	Date startTime;

	Date endTime;
}
