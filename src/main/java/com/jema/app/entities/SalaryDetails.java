/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-Apr-2023
*
*/

package com.jema.app.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "salarydetails")
@Entity
@Data
@Getter
@Setter
public class SalaryDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "type")
	String type;

	@Column(name = "basicsalary")
	Long basicSalary;

	@Column(name = "joiningbonus")
	Long joiningBonus;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "allowance")
	private List<SalaryAllowance> salaryAllowance;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "salary")
	private List<SalaryDeduction> salaryDeduction;

	@Column(name = "total_salary")
	private Double totalSalary;

	public void calculateAndSetTotalValue() {
		if (basicSalary != null && joiningBonus != null && salaryAllowance != null && salaryDeduction != null) {
			double allowanceSum = salaryAllowance.stream().mapToDouble(SalaryAllowance::getAllowanceValue).sum();

			double deductionSum = salaryDeduction.stream().mapToDouble(SalaryDeduction::getTaxValue).sum();

			totalSalary = basicSalary + joiningBonus + allowanceSum - deductionSum;
		}
	}

	

}
