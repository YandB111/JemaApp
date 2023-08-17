/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-May-2023
*
*/

package com.jema.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class InventorySettingQuantityLimitListView {

	private Long id;
	Long quantity;
	String chemical_name;
	Long chemical_id;

	@JsonIgnore
	private Long total;
}
