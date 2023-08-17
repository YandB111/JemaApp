/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Mar-2023
*
*/

package com.jema.app.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "employee")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", unique = true)
	String name;

	@Column(name = "employeeid", unique = true)
	String employeeId;

	@Column(name = "gender")
	String gender;

	@Column(name = "contact", unique = true)
	String contact;

	@Column(name = "email", unique = true)
	String email;

	@Column(name = "fathername")
	String fatherName;

	@Column(name = "fathercontact")
	String fatherContact;

	@Column(name = "mothername")
	String motherName;

	@Column(name = "mothercontact")
	String motherContact;

	@Column(name = "nida")
	String NIDA;

	@Column(name = "designation")
	String designation;

	@Column(name = "status")
	int status;

	@Column(name = "department")
	private Long department;

	@Column(name = "branch")
	private Long branch;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "employee")
	private List<Images> images;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "employee")
	private List<EducationDetails> educationDetails;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "employee")
	private List<ProfessionalDetails> professionalDetails;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "bank_details_employee_id")
	private BankDetails bankDetails;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "salary_details_employee_id")
	private SalaryDetails salaryDetails;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "employee")
	private List<EmployeeDocuments> employeeDocuments;

	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	@Column(name = "updateTime", nullable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateTime;

	@Column(name = "amount_added")
	private Long amountPaid;

	@Column(name = "last_payment_date")
	private LocalDateTime lastPaymentDate;

}
