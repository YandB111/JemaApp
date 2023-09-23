/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 03-Jun-2023
*
*/

package com.jema.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class InventoryRequestDTO {

	private String id;

	private String vendorName;
    private String verification = "0";
	private List<InventoryRequestItemDTO> item;
}
