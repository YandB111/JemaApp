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

import com.jema.app.dto.ElutionCustomerSettingStatusDTO;
import com.jema.app.dto.ElutionMachineRawDTO;
import com.jema.app.dto.ElutionMachineRawListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.ElutionCustomerSettingStatus;
import com.jema.app.entities.ElutionMachineRaw;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.ElutionMachineRawService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Elution Machine Raw Controller")
@RestController
public class ElutionMachineRawController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ElutionMachineRawController.class);

	@Autowired
	ElutionMachineRawService mElutionMachineRawService;


	/*
	 * ======================== Raw ADD =================
	 */
	@ApiOperation(value = "Add Raw", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_MACHINE_SETTING_RAW_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ElutionMachineRawDTO mElutionMachineRawDTO) {
		logger.info("Request:In Elution Machine Raw Controller for Add Raw :{} ", mElutionMachineRawDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionMachineRaw mElutionMachineRaw = new ElutionMachineRaw();
		BeanUtils.copyProperties(mElutionMachineRawDTO, mElutionMachineRaw);
		mElutionMachineRaw.setCreateTime(new Date());
		mElutionMachineRaw.setUpdateTime(new Date());
		Long id = mElutionMachineRawService.save(mElutionMachineRaw);
		mElutionMachineRawDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mElutionMachineRawDTO, "Successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}
	
	
	/*
	 * ======================== Get All Machine Raw =================
	 */

	@ApiOperation(value = "Get All Raw", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_MACHINE_SETTING_RAW_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Elution Machine Raw");

		Long recordsCount = 0l;

		List<ElutionMachineRawListView> dataList = mElutionMachineRawService.findAll(pageRequestDTO);

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
	 * ======================== Delete Machine Raw ========================
	 */

	@ApiOperation(value = "Delete Raw", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = ELUTION_MACHINE_SETTING_RAW_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Elution Machine Raw Controller Delete Raw of ids  :{} ", idsArrays);

		int status = mElutionMachineRawService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Successfully deleted", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Raw ID");

			return new ResponseEntity<>(response.getResponse("", "Invalid Raw ID", HttpStatus.OK), HttpStatus.OK);
		}
	}

	
	/*
	 * ======================== Raw Edit/Update ====================
	 */
	@ApiOperation(value = "Update Raw", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_MACHINE_SETTING_RAW_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody ElutionMachineRawDTO mElutionMachineRawDTO) {
		logger.info("Request:In Elution Machine Raw Controller for Update Raw :{} ", mElutionMachineRawDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionMachineRaw mElutionMachineRaw = mElutionMachineRawService.findById(id);

		if (mElutionMachineRaw != null) {

			ElutionMachineRaw elutionMachineRaw = new ElutionMachineRaw();
			BeanUtils.copyProperties(mElutionMachineRawDTO, elutionMachineRaw);
			elutionMachineRaw.setId(id);
			elutionMachineRaw.setCreateTime(mElutionMachineRaw.getCreateTime());
			elutionMachineRaw.setUpdateTime(new Date());
			elutionMachineRaw.setStatus(mElutionMachineRaw.getStatus());
			Long mID = mElutionMachineRawService.save(elutionMachineRaw);
			mElutionMachineRawDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(mElutionMachineRawDTO, "Successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Raw", HttpStatus.OK), HttpStatus.OK);
		}

	}

	

	/*
	 * ======================== Find One Raw ========================
	 */

	@ApiOperation(value = "Find Raw By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ELUTION_MACHINE_SETTING_RAW_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Elution Machine Raw Controller Find Raw By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		ElutionMachineRaw mElutionMachineRaw = mElutionMachineRawService.findById(id);
		String msg = mElutionMachineRaw != null ? "Raw Found" : "No Raw found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		ElutionMachineRawDTO mElutionMachineRawDTO = new ElutionMachineRawDTO();
		BeanUtils.copyProperties(mElutionMachineRaw, mElutionMachineRawDTO);
		return new ResponseEntity<>(response.getResponse(mElutionMachineRawDTO,
				mElutionMachineRawDTO != null ? "Raw Found" : "No Raw found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Select Raw========================
	 */

	@ApiOperation(value = "Update Raw Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_MACHINE_SETTING_RAW_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<Long> idsArrays,
			@PathVariable Integer status) {
		log.info("REST Request In Elution Machine Raw Controller to update status {} {},", idsArrays, status);
		int res = mElutionMachineRawService.updateStatus(status, idsArrays);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Status Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Status successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Raw");

			return new ResponseEntity<>(response.getResponse("", "Invalid Raw", HttpStatus.OK), HttpStatus.OK);
		}
	}


}
