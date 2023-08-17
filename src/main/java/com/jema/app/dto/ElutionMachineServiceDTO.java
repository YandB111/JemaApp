/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ElutionMachineServiceDTO {

	private Long id;
	
	@NotBlank(message = "machineId is mandatory")
	String machineId;
	
	@NotNull(message = "serviceDate is mandatory")
	private Date serviceDate;

}
