/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Jul-2023
*
*/

package com.jema.app.dto;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;

import com.jema.app.entities.MachineWorkingSlots;

import lombok.Data;

@Data
public class ElutionMachineDTO {

	private String id;

	String name;
	
	String image;

	Long raw;

	Long operator;

	int maintenanceDays;

	String functionalType;

	Double breakStatus;
	
	Double slot;
	
	private LocalTime startTime;

	private LocalTime endTime;
	
	Double breakInterval;
	
	Double workingHours;
	
	private List<MachineWorkingSlots> slots;

}
