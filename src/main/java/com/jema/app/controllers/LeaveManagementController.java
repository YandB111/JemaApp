/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-May-2023
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.LeaveManagementDTO;
import com.jema.app.dto.LeaveManagementView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.LeaveManagement;
import com.jema.app.exceptions.DepartmentErrorResponse;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.LeaveManagementService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "LeaveManagement Controller")
@RestController
public class LeaveManagementController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(LeaveManagementController.class);

	@Autowired
	LeaveManagementService leaveManagementService;

	/*
	 * ======================== Leave ADD =================
	 */
	@ApiOperation(value = "Leave ADD", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = LEAVE_MANAGEMENT_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody LeaveManagementDTO leaveManagementDTO) {

		logger.info("Request:In LeaveManagement Controller for Add Leave :{} ", leaveManagementDTO);
		GenericResponse genericResponse = new GenericResponse();

		try {
			LeaveManagement leaveManagement = new LeaveManagement();
			BeanUtils.copyProperties(leaveManagementDTO, leaveManagement);
			leaveManagement.setCreateTime(new Date());
			leaveManagement.setUpdateTime(new Date());

			if (leaveManagementService.isLeaveEntryExists(leaveManagement.getEmployeeId(), leaveManagement.getDate(),
					leaveManagement.getLeaveType())) {
				// Leave entry already exists for this employee, date, and leave type
				DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
				customResponse.setStatus(HttpStatus.CONFLICT.value());
				customResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
				customResponse.setMessage("Attendance already marked for this date and leave type");
				customResponse.setTimestamp(new Date());
				return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
			}

			Long id = leaveManagementService.save(leaveManagement);
			leaveManagementDTO.setId(id);

			return new ResponseEntity<>(
					genericResponse.getResponse(leaveManagementDTO, "Successfully added", HttpStatus.OK),
					HttpStatus.OK);
		} catch (Exception e) {
			// Handle any unexpected exceptions and send a custom error response
			DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
			customResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			customResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			customResponse.setMessage("An unexpected error occurred.");
			customResponse.setTimestamp(new Date());
			return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * ======================== Get All Leaves ======================
	 */

	@ApiOperation(value = "Get All Leave with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = LEAVE_MANAGEMENT_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Leaves {} ", pageRequestDTO);
		Long recordsCount = 0l;

		List<LeaveManagementView> dataList = leaveManagementService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		return onSuccess(obj, Constants.ATTENDANCE_FETCHED);
	}

	/*
	 * ======================== Get Employee All Leaves ======================
	 */

	@ApiOperation(value = "Get Employee All Leave with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = FIND_EMPLOYEE_ALL_LEAVES, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@PathVariable(name = "id") Long id) {
		logger.info("Rest request to get employee all Leaves {} ", id);

		List<LeaveManagement> dataList = leaveManagementService.findByEmployeeId(id);

		return onSuccess(dataList, Constants.LEAVE_FETCHED);
	}

}
