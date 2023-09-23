package com.jema.app.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "Elution_Finance_AddRecord")
@Entity
@Data
@Getter
@Setter
public class ElutionFinanceAddRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "record_type")
	private String recordType;

	@Column(name = "add_type")
	private String addType;

	@Column(name = "employee_id", unique = true)
	private String employeeIds;
	
	@Column(name = "select_tax_type")
	private String selectTaxType;

	@Column(name = "emp_name")
	private String employeeName;

	@Column(name = "description")
	private String description;

	@Column(name = "dueDate")
	private LocalDate dueDate;

	@Column(name = "amount")
	private double amount;

	@Column(name = "totalTax", insertable = false, updatable = false)
	private double totalTax;

	@Column(name = "totalTax")
	private double totaPrice;

	@Column(name = "status")
	private String status;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@Column(name = "invoice_finance")
	private List<DocumentFinance> invoiceDocs;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@Column(name = "receipt_finance_id")
	private List<DocumentFinance> receiptDocs;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaxForElutonRecord> taxInvoices = new ArrayList<>();

}
