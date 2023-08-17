/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 10-Jun-2023
*
*/

package com.jema.app.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "customer_order_price_negotiation_history")
@Entity
@Data
@Getter
@Setter
public class CustomerOrderPriceNegotiationHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "customer_order_id")
	private String customerOrderId;

	@Column(name = "chemical_id")
	private Long chemicalId;

	@Column(name = "price")
	Double price;

	@Column(name = "quantity")
	Long quantity;

	@Column(name = "total_tax")
	Double totalTax;

	@Column(name = "total_price")
	Double totalPrice;

	@Column(name = "comment")
	String comment;

	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;
}
