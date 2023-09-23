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

import com.jema.app.dto.ElutionCustomerSettingLeadStatusDTO;
import com.jema.app.dto.ElutionCustomerSettingLeadStatusListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.ElutionCustomerSettingLeadStatus;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.ElutionCustomerSettingLeadStatusService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Elution Customer Setting Lead Status Controller")
@RestController
public class ElutionCustomerSettingLeadStatusController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ElutionCustomerSettingLeadStatusController.class);

	@Autowired
	ElutionCustomerSettingLeadStatusService mElutionCustomerSettingLeadStatusService;


	/*
	 * ======================== Status ADD =================
	 */
	@ApiOperation(value = "Add Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_CUSTOMER_SETTING_LEAD_STATUS_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ElutionCustomerSettingLeadStatusDTO mElutionCustomerSettingLeadStatusDTO) {
		logger.info("Request:In Elution Customer Setting Lead Status Controller for Add Status :{} ", mElutionCustomerSettingLeadStatusDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionCustomerSettingLeadStatus mElutionCustomerSettingLeadStatus = new ElutionCustomerSettingLeadStatus();
		BeanUtils.copyProperties(mElutionCustomerSettingLeadStatusDTO, mElutionCustomerSettingLeadStatus);
		mElutionCustomerSettingLeadStatus.setCreateTime(new Date());
		mElutionCustomerSettingLeadStatus.setUpdateTime(new Date());

		
		try {

		Long id = mElutionCustomerSettingLeadStatusService.save(mElutionCustomerSettingLeadStatus);
		mElutionCustomerSettingLeadStatusDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mElutionCustomerSettingLeadStatusDTO, "Successfully added", HttpStatus.OK),
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
	@PostMapping(value = ELUTION_CUSTOMER_SETTING_LEAD_STATUS_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Elution Lead Status");

		Long recordsCount = 0l;

		List<ElutionCustomerSettingLeadStatusListView> dataList = mElutionCustomerSettingLeadStatusService.findAll(pageRequestDTO);

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
	@DeleteMapping(value = ELUTION_CUSTOMER_SETTING_LEAD_STATUS_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Elution Customer Setting Lead Status Controller Delete Status of ids  :{} ", idsArrays);

		int status = mElutionCustomerSettingLeadStatusService.delete(idsArrays);
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
	@PutMapping(value = ELUTION_CUSTOMER_SETTING_LEAD_STATUS_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody ElutionCustomerSettingLeadStatusDTO mElutionCustomerSettingLeadStatusDTO) {
		logger.info("Request:In Elution Customer Setting Lead Status Controller for Update Status :{} ", mElutionCustomerSettingLeadStatusDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionCustomerSettingLeadStatus mElutionCustomerSettingLeadStatus = mElutionCustomerSettingLeadStatusService.findById(id);

		if (mElutionCustomerSettingLeadStatus != null) {

			ElutionCustomerSettingLeadStatus elutionCustomerSettingLeadStatus = new ElutionCustomerSettingLeadStatus();
			BeanUtils.copyProperties(mElutionCustomerSettingLeadStatusDTO, elutionCustomerSettingLeadStatus);
			elutionCustomerSettingLeadStatus.setId(id);
			elutionCustomerSettingLeadStatus.setStatus(mElutionCustomerSettingLeadStatus.getStatus());
			elutionCustomerSettingLeadStatus.setCreateTime(mElutionCustomerSettingLeadStatus.getCreateTime());
			elutionCustomerSettingLeadStatus.setUpdateTime(new Date());
			Long mID = mElutionCustomerSettingLeadStatusService.save(elutionCustomerSettingLeadStatus);
			mElutionCustomerSettingLeadStatusDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(mElutionCustomerSettingLeadStatusDTO, "Status successfully Updated", HttpStatus.OK),
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
	@GetMapping(value = ELUTION_CUSTOMER_SETTING_LEAD_STATUS_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Elution Customer Lead Status Controller Find Lead Status By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		ElutionCustomerSettingLeadStatus mElutionCustomerSettingLeadStatus = mElutionCustomerSettingLeadStatusService.findById(id);
		String msg = mElutionCustomerSettingLeadStatus != null ? "Status Found" : "No Status found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		ElutionCustomerSettingLeadStatusDTO mElutionCustomerSettingLeadStatusDTO = new ElutionCustomerSettingLeadStatusDTO();
		BeanUtils.copyProperties(mElutionCustomerSettingLeadStatus, mElutionCustomerSettingLeadStatusDTO);
		return new ResponseEntity<>(response.getResponse(mElutionCustomerSettingLeadStatusDTO,
				mElutionCustomerSettingLeadStatusDTO != null ? "Status Found" : "No Status found", HttpStatus.OK), HttpStatus.OK);
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
	@PutMapping(value = ELUTION_CUSTOMER_SETTING_LEAD_STATUS_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<Long> idsArrays,
			@PathVariable Integer status) {
		log.info("REST Request In Elution Customer Lead Status Controller to update status {} {},", idsArrays, status);
		int res = mElutionCustomerSettingLeadStatusService.updateStatus(status, idsArrays);
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
