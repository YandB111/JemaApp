/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 02-May-2023
*
*/

package com.jema.app.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.jema.app.dto.LeaveTypeDTO;
import com.jema.app.entities.LeaveType;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.LeaveTypeService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "LeaveType Controller")
@RestController
public class LeaveTypeController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(LeaveTypeController.class);

	@Autowired
	LeaveTypeService leaveTypeService;

	/*
	 * ======================== Leave Type ADD =================
	 */
	@ApiOperation(value = "Leave Type ADD", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = LEAVE_TYPE_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody LeaveTypeDTO leaveTypeDTO) {
		logger.info("Request:In Leave Type Controller for Add Leave Type :{} ", leaveTypeDTO);
		GenericResponse genericResponse = new GenericResponse();

		LeaveType mLeaveType = leaveTypeService.findByName(leaveTypeDTO.getName());
		if (mLeaveType == null) {
			LeaveType leaveType = new LeaveType();
			BeanUtils.copyProperties(leaveTypeDTO, leaveType);
			leaveType.setCreateTime(new Date());
			leaveType.setUpdateTime(new Date());
			Long id = leaveTypeService.save(leaveType);
			leaveTypeDTO.setId(id);
		} else {
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(leaveTypeDTO, "This Leave Type already exist", HttpStatus.CONFLICT),
					HttpStatus.CONFLICT);
		}
		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(leaveTypeDTO, "Successfully added", HttpStatus.OK), HttpStatus.OK);

	}

	/*
	 * ======================== Delete Leave Type ========================
	 */

	@ApiOperation(value = "Delete Leave Type", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = LEAVE_TYPE_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Leave Type Controller Delete Leave Type of ids  :{} ", idsArrays);

		int status = leaveTypeService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Successfully deleted", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Leave Type ID");

			return new ResponseEntity<>(response.getResponse("", "Invalid Leave Type ID", HttpStatus.OK),
					HttpStatus.OK);
		}
	}

	/*
	 * ======================== Leave Type Edit/Update ====================
	 */
	@ApiOperation(value = "Update Leave Type", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = LEAVE_TYPE_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody LeaveTypeDTO leaveTypeDTO) {
		logger.info("Request:In Leave Type Controller for Update Leave Type :{} ", leaveTypeDTO);
		GenericResponse genericResponse = new GenericResponse();

		LeaveType mLeaveType = leaveTypeService.findById(id);

		if (mLeaveType != null) {

			LeaveType leaveType = new LeaveType();
			BeanUtils.copyProperties(leaveTypeDTO, leaveType);
			leaveType.setId(id);
			leaveType.setCreateTime(leaveType.getCreateTime());
			leaveType.setUpdateTime(new Date());
			Long mID = leaveTypeService.save(leaveType);
			leaveTypeDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(leaveTypeDTO, "Leave Type successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Leave Type", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Get All Leave Type =================
	 */

	@ApiOperation(value = "Get All Leave Type", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = LEAVE_TYPE_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll() {
		logger.info("Rest request to get all Leave Type");

		List<LeaveType> mLeaveType = leaveTypeService.findAll();
		List<LeaveTypeDTO> mLeaveTypeList = new ArrayList<>();
		mLeaveTypeList = mLeaveType.stream().map(leaveType -> {
			LeaveTypeDTO mLeaveTypeDTO = new LeaveTypeDTO();
			BeanUtils.copyProperties(leaveType, mLeaveTypeDTO);
			return mLeaveTypeDTO;
		}).collect(Collectors.toList());

		return onSuccess(mLeaveTypeList, Constants.LEAVE_TYPE_FETCHED);
	}

	/*
	 * ======================== Find One Leave Type ========================
	 */

	@ApiOperation(value = "Find Leave Type By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = LEAVE_TYPE_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Leave Type Find Leave Type By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		LeaveType leaveType = leaveTypeService.findById(id);
		String msg = leaveType != null ? "Leave Type Found" : "No Leave Type found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		LeaveTypeDTO mLeaveTypeDTO = new LeaveTypeDTO();
		BeanUtils.copyProperties(leaveType, mLeaveTypeDTO);
		return new ResponseEntity<>(response.getResponse(mLeaveTypeDTO,
				mLeaveTypeDTO != null ? "Leave Type Found" : "No Leave Type found", HttpStatus.OK), HttpStatus.OK);
	}

}
