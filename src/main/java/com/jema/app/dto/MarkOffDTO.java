/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 06-Jun-2023
*
*/

package com.jema.app.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class MarkOffDTO {

	@NotBlank(message = "Id is mandatory")
	String id;

	@NotBlank(message = "Comment is mandatory")
	String comment;

	int markOff;

}
