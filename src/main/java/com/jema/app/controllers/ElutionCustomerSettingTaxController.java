/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 25-May-2023
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

import com.jema.app.dto.CustomerSettingTaxDTO;
import com.jema.app.dto.ElutionCustomerSettingTaxDTO;
import com.jema.app.dto.ElutionCustomerSettingTaxListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.CustomerSettingTax;
import com.jema.app.entities.ElutionCustomerSettingTax;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.ElutionCustomerSettingTaxService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Elution Customer Setting Tax Controller")
@RestController
public class ElutionCustomerSettingTaxController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ElutionCustomerSettingTaxController.class);

	@Autowired
	ElutionCustomerSettingTaxService mElutionCustomerSettingTaxService;

	/*
	 * ======================== Tax ADD =================
	 */
	@ApiOperation(value = "Add Tax", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_CUSTOMER_SETTING_TAX_ADD, produces = "application/json")
	public ResponseEntity<?> addTax(@Valid @RequestBody ElutionCustomerSettingTaxDTO mElutionCustomerSettingTaxDTO) {
		logger.info("Request:In Elution Customer Setting Tax Controller for Add Tax :{} ", mElutionCustomerSettingTaxDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionCustomerSettingTax mElutionCustomerSettingTax = new ElutionCustomerSettingTax();
		BeanUtils.copyProperties(mElutionCustomerSettingTaxDTO, mElutionCustomerSettingTax);
		mElutionCustomerSettingTax.setCreateTime(new Date());
		mElutionCustomerSettingTax.setUpdateTime(new Date());
		Long id = mElutionCustomerSettingTaxService.save(mElutionCustomerSettingTax);
		mElutionCustomerSettingTaxDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mElutionCustomerSettingTaxDTO, "Successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}

	/*
	 * ======================== Delete Tax ========================
	 */

	@ApiOperation(value = "Delete Tax", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = ELUTION_CUSTOMER_SETTING_TAX_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Elution Customer Setting Tax Controller Delete Tax of ids  :{} ", idsArrays);

		int status = mElutionCustomerSettingTaxService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Successfully deleted", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Tax ID");

			return new ResponseEntity<>(response.getResponse("", "Invalid Tax ID", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Tax Edit/Update ====================
	 */
	@ApiOperation(value = "Update Tax", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_CUSTOMER_SETTING_TAX_UPDATE, produces = "application/json")
	public ResponseEntity<?> updateTax(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody ElutionCustomerSettingTaxDTO mElutionCustomerSettingTaxDTO) {
		logger.info("Request:In Elution Customer Setting Tax Controller for Update Tax :{} ", mElutionCustomerSettingTaxDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionCustomerSettingTax mElutionCustomerSettingTax = mElutionCustomerSettingTaxService.findById(id);

		if (mElutionCustomerSettingTax != null) {

			ElutionCustomerSettingTax elutionCustomerSettingTax = new ElutionCustomerSettingTax();
			BeanUtils.copyProperties(mElutionCustomerSettingTaxDTO, elutionCustomerSettingTax);
			elutionCustomerSettingTax.setId(id);
			elutionCustomerSettingTax.setCreateTime(mElutionCustomerSettingTax.getCreateTime());
			elutionCustomerSettingTax.setUpdateTime(new Date());
			Long mID = mElutionCustomerSettingTaxService.save(elutionCustomerSettingTax);
			mElutionCustomerSettingTaxDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(mElutionCustomerSettingTaxDTO, "Tax successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Tax", HttpStatus.OK), HttpStatus.OK);
		}

	}

	/*
	 * ======================== Get All Tax =================
	 */

	@ApiOperation(value = "Get All Tax", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_CUSTOMER_SETTING_TAX_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllTax(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Elution Customer Tax");

		Long recordsCount = 0l;

		List<ElutionCustomerSettingTaxListView> dataList = mElutionCustomerSettingTaxService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		
		return onSuccess(obj, Constants.TAX_FETCHED);
	}

	/*
	 * ======================== Find One Tax ========================
	 */

	@ApiOperation(value = "Find Tax By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ELUTION_CUSTOMER_SETTING_TAX_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Elution Customer Tax Find Tax By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		ElutionCustomerSettingTax mElutionCustomerSettingTax = mElutionCustomerSettingTaxService.findById(id);
		String msg = mElutionCustomerSettingTax != null ? "Tax Found" : "No Tax found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		ElutionCustomerSettingTaxDTO mElutionCustomerSettingTaxDTO = new ElutionCustomerSettingTaxDTO();
		BeanUtils.copyProperties(mElutionCustomerSettingTax, mElutionCustomerSettingTaxDTO);
		return new ResponseEntity<>(response.getResponse(mElutionCustomerSettingTaxDTO,
				mElutionCustomerSettingTaxDTO != null ? "Tax Found" : "No Tax found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Select Tax========================
	 */

	@ApiOperation(value = "Update Tax Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_CUSTOMER_SETTING_TAX_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<Long> idsArrays,
			@PathVariable Integer status) {
		log.info("REST Request In Elution Customer Tax Controller to update status {} {},", idsArrays, status);
		int res = mElutionCustomerSettingTaxService.updateStatus(status, idsArrays);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Tax Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Tax successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Tax");

			return new ResponseEntity<>(response.getResponse("", "Invalid Tax", HttpStatus.OK), HttpStatus.OK);
		}
	}

}
