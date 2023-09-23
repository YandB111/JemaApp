/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Apr-2023
*
*/

package com.jema.app.service;

import java.util.List;


import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jema.app.dto.EmployeeDTO;
import com.jema.app.dto.EmployeeListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Department;
import com.jema.app.entities.ElutionCustomer;

import com.jema.app.entities.Employee;

public interface EmployeeService {

	public Long save(Employee employee);

	public Page<Employee> findAllByName(String name, Pageable pageable);

	public Page<Employee> findAll(Pageable pageable);

	public List<EmployeeListView> findAll(PageRequestDTO pageRequestDTO);

	public List<Employee> findAll();

	public Employee findById(Long id);

	public int deleteEmployee(List<Long> idsArrays);

	public int updateEmployeeStatus(Long id, Integer status);

	public Long getCount(String name);


	public int updateEmployeeBasicSalary(Long id, Long basicSalary);

	public List<Employee> findAllEmployees();

	Double calculateTotalSalarySum();



	

}
