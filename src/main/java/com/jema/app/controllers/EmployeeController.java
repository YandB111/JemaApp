/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 09-Apr-2023
*
*/

package com.jema.app.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.EmployeeDTO;
import com.jema.app.dto.EmployeeListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.Employee;
import com.jema.app.response.DepartmentErrorResponse;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.EmployeeService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Employee Controller")
@RestController
public class EmployeeController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	/*
	 * ======================== Employee ADD ==================================
	 */
	@CrossOrigin
	@PostMapping(value = EMPLOYEE_ADD, produces = "application/json")
	public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
	    logger.info("Request:In Employee Controller for Add Employee :{} ", employeeDTO);
	    GenericResponse genericResponse = new GenericResponse();
	    try {
	        Employee employee = new Employee();
	        BeanUtils.copyProperties(employeeDTO, employee);
	        employee.setCreateTime(new Date());
	        employee.setUpdateTime(new Date());
	        Long id = employeeService.save(employee);
	        employeeDTO.setId(id);

	        return new ResponseEntity<GenericResponse>(
	                genericResponse.getResponse(employeeDTO, "Employee successfully added", HttpStatus.OK), HttpStatus.OK);
	    } catch (IllegalArgumentException e) {
	        // Handle the IllegalArgumentException and send a custom error response
	        DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
	        customResponse.setStatus(HttpStatus.BAD_REQUEST.value());
	        customResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
	        customResponse.setMessage(e.getMessage());
	        customResponse.setTimestamp(new Date());
	        return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        // Handle any other unexpected exceptions and send a custom error response
	        DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
	        customResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        customResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	        customResponse.setMessage("An unexpected error occurred.");
	        customResponse.setTimestamp(new Date());
	        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	/*
	 * ======================== Employee Edit/Update ========================
	 */
	@CrossOrigin
	@PutMapping(value = EMPLOYEE_UPDATE, produces = "application/json")
	public ResponseEntity<?> updateEmployee(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody EmployeeDTO employeeDTO) {
		logger.info("Request:In Employee Controller for Update Employee :{} ", employeeDTO);
		GenericResponse genericResponse = new GenericResponse();

		Employee mEmployee = employeeService.findById(id);

		if (mEmployee != null) {

			Employee employee = new Employee();
			BeanUtils.copyProperties(employeeDTO, employee);
			employee.setId(id);
			employee.setCreateTime(mEmployee.getCreateTime());
			employee.setUpdateTime(new Date());

			Long mID = employeeService.save(employee);
			employeeDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(employeeDTO, "Employee successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Employee", HttpStatus.OK),
					HttpStatus.OK);
		}

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
		logger.info("Request: In Employee Controller Find employee By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		Employee employee = employeeService.findById(id);
		String msg = employee != null ? "Employee Found" : "No Employee found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(response.getResponse(employee,
				employee != null ? "Employee Found" : "No Employee found", HttpStatus.OK), HttpStatus.OK);
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
