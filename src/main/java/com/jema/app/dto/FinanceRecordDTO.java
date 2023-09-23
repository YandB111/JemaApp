package com.jema.app.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class FinanceRecordDTO {
	private String paymentType;
	private String recordType;
	private String addType;

	private String employeeIds;
	private String employeeName;
	private String description;
	private LocalDate dueDate;
	private double amount;
	private double totalTax;
	private String status;
	private double totaPrice;
	private List<DocumentDTO> invoiceDocs;
	private List<DocumentDTO> receiptDocs;
}
