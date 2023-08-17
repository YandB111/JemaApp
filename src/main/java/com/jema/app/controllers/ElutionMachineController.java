/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 16-Jul-2023
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.ElutionCustomerDTO;
import com.jema.app.dto.ElutionMachineDTO;
import com.jema.app.dto.ElutionMachineListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.ElutionCustomer;
import com.jema.app.entities.ElutionMachine;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.ElutionMachineService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Elution Machine Controller")
@RestController
public class ElutionMachineController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ElutionMachineController.class);

	@Autowired
	ElutionMachineService mElutionMachineService;
	
	/*
	 * ======================== Machine ADD ==================================
	 */
	@ApiOperation(value = "Add Machine", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_MACHINE_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ElutionMachineDTO mElutionMachineDTO) {
		logger.info("Request:In Elution Machine Controller for Add Machine :{} ", mElutionMachineDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionMachine mElutionMachine = new ElutionMachine();
		BeanUtils.copyProperties(mElutionMachineDTO, mElutionMachine);
		mElutionMachine.setCreateTime(new Date());
		mElutionMachine.setUpdateTime(new Date());
		String id = mElutionMachineService.save(mElutionMachine);
		mElutionMachineDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mElutionMachineDTO, "Machine successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}
	
	
	/*
	 * ======================== Machine Edit/Update ========================
	 */
	@ApiOperation(value = "Update Machine", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_MACHINE_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) String id,
			@Valid @RequestBody ElutionMachineDTO mElutionMachineDTO) {
		logger.info("Request:In Elution Machine Controller for Update Machine :{} ", mElutionMachineDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionMachine mElutionMachine = mElutionMachineService.findById(id);

		if (mElutionMachine != null) {

			ElutionMachine elutionMachine = new ElutionMachine();
			BeanUtils.copyProperties(mElutionMachineDTO, elutionMachine);
			elutionMachine.setId(id);
			elutionMachine.setCreateTime(mElutionMachine.getCreateTime());
			elutionMachine.setUpdateTime(new Date());
			elutionMachine.setDeleted(mElutionMachine.getDeleted());

			String mID = mElutionMachineService.save(elutionMachine);
			mElutionMachineDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(mElutionMachineDTO, "Machine successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Machine", HttpStatus.OK),
					HttpStatus.OK);
		}

	}
	
	/*
	 * ======================== Get All Machine =================
	 */

	@ApiOperation(value = "Get All Machine", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_MACHINE_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Elution Machine");

		Long recordsCount = 0l;

		List<ElutionMachineListView> dataList = mElutionMachineService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		
		return onSuccess(obj, Constants.MACHINE_FETCHED);
	}
	
	
	/*
	 * ======================== Delete Machine ========================
	 */

	@ApiOperation(value = "Delete Machine", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = ELUTION_MACHINE_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<String> idsArrays) {
		logger.info("Request: In Machine Controller Delete Machine of ids  :{} ", idsArrays);

		int status = mElutionMachineService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Machine Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Machine successfully deleted", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Machine");

			return new ResponseEntity<>(response.getResponse("", "Invalid Machine", HttpStatus.OK), HttpStatus.OK);
		}
	}

}
