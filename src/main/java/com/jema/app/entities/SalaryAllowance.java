/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Apr-2023
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

@Table(name = "salaryallowance")
@Entity
@Data
@Getter
@Setter
public class SalaryAllowance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "allowanceid")
	private Long allowanceId;

	@Column(name = "allowancelabel")
	String allowanceLabel;

	@Column(name = "allowancevalue", columnDefinition = "DECIMAL(10, 2)")
	double allowanceValue;

	
}
