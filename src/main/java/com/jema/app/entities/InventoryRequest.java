/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
*
*/

package com.jema.app.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

@Table(name = "inventory_request")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class InventoryRequest {

	@Id
	@GeneratedValue(generator = "sequence_dep_id")
	@GenericGenerator(name = "sequence_dep_id", strategy = "com.jema.app.entities.InventoryRequestIdGenerator")
	@Expose
	@Column(name = "id")
	private String id;

	@Column(name = "chemical_id")
	private Long chemicalId;

	@Column(name = "vendor_id")
	private Long vendorId;

	@Column(name = "billing_address")
	private String billingAddress;

	@Column(name = "shiping_address")
	private String shipingAddress;

	@Column(name = "price")
	Double price;

	@Column(name = "quantity")
	Long quantity;

	@Column(name = "requiredDate")
	@Temporal(TemporalType.TIMESTAMP)
	Date requiredDate;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "inventory_request_item")
	private List<InventoryRequestItemTax> tax;

	@Column(name = "total_tax")
	Double totalTax;

	@Column(name = "total_price")
	Double totalPrice;

	@Column(name = "status")
	int status;

	@Column(name = "mark_off")
	int markOff;

	@Column(name = "mark_off_date")
	Date markOffDate;

	@Column(name = "comment")
	String comment;
	
	@Column(name = "status_comment")
	String statusComment;

	@Column(name = "cancel")
	int cancel;

	@Column(name = "returned")
	int returned;

	@Column(name = "payment_mode")
	String paymentMode;

	@Column(name = "invoice_number")
	String invoiceNumber;

	@Column(name = "invoice_url")
	String invoiceURL;

	@Column(name = "invoice_date")
	Date invoiceDate;

	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	@Column(name = "updateTime", nullable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateTime;

	@Column(name = "typeOfPayment")
	private String typeOfPayment;

	@Column(name = "verification", columnDefinition = "VARCHAR(255) DEFAULT '0'")
	private String verification = "0";

}
