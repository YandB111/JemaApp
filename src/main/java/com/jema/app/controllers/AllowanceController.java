/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 28-Apr-2023
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

import com.jema.app.dto.AllowanceDTO;
import com.jema.app.entities.Allowance;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.AllowanceService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Allowance Controller")
@RestController
public class AllowanceController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(AllowanceController.class);

	@Autowired
	AllowanceService allowanceService;

	/*
	 * ======================== Allowance ADD =================
	 */
	@CrossOrigin
	@PostMapping(value = ALLOWANCE_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody AllowanceDTO allowanceDTO) {
		logger.info("Request:In Allowance Controller for Add Allowance :{} ", allowanceDTO);
		GenericResponse genericResponse = new GenericResponse();

		Allowance allowance = new Allowance();
		BeanUtils.copyProperties(allowanceDTO, allowance);
		allowance.setCreateTime(new Date());
		allowance.setUpdateTime(new Date());
		Long id = allowanceService.save(allowance);
		allowanceDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(allowanceDTO, "Successfully added", HttpStatus.OK), HttpStatus.OK);

	}

	/*
	 * ======================== Delete Allowance ========================
	 */

	@ApiOperation(value = "Delete Allowance", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = ALLOWANCE_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Allowance Controller Delete Allowance of ids  :{} ", idsArrays);

		int status = allowanceService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Successfully deleted", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Allowance ID");

			return new ResponseEntity<>(response.getResponse("", "Invalid Allowance ID", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Allowance Edit/Update ====================
	 */
	@CrossOrigin
	@PutMapping(value = ALLOWANCE_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody AllowanceDTO allowanceDTO) {
		logger.info("Request:In Allowance Controller for Update Allowance :{} ", allowanceDTO);
		GenericResponse genericResponse = new GenericResponse();

		Allowance mAllowance = allowanceService.findById(id);

		if (mAllowance != null) {

			Allowance allowance = new Allowance();
			BeanUtils.copyProperties(allowanceDTO, allowance);
			allowance.setId(id);
			allowance.setCreateTime(mAllowance.getCreateTime());
			allowance.setUpdateTime(new Date());

			Long mID = allowanceService.save(allowance);
			allowanceDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(allowanceDTO, "Allowance successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Allowance", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Get All Allowance =================
	 */

	@ApiOperation(value = "Get All Allowance", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ALLOWANCE_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll() {
		logger.info("Rest request to get all Allowance");

		List<Allowance> mAllowance = allowanceService.findAll();
		List<AllowanceDTO> mAllowanceList = new ArrayList<>();
		mAllowanceList = mAllowance.stream().map(allowance -> {
			AllowanceDTO mAllowanceDTO = new AllowanceDTO();
			BeanUtils.copyProperties(allowance, mAllowanceDTO);
			return mAllowanceDTO;
		}).collect(Collectors.toList());

		return onSuccess(mAllowanceList, Constants.ALLOWANCE_FETCHED);
	}

	/*
	 * ======================== Find One Allowance ========================
	 */

	@ApiOperation(value = "Find Allowancee By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ALLOWANCE_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Allowance Controller Find Allowance By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		Allowance allowance = allowanceService.findById(id);
		String msg = allowance != null ? "Allowance Found" : "No Allowance found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		AllowanceDTO mAllowanceDTO = new AllowanceDTO();
		BeanUtils.copyProperties(allowance, mAllowanceDTO);
		return new ResponseEntity<>(response.getResponse(mAllowanceDTO,
				mAllowanceDTO != null ? "Allowance Found" : "No Allowance found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Select Allowance========================
	 */

	@ApiOperation(value = "Update Allowance Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ALLOWANCE_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<Long> idsArrays,
			@PathVariable Integer status) {
		log.info("REST Request In Allowance Controller to update status {} {},", idsArrays, status);
		int res = allowanceService.updateStatus(status, idsArrays);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Allowance Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Allowance successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Allowance");

			return new ResponseEntity<>(response.getResponse("", "Invalid Allowance", HttpStatus.OK), HttpStatus.OK);
		}
	}

}
