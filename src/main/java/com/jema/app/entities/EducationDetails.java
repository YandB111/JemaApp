/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Apr-2023
*
*/

package com.jema.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "educationdetails")
@Entity
@Data
@Getter
@Setter
public class EducationDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "educationboard")
	String educationBoard;

	@Column(name = "schoolcollege")
	String schoolCollege;
	
	@Column(name = "yearofpassing")
	String yearOfPassing;
}
