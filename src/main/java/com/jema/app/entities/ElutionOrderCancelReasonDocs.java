/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-Jun-2023
*
*/

package com.jema.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "elution_order_cancel_reason_docs")
@Entity
@Data
@Getter
@Setter
public class ElutionOrderCancelReasonDocs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "url")
	private String url;
}
