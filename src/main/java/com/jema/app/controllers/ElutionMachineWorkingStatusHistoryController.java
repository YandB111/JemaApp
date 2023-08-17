/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Jul-2023
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.ElutionMachineWorkingStatusHistoryDTO;
import com.jema.app.dto.ElutionMachineWorkingStatusHistoryListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.ElutionMachineWorkingStatusHistory;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.ElutionMachineService;
import com.jema.app.service.ElutionMachineWorkingStatusHistoryService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Elution Machine Working Status History Controller")
@RestController
public class ElutionMachineWorkingStatusHistoryController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ElutionMachineWorkingStatusHistoryController.class);

	@Autowired
	ElutionMachineWorkingStatusHistoryService mElutionMachineWorkingStatusHistoryService;

	@Autowired
	ElutionMachineService mElutionMachineService;

	/*
	 * ======================== Working Status History ADD =================
	 */

	@ApiOperation(value = "Add Working Status History", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_MACHINE_WORKING_STATUS_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ElutionMachineWorkingStatusHistoryDTO mDTO) {
		logger.info("Request:In Elution Machine Add Wprking Status History Controller for Add :{} ", mDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionMachineWorkingStatusHistory mElutionMachineWorkingStatusHistory = new ElutionMachineWorkingStatusHistory();
		BeanUtils.copyProperties(mDTO, mElutionMachineWorkingStatusHistory);
		mElutionMachineWorkingStatusHistory.setCreateTime(new Date());
		Long id = mElutionMachineWorkingStatusHistoryService.save(mElutionMachineWorkingStatusHistory);
		mDTO.setId(id);
		mElutionMachineService.updateFunctionalType(mDTO.getMachineId(), mDTO.getFunctionalType());
		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mDTO, "Successfully added", HttpStatus.OK), HttpStatus.OK);

	}

	/*
	 * ======================== Get All Machine Working Status History
	 * =================
	 */

	@ApiOperation(value = "Get All Machine Working Status History", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_MACHINE_WORKING_STATUS_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Elution Machine Working status history");

		Long recordsCount = 0l;

		List<ElutionMachineWorkingStatusHistoryListView> dataList = mElutionMachineWorkingStatusHistoryService
				.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.MACHINE_FETCHED);
	}

}
