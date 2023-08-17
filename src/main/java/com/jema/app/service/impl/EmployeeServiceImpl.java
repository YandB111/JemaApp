/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Apr-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.jema.app.dto.EmployeeListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Employee;
import com.jema.app.repositories.EmployeeRepository;
import com.jema.app.service.EmployeeService;
import com.jema.app.utils.AppUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public Long save(Employee employee) {
		String name = employee.getName();
		String email = employee.getEmail();
		String employeeId = employee.getEmployeeId();
		String contact = employee.getContact();

		// Check if an Employee with the given name or email already exists
		if (employeeRepository.existsName(name) && employeeRepository.existsEmail(email)) {
			throw new IllegalArgumentException("Employee with the same name and email already exists.");
		} else if (employeeRepository.existsName(name)) {
			throw new IllegalArgumentException("Employee with the same name already exists.");
		} else if (employeeRepository.existsEmail(email)) {
			throw new IllegalArgumentException("Employee with the same email already exists.");
		} else if (employeeRepository.existsByEmployeeId(employeeId)) {
			throw new IllegalArgumentException("Employee with the same employeeId already exists.");
		} else if (employeeRepository.existsByContact(contact)) {
			throw new IllegalArgumentException("Employee with the same contact already exists.");
		}

		Employee savedEmployee = employeeRepository.save(employee);
		return savedEmployee.getId();
	}

	@Override
	public Page<Employee> findAllByName(String name, Pageable pageable) {
		// TODO Auto-generated method stub
		return employeeRepository.findByNameContainingIgnoreCase(name, pageable);
	}

	@Override
	public Page<Employee> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return employeeRepository.findAll(pageable);
	}

	@Override
	public Employee findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			return employee.get();
		}
		return null;
	}

	@Override
	public int deleteEmployee(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		employeeRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateEmployeeStatus(Long id, Integer status) {
		// TODO Auto-generated method stub
		return employeeRepository.updateEmployeeStatus(id, status);
	}

	@Override
	public Long getCount(String name) {
		// TODO Auto-generated method stub
		if (name == null || name.trim().isEmpty()) {
			return employeeRepository.count();
		} else {
			return employeeRepository.getCount(name);
		}

	}

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(employeeRepository.findAll());
	}

	@Override
	public List<EmployeeListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, e.name as name, e.id as id, e.status as status, "
				+ "e.employeeid as employee_id, e.designation as designation, e.email as email, e.contact as contact, "
				+ "d.name as department, b.name as branch, i.url as image " + "from employee e "
				+ "left join images i on e.id = i.employee " + "left join branch b on e.branch = b.id "
				+ "left join department d on e.department = d.id";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where e.name ilike '%" + pageRequestDTO.getKeyword().trim() + "%'";
		}

		baseBuery = baseBuery
				+ " group by e.name, e.id, e.status, e.designation, e.employeeid, e.email, e.contact, d.name, b.name, i.url order by e.id DESC";
		// create a query to retrieve MyEntity objects
		Query query = null;
		try {
			query = entityManager.createNativeQuery(baseBuery, Tuple.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set the maximum number of results to retrieve (i.e., the page size)
		query.setMaxResults(pageRequestDTO.getPageSize());

		// set the index of the first result to retrieve (i.e., the offset)
		query.setFirstResult(pageRequestDTO.getPageNumber() * pageRequestDTO.getPageSize());

		// execute the query and obtain the list of entities for the requested page
		List<Tuple> tuples = query.getResultList();

		List<EmployeeListView> dataList = AppUtils.parseTuple(tuples, EmployeeListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;
	}

	@Override
	public int updateEmployeeBasicSalary(Long id, Long basicSalary) {
		// TODO Auto-generated method stub
		return employeeRepository.updateEmployeeBasicSalary(id, basicSalary);
	}

}
