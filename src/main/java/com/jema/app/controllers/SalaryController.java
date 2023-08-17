/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 12-May-2023
*
*/

package com.jema.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.LeaveManagementView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.dto.SalariesView;
import com.jema.app.entities.LeaveManagement;
import com.jema.app.entities.Salary;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.SalaryService;
import com.jema.app.utils.Constants;
import com.jema.app.utils.SalaryUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Salary Controller")
@RestController
public class SalaryController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(SalaryController.class);

	@Autowired
	SalaryService salaryService;
	
	@Autowired
	SalaryUtils salaryUtils;
	
	/*
	 * ======================== Salary ADD ==================================
	 */
	@ApiOperation(value = "Salaries ADD", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = SALARY_ADD, produces = "application/json")
	public ResponseEntity<?> add() {
		logger.info("Request:In Salary Controller for Add Salaries :{} ");
		GenericResponse genericResponse = new GenericResponse();

		List<Salary> mSalary = salaryUtils.getSalaries();
		salaryService.save(mSalary);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mSalary, "Salaries successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}
	
	/*
	 * ======================== Get All Salaries ======================
	 */

	@ApiOperation(value = "Get All Salaries with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = SALARY_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Salaries {} ", pageRequestDTO);
		Long recordsCount = 0l;

		List<SalariesView> dataList = salaryService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		return onSuccess(obj, Constants.SALARIES_FETCHED);
	}
	
	/*
	 * ======================== Get Employee All Salaries ======================
	 */

	@ApiOperation(value = "Get Employee All Salaries with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = FIND_EMPLOYEE_ALL_SALARY, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@PathVariable(name = "id") Long id) {
		logger.info("Rest request to get employee all Leaves {} ", id);
		Long recordsCount = 0l;
		
		List<SalariesView> dataList = salaryService.findByEmployeeId(id);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		return onSuccess(obj, Constants.SALARIES_FETCHED);
	}
	
	
	/*
	 * ======================== Paid Salary | Mark Salary Status Paid/Unpaid========================
	 */

	@ApiOperation(value = "Update Salary Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = SALARY_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<Long> idsArrays,
			@PathVariable Integer status) {
		log.info("REST Request In Salary Controller to update status {} {},", idsArrays, status);
		int res = salaryService.updateStatus(status, idsArrays);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Salary Successfully Paid");

			return new ResponseEntity<>(response.getResponse("", "Salary Successfully Paid", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Id");

			return new ResponseEntity<>(response.getResponse("", "Invalid Id", HttpStatus.OK), HttpStatus.OK);
		}
	}

}
