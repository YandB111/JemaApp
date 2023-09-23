/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Apr-2023
*
*/

package com.jema.app.dto;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PageRequestDTO {

	@NotNull(message = "Page size is mandatory")
	int pageSize;


	@NotNull(message = "Page number is mandatory")
	int pageNumber;

	String sort;
	String keyword;
	

}
