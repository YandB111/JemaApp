/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jema.app.entities.Increment;
import com.jema.app.repositories.IncrementRepository;
import com.jema.app.service.EmployeeService;
import com.jema.app.service.IncrementService;

@Service
public class IncrementServiceImpl implements IncrementService {

	@Autowired
	IncrementRepository incrementRepository;

	@Autowired
	EmployeeService employeeService;

	@Override
	public Long save(Increment increment) {
		// TODO Auto-generated method stub
		Long id = incrementRepository.save(increment).getId();
		if (id > 0) {
			Long salId = employeeService.findById(increment.getEmployeeId()).getSalaryDetails().getId();
			employeeService.updateEmployeeBasicSalary(salId, increment.getBasicSalary());
		}
		return id;
	}

	@Override
	public List<Increment> findAll(Long employeeId) {
		// TODO Auto-generated method stub
		return incrementRepository.findByEmployeeIdOrderByIdDesc(employeeId);
	}

}
