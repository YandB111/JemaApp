
/* yp
*  Projecamet : JemaApp
=======
/* 
*  Project : JemaApp
>>>>>>> a74bf511ab52fec3349dbcb67c72bd01a6924998
*  Author  : Raj Khatri
*  Date    : 27-Jun-2023
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

@Table(name = "customer_order")
@Entity
@Data
@Getter
@Setter
@JsonIgnoreProperties(value = { "createTime", "updateTime" }, allowGetters = true)
public class CustomerOrder {

	@Id
	@GeneratedValue(generator = "sequence_cus_order_id")
	@GenericGenerator(name = "sequence_cus_order_id", strategy = "com.jema.app.entities.CustomerOrderIdGenerator")
	@Expose
	@Column(name = "id")
	private String id;


	@Column(name = "chemical_id")
	private Long chemicalId;

	@Column(name = "customer_id")
	private String customerId;


	@Column(name = "price")
	Double price;

	@Column(name = "quantity")
	Long quantity;


	@Column(name = "orderDate")
	@Temporal(TemporalType.TIMESTAMP)
	Date orderDate;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "customer_order_item")
	private List<CustomerOrderItemTax> tax;

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
	String status_comment;

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

	@Column(name = "billing_address")
	private String billingAddress;

	@Column(name = "shiping_address")
	private String shipingAddress;

	@Column(name = "sales_name")
	private String salesName;


	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

	@Column(name = "updateTime", nullable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updateTime;

	@Column(name = "amount_received")
	private Double TotalAmountReceived;
	
	
	@Column(name= "typeOfPayment")
	private String typeOfPayment;
	
	 @Column(name = "verification", columnDefinition = "VARCHAR(255) DEFAULT '0'")
	    private String verification = "0";

}
