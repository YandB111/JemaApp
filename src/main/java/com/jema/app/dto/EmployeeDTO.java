/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Apr-2023
*
*/

package com.jema.app.dto;

import java.util.List;

import com.jema.app.entities.BankDetails;
import com.jema.app.entities.EducationDetails;
import com.jema.app.entities.EmployeeDocuments;
import com.jema.app.entities.Images;
import com.jema.app.entities.ProfessionalDetails;
import com.jema.app.entities.SalaryDetails;

import lombok.Data;

@Data
public class EmployeeDTO {

	private Long id;

	String name;

	String employeeId;

	String gender;

	String contact;

	String email;

	String fatherName;

	String fatherContact;

	String motherName;

	String motherContact;

	String NIDA;

	String designation;

	int status;

	private Long department;

	private Long branch;

	private List<Images> images;

	private List<EducationDetails> educationDetails;

	private List<ProfessionalDetails> professionalDetails;

	private BankDetails bankDetails;

	
	private SalaryDetails salaryDetails;

	private List<EmployeeDocuments> employeeDocuments;

}
