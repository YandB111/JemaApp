/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 22-Apr-2023
*
*/

package com.jema.app.dto;

import java.util.List;

import com.jema.app.entities.SchoolCollege;

import lombok.Data;

@Data
public class BoardUniversityDTO {

	private Long id;

	String name;

	private List<SchoolCollege> schoolCollege;
}
