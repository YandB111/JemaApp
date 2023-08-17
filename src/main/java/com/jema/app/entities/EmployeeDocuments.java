/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 01-May-2023
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

@Table(name = "employee_documents")
@Entity
@Data
@Getter
@Setter
public class EmployeeDocuments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "documentid")
	private Long documentId;

	@Column(name = "documentlabel")
	String documentLabel;

	@Column(name = "documentvalue")
	String documentValue;
}
