package com.jema.app.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;
@Data
public class AuditAndReconsileDTO {
	private Long id; // From AuditAndReconsileVerify
    private String name; // From Employee
    private Date createTime; // From Employee
    private String typeOfPayment; // From Employee
    private String verification; // Defaulted value
    private String paymentType; // From FinanceAddRecordRequest
    private String employeeName; // From FinanceAddRecordRequest
    private LocalDate dueDate; // From FinanceAddRecordRequest
    private double totalTax; // From FinanceAddRecordRequest
    private String inventoryId; // From InventoryRequest
    private Long vendorId; // From InventoryRequest
    private Double inventoryPrice; // From InventoryRequest
    private Date inventoryRequiredDate; // From InventoryRequest
    private String inventoryPaymentMode; // From InventoryRequest
    private String customerOrderId; // From CustomerOrder
    private Double customerOrderPrice; // From CustomerOrder
    private String customerOrderPaymentMode; // From CustomerOrder
    private Date customerOrderCreateTime; // From CustomerOrder


}
