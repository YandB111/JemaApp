/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 12-May-2023
*
*/

package com.jema.app.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jema.app.entities.Employee;
import com.jema.app.entities.Salary;
import com.jema.app.entities.SalaryAllowance;
import com.jema.app.entities.SalaryDeduction;
import com.jema.app.service.EmployeeService;

@Component
public class SalaryUtils {

	@Autowired
	EmployeeService employeeService;

	private static final Logger LOGGER = LoggerFactory.getLogger(SalaryUtils.class);

	public List<Salary> getSalaries() {

		List<Employee> mEmployeeList = employeeService.findAll();
		List<Salary> salaries = getSalaryData(mEmployeeList);
		return salaries;
	}

	private List<Salary> getSalaryData(List<Employee> mEmployeeList) {
		// TODO Auto-generated method stub
		List<Salary> mSalaryList = new ArrayList<>();
		Salary mSalary;
		for (int i = 0; i < mEmployeeList.size(); i++) {
			mSalary = new Salary();

			mSalary.setEmpId(mEmployeeList.get(i).getId());
			mSalary.setCreateTime(new Date());
			mSalary.setUpdateTime(new Date());
			mSalary.setDate(new Date());
			mSalary.setStatus(Constants.SALARY_UNPAID);
			mSalary.setGross(calculateGross(mEmployeeList.get(i)));
			mSalary.setDeduction(calculateDeduction(mEmployeeList.get(i)));

			mSalaryList.add(mSalary);
		}

		return mSalaryList;
	}

	private BigDecimal calculateDeduction(Employee employee) {
		BigDecimal deduction = BigDecimal.ZERO;
		for (SalaryDeduction deductionItem : employee.getSalaryDetails().getSalaryDeduction()) {
			deduction = deduction.add(new BigDecimal(deductionItem.getTaxValue()));
		}
		return deduction;
	}

	private BigDecimal calculateGross(Employee employee) {
		BigDecimal basic = new BigDecimal(employee.getSalaryDetails().getBasicSalary());
		BigDecimal allowance = BigDecimal.ZERO;
		for (SalaryAllowance allowanceItem : employee.getSalaryDetails().getSalaryAllowance()) {
			allowance = allowance.add(new BigDecimal(allowanceItem.getAllowanceValue()));
		}
		return basic.add(allowance);
	}
}
