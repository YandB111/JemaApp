/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 12-May-2023
*
*/

package com.jema.app.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jema.app.entities.Employee;
import com.jema.app.entities.Salary;
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

	private double calculateDeduction(Employee employee) {
		// TODO Auto-generated method stub
		double deduction = 0l;
		for (int i = 0; i < employee.getSalaryDetails().getSalaryDeduction().size(); i++) {
			deduction = deduction + employee.getSalaryDetails().getSalaryDeduction().get(i).getTaxValue();
		}
		return deduction;
	}

	private double calculateGross(Employee employee) {
		// TODO Auto-generated method stub
		double gross = 0l;
		double basic = employee.getSalaryDetails().getBasicSalary();
		double allowance = 0l;
		for (int i = 0; i < employee.getSalaryDetails().getSalaryAllowance().size(); i++) {
			allowance = allowance + employee.getSalaryDetails().getSalaryAllowance().get(i).getAllowanceValue();
		}
		gross = basic + allowance;
		return gross;
	}
}
