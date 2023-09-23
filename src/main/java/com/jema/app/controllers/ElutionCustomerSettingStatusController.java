/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 26-May-2023
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
import org.springframework.web.server.ResponseStatusException;

import com.jema.app.dto.CustomerSettingStatusDTO;
import com.jema.app.dto.ElutionCustomerSettingStatusDTO;
import com.jema.app.dto.ElutionCustomerSettingStatusListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.CustomerSettingStatus;
import com.jema.app.entities.ElutionCustomerSettingStatus;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.ElutionCustomerSettingStatusService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Elution Customer Setting Status Controller")
@RestController
public class ElutionCustomerSettingStatusController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ElutionCustomerSettingStatusController.class);

	@Autowired
	ElutionCustomerSettingStatusService mElutionCustomerSettingStatusService;


	/*
	 * ======================== Status ADD =================
	 */
	@ApiOperation(value = "Add Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_CUSTOMER_SETTING_STATUS_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ElutionCustomerSettingStatusDTO mElutionCustomerSettingStatusDTO) {
		logger.info("Request:In Elution Customer Setting Status Controller for Add Status :{} ", mElutionCustomerSettingStatusDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionCustomerSettingStatus mElutionCustomerSettingStatus = new ElutionCustomerSettingStatus();
		BeanUtils.copyProperties(mElutionCustomerSettingStatusDTO, mElutionCustomerSettingStatus);
		mElutionCustomerSettingStatus.setCreateTime(new Date());
		mElutionCustomerSettingStatus.setUpdateTime(new Date());

		try {
		Long id = mElutionCustomerSettingStatusService.save(mElutionCustomerSettingStatus);
		mElutionCustomerSettingStatusDTO.setId(id);
		
		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mElutionCustomerSettingStatusDTO, "Successfully added", HttpStatus.OK),
				HttpStatus.OK);
		} catch (ResponseStatusException ex) {
			if (ex.getStatus() == HttpStatus.CONFLICT) {
				return new ResponseEntity<GenericResponse>(
						genericResponse.getResponse(null, ex.getReason(), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
			} else {
				throw ex;
			}
			
		}
	}
	
	
	/*
	 * ======================== Get All Status =================
	 */

	@ApiOperation(value = "Get All Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_CUSTOMER_SETTING_STATUS_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Elution Status");

		Long recordsCount = 0l;

		List<ElutionCustomerSettingStatusListView> dataList = mElutionCustomerSettingStatusService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		
		return onSuccess(obj, Constants.STATUS_FETCHED);
	}

	
	
	/*
	 * ======================== Delete Status ========================
	 */

	@ApiOperation(value = "Delete Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = ELUTION_CUSTOMER_SETTING_STATUS_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Elution Customer Setting Status Controller Delete Status of ids  :{} ", idsArrays);

		int status = mElutionCustomerSettingStatusService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Successfully deleted", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Status ID");

			return new ResponseEntity<>(response.getResponse("", "Invalid Status ID", HttpStatus.OK), HttpStatus.OK);
		}
	}

	
	/*
	 * ======================== Status Edit/Update ====================
	 */
	@ApiOperation(value = "Update Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_CUSTOMER_SETTING_STATUS_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody ElutionCustomerSettingStatusDTO mElutionCustomerSettingStatusDTO) {
		logger.info("Request:In Elution Customer Setting Status Controller for Update Status :{} ", mElutionCustomerSettingStatusDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionCustomerSettingStatus mElutionCustomerSettingStatus = mElutionCustomerSettingStatusService.findById(id);

		if (mElutionCustomerSettingStatus != null) {

			ElutionCustomerSettingStatus elutionCustomerSettingStatus = new ElutionCustomerSettingStatus();
			BeanUtils.copyProperties(mElutionCustomerSettingStatusDTO, elutionCustomerSettingStatus);
			elutionCustomerSettingStatus.setId(id);
			elutionCustomerSettingStatus.setCreateTime(mElutionCustomerSettingStatus.getCreateTime());
			elutionCustomerSettingStatus.setUpdateTime(new Date());
			Long mID = mElutionCustomerSettingStatusService.save(elutionCustomerSettingStatus);
			mElutionCustomerSettingStatusDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(mElutionCustomerSettingStatusDTO, "Status successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Status", HttpStatus.OK), HttpStatus.OK);
		}

	}

	

	/*
	 * ======================== Find One Status ========================
	 */

	@ApiOperation(value = "Find Status By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ELUTION_CUSTOMER_SETTING_STATUS_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Elution Customer Status Controller Find Status By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		ElutionCustomerSettingStatus mElutionCustomerSettingStatus = mElutionCustomerSettingStatusService.findById(id);
		String msg = mElutionCustomerSettingStatus != null ? "Status Found" : "No Status found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		ElutionCustomerSettingStatusDTO mElutionCustomerSettingStatusDTO = new ElutionCustomerSettingStatusDTO();
		BeanUtils.copyProperties(mElutionCustomerSettingStatus, mElutionCustomerSettingStatusDTO);
		return new ResponseEntity<>(response.getResponse(mElutionCustomerSettingStatusDTO,
				mElutionCustomerSettingStatusDTO != null ? "Status Found" : "No Status found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Select Status========================
	 */

	@ApiOperation(value = "Update Status Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_CUSTOMER_SETTING_STATUS_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<Long> idsArrays,
			@PathVariable Integer status) {
		log.info("REST Request In Elution Customer Status Controller to update status {} {},", idsArrays, status);
		int res = mElutionCustomerSettingStatusService.updateStatus(status, idsArrays);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Status Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Status successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Status");

			return new ResponseEntity<>(response.getResponse("", "Invalid Status", HttpStatus.OK), HttpStatus.OK);
		}
	}


}
