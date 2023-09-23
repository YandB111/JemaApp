/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 19-Aug-2023
*
*/

package com.jema.app.entities;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "elution_order")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class ElutionOrder {

	@Id
	@GeneratedValue(generator = "sequence_elu_order_id")
	@GenericGenerator(name = "sequence_elu_order_id", strategy = "com.jema.app.entities.ElutionOrderIdGenerator")
	@Expose
	@Column(name = "id")
	private String id;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "date")
	Date date;

	@Column(name = "time")
	private LocalTime time;

	@Column(name = "loaded_date")
	Date loadedDate;

	@Column(name = "loaded_time")
	private LocalTime loadedTime;

	@Column(name = "column_number")
	private Long columnNumber;

	@Column(name = "carbon_details")
	private String carbonDetails;

	@Column(name = "machine")
	private String machine;

	@Column(name = "sales_personnel")
	private String salesPersonnel;

	@Column(name = "comments")
	private String comments;

	@Column(name = "status")
	int status;

	@Column(name = "cancel")
	int cancel;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "elution_order_item")
	private List<ElutionOrderItemTax> tax;
	
	@Column(name = "price")
	Double price;
	
	@Column(name = "total_tax")
	Double totalTax;
	
	@Column(name = "total_price")
	Double totalPrice;
	
	@Column(name = "payment_mode")
	String paymentMode;
	
	@Column(name = "shiping_address")
	private String shipingAddress;

	@Column(name = "invoice_number")
	String invoiceNumber;

	@Column(name = "invoice_url")
	String invoiceURL;

	@Column(name = "invoice_date")
	Date invoiceDate;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "elution_order")
	private List<ElutionOrderWorkingSlots> slots;

	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	@Column(name = "updateTime", nullable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateTime;
}
