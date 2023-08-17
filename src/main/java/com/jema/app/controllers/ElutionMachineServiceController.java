/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 22-Jul-2023
*
*/

package com.jema.app.controllers;

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

import com.jema.app.dto.ElutionMachineServiceDTO;
import com.jema.app.dto.ElutionMachineServiceListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.ElutionMachineServiceEntity;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.ElutionMachineServiceService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Elution Machine Service Controller")
@RestController
public class ElutionMachineServiceController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ElutionMachineServiceController.class);

	@Autowired
	ElutionMachineServiceService mElutionMachineServiceService;

	/*
	 * ======================== Service ADD =================
	 */
	
	@ApiOperation(value = "Add Service", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_MACHINE_SERVICE_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ElutionMachineServiceDTO mElutionMachineServiceDTO) {
		logger.info("Request:In Elution Machine Service Controller for Add Service :{} ", mElutionMachineServiceDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionMachineServiceEntity mElutionMachineServiceEntity = new ElutionMachineServiceEntity();
		BeanUtils.copyProperties(mElutionMachineServiceDTO, mElutionMachineServiceEntity);
		Long id = mElutionMachineServiceService.save(mElutionMachineServiceEntity);
		mElutionMachineServiceDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mElutionMachineServiceDTO, "Successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}
	
	
	/*
	 * ======================== Get All Machine Service=================
	 */

	@ApiOperation(value = "Get All Machine Service", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_MACHINE_SERVICE_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Elution Machine Service");

		Long recordsCount = 0l;

		List<ElutionMachineServiceListView> dataList = mElutionMachineServiceService.findAll(pageRequestDTO);

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
