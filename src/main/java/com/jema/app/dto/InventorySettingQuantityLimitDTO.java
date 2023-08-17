/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-May-2023
*
*/

package com.jema.app.dto;

import lombok.Data;

@Data
public class InventorySettingQuantityLimitDTO {

	private Long id;
	Long chemical;
	Long quantity;
}
