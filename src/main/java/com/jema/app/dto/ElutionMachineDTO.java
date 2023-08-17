/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Jul-2023
*
*/

package com.jema.app.dto;

import java.util.List;

import com.jema.app.entities.MachineWorkingSlots;

import lombok.Data;

@Data
public class ElutionMachineDTO {

	private String id;

	String name;

	Long raw;

	Long operator;

	int maintenanceDays;

	String functionalType;

	Double breakStatus;
	
	Double slot;
	
	private List<MachineWorkingSlots> slots;

}
