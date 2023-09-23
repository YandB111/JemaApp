/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 09-Apr-2023
*
*/

package com.jema.app.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.BranchDTO;
import com.jema.app.dto.EmployeeDTO;
import com.jema.app.dto.EmployeeListView;
import com.jema.app.dto.LeaveManagementView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.Department;
import com.jema.app.entities.Employee;
import com.jema.app.repositories.EmployeeRepository;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.EmployeeService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Employee Controller")
@RestController
public class EmployeeController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeRepository employeeRepository;

	/*
	 * ======================== Employee ADD ==================================
	 */
	@CrossOrigin
	@PostMapping(value = EMPLOYEE_ADD, produces = "application/json")
	public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
	    logger.info("Request: In Employee Controller for Add Employee: {}", employeeDTO);
	    GenericResponse genericResponse = new GenericResponse();

	    // Check for conflicts before adding the employee
	    if (isEmployeeDataValid(employeeDTO)) {
	        Employee employee = new Employee();
	        BeanUtils.copyProperties(employeeDTO, employee);
	        employee.setCreateTime(new Date());
	        employee.setUpdateTime(new Date());
	        Long id = employeeService.save(employee);
	        employeeDTO.setId(id);

	        return new ResponseEntity<GenericResponse>(
	                genericResponse.getResponse(employeeDTO, "Employee successfully added", HttpStatus.OK), HttpStatus.OK);
	    } else {
	        // Conflict with existing data
	        return new ResponseEntity<>(genericResponse.getResponse("", "Employee data conflicts with existing records", HttpStatus.CONFLICT),
	                HttpStatus.CONFLICT);
	    }
	}
	private boolean isEmployeeDataValid(@Valid EmployeeDTO employeeDTO) {
	    // Retrieve the employee from the database using the ID from employeeDTO
	    Optional<Employee> existingEmployee = employeeRepository.findById(employeeDTO.getId());

	    if (existingEmployee != null) {
	        // Check if the updated values conflict with other employees
	        return !employeeRepository.existsByEmailAndIdNot(employeeDTO.getEmail(), employeeDTO.getId())
	                && !employeeRepository.existsByNameAndIdNot(employeeDTO.getName(), employeeDTO.getId())
	                && !employeeRepository.existsByEmployeeIdAndIdNot(employeeDTO.getEmployeeId(), employeeDTO.getId())
	                && !employeeRepository.existsByContactAndIdNot(employeeDTO.getContact(), employeeDTO.getId());
	    } else {
	        // Employee with the specified ID does not exist, no conflict
	        return true;
	    }
	}

	
	/*
	 * ======================== Employee Edit/Update ========================
	 */
	@CrossOrigin
	@PutMapping(value = EMPLOYEE_UPDATE, produces = "application/json")
	public ResponseEntity<?> updateEmployee(@PathVariable(name = "id", required = true) Long id,
	        @Valid @RequestBody EmployeeDTO employeeDTO) {
	    logger.info("Request: In Employee Controller for Update Employee: {}", employeeDTO);
	    GenericResponse genericResponse = new GenericResponse();

	    Employee existingEmployee = employeeService.findById(id);

	    if (existingEmployee != null) {
	        if (isDataConflicting(existingEmployee, employeeDTO)) {
	            // Conflict with other employees, return a conflict response
	            return new ResponseEntity<>(genericResponse.getResponse("", "Employee data already exists", HttpStatus.CONFLICT),
	                    HttpStatus.CONFLICT);
	        }

	        // If no conflict, update the employee data
	        Employee updatedEmployee = new Employee();
	        BeanUtils.copyProperties(employeeDTO, updatedEmployee);
	        updatedEmployee.setId(id);
	        updatedEmployee.setCreateTime(existingEmployee.getCreateTime());
	        updatedEmployee.setUpdateTime(new Date());

	        Long updatedEmployeeId = employeeService.save(updatedEmployee);
	        employeeDTO.setId(updatedEmployeeId);
	        return new ResponseEntity<GenericResponse>(
	                genericResponse.getResponse(employeeDTO, "Employee successfully Updated", HttpStatus.OK),
	                HttpStatus.OK);
	    } else {
	        // Employee not found, return a not found response
	        return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Employee", HttpStatus.NOT_FOUND),
	                HttpStatus.NOT_FOUND);
	    }
	}

	private boolean isDataConflicting(Employee existingEmployee, EmployeeDTO updatedEmployeeDTO) {
	    // Check if email, name, employeeId, or contact already exist for other
	    // employees but not for the current employee being updated
	    return employeeRepository.existsByEmailAndIdNot(updatedEmployeeDTO.getEmail(), existingEmployee.getId())
	            || employeeRepository.existsByNameAndIdNot(updatedEmployeeDTO.getName(), existingEmployee.getId())
	            || employeeRepository.existsByEmployeeIdAndIdNot(updatedEmployeeDTO.getEmployeeId(), existingEmployee.getId())
	            || employeeRepository.existsByContactAndIdNot(updatedEmployeeDTO.getContact(), existingEmployee.getId());
	}


	/*
	 * ======================== Get All Employee ==================================
	 */

	@ApiOperation(value = "Get All Employee with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = EMPLOYEE_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllEmployee(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all employee");
//		Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, pageRequestDTO.getSort()).ignoreCase());
//		Pageable pageable = PageRequest.of(pageRequestDTO.getPageNumber(), pageRequestDTO.getPageSize(), sortBy);
//
//		Page<Employee> page;
//		Long recordsCount = 0l;
//		if (pageRequestDTO.getKeyword() == null || pageRequestDTO.getKeyword().trim().isEmpty()) {
//			page = employeeService.findAll(pageable);
//		} else {
//			page = employeeService.findAllByName(pageRequestDTO.getKeyword().trim(), pageable);
//		}
//		recordsCount = employeeService.getCount(pageRequestDTO.getKeyword().trim());
		
//		Object obj = (new PageResponseDTO()).getRespose(page.getContent(), recordsCount);
		
		Long recordsCount = 0l;

		List<EmployeeListView> dataList = employeeService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		
		return onSuccess(obj, Constants.EMPLOYEE_FETCHED);
	}

	/*
	 * ======================== Find One Employee ========================
	 */

	@ApiOperation(value = "Find Employee By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = EMPLOYEE_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
	    logger.info("Request: In Employee Controller Find employee By Id: {}", id);

	    GenericResponse response = new GenericResponse();
	    Employee employee = employeeService.findById(id);
	    
	    if (employee != null) {
	        logger.info("Response: Employee found with ID: {}", id);
	        return new ResponseEntity<>(response.getResponse(employee, "Employee Found", HttpStatus.OK), HttpStatus.OK);
	    } else {
	        logger.info("Response: No Employee found with ID: {}", id);
	        return new ResponseEntity<>(response.getResponse(null, "No Employee found", HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
	    }
	}


	/*
	 * ======================== Delete Employee ========================
	 */

	@ApiOperation(value = "Delete Employee", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = EMPLOYEE_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Employee Controller Delete employee of ids  :{} ", idsArrays);

		int status = employeeService.deleteEmployee(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Employee Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Employee successfully deleted", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Employee");

			return new ResponseEntity<>(response.getResponse("", "Invalid Employee", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Update Employee Status========================
	 */

	@ApiOperation(value = "Update Employee Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = EMPLOYEE_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
		log.info("REST Request In Employee Controller to update status {} {},", id, status);
		int res = employeeService.updateEmployeeStatus(id, status);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Employee Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Employee successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Employee");

			return new ResponseEntity<>(response.getResponse("", "Invalid Employee", HttpStatus.OK), HttpStatus.OK);
		}
	}

}
