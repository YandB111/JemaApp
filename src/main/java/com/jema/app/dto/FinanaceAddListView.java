package com.jema.app.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import com.jema.app.entities.DocumentFinance;

import lombok.Data;

@Data
public class FinanaceAddListView {
	private Long id;

	@SerializedName("employee_id")
	String employeeId;

	@SerializedName("designation")
	String designation;

	@SerializedName("payment_type")
	private String paymentType;

	@SerializedName("record_type")
	private String recordType;

	private double totalTax;

	@SerializedName("add_type")
	private String addType;

	@SerializedName("emp_name")
	private String employeeName;

	@SerializedName("description")
	private String description;
	@SerializedName("date")
	private Date dueDate;

	@SerializedName("amount")
	private double amount;

	private List<DocumentFinance> invoiceDocs;

	private List<DocumentFinance> receiptDocs;

	@JsonIgnore
	private Long total;
}
