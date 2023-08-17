/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 24-Apr-2023
*
*/

package com.jema.app.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PageResponseDTO {

	private Long total;
	private Object records;

	public PageResponseDTO getRespose(Object obj, Long total) {
		this.total = total;
		this.records = obj;
		return this;
	}

}
