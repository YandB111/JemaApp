/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
*
*/

package com.jema.app.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ChemicalDTO {

	Long id;
	
	String image;

	String name;

	String code;

	String description;

	Double price;

	Long quantity;

	String MSSD;

	String hsCode;

	Date expireDate;
	
	

}
