/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 11-Jun-2023
*
*/

package com.jema.app.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "elution_order_status_history")
@Entity
@Data
@Getter
@Setter
public class ElutionOrderStatusHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "comment")
	String comment;

	@Column(name = "status")
	int status;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "elution_order_history")
	private List<ElutionOrderStatusDocs> elutionOrderStatusDocs;
	
	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

}
