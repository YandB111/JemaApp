/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.dto;

import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import com.jema.app.entities.ReturnReasonDocs;

import lombok.Data;

@Data
public class ReturnDTO {

	private Long id;
	
	@NotBlank(message = "Comment is mandatory")
	String comment;

	@Column(name = "reason_id")
	Long reasonId;

	@NotBlank(message = "Inventory Request Id is mandatory")
	String inventoryRequestId;

	int returned;
	
	private List<ReturnReasonDocs> returnReasonDocs;

	
}
