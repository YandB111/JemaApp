/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-Jun-2023
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

@Table(name = "return_reason")
@Entity
@Data
@Getter
@Setter
public class ReturnReason {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "comment")
	String comment;

	@Column(name = "reason_id")
	Long reasonId;

	@Column(name = "inventory_request_id")
	String inventoryRequestId;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "return_reason_id")
	private List<ReturnReasonDocs> returnReasonDocs;
	
	@Column(name = "createTime", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createTime;

}
