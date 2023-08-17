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
import com.jema.app.dto.CustomerSettingTaxListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.CustomerSettingTax;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.CustomerSettingTaxService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Customer Setting Tax Controller")
@RestController
public class CustomerSettingTaxController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(CustomerSettingTaxController.class);

	@Autowired
	CustomerSettingTaxService customerSettingTaxService;

	/*
	 * ======================== Tax ADD =================
	 */
	@ApiOperation(value = "Add Tax", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = CUSTOMER_SETTING_TAX_ADD, produces = "application/json")
	public ResponseEntity<?> addTax(@Valid @RequestBody CustomerSettingTaxDTO mCustomerSettingTaxDTO) {
		logger.info("Request:In Customer Setting Tax Controller for Add Tax :{} ", mCustomerSettingTaxDTO);
		GenericResponse genericResponse = new GenericResponse();

		CustomerSettingTax customerSettingTax = new CustomerSettingTax();
		BeanUtils.copyProperties(mCustomerSettingTaxDTO, customerSettingTax);
		customerSettingTax.setCreateTime(new Date());
		customerSettingTax.setUpdateTime(new Date());
		Long id = customerSettingTaxService.save(customerSettingTax);
		mCustomerSettingTaxDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mCustomerSettingTaxDTO, "Successfully added", HttpStatus.OK),
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
	@DeleteMapping(value = CUSTOMER_SETTING_TAX_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Customer Setting Tax Controller Delete Tax of ids  :{} ", idsArrays);

		int status = customerSettingTaxService.delete(idsArrays);
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
	@PutMapping(value = CUSTOMER_SETTING_TAX_UPDATE, produces = "application/json")
	public ResponseEntity<?> updateTax(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody CustomerSettingTaxDTO mCustomerSettingTaxDTO) {
		logger.info("Request:In Customer Setting Tax Controller for Update Tax :{} ", mCustomerSettingTaxDTO);
		GenericResponse genericResponse = new GenericResponse();

		CustomerSettingTax mCustomerSettingTax = customerSettingTaxService.findById(id);

		if (mCustomerSettingTax != null) {

			CustomerSettingTax customerSettingTax = new CustomerSettingTax();
			BeanUtils.copyProperties(mCustomerSettingTaxDTO, customerSettingTax);
			customerSettingTax.setId(id);
			customerSettingTax.setCreateTime(mCustomerSettingTax.getCreateTime());
			customerSettingTax.setUpdateTime(new Date());
			Long mID = customerSettingTaxService.save(customerSettingTax);
			mCustomerSettingTaxDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(mCustomerSettingTaxDTO, "Tax successfully Updated", HttpStatus.OK),
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
	@PostMapping(value = CUSTOMER_SETTING_TAX_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllTax(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Tax");

		Long recordsCount = 0l;

		List<CustomerSettingTaxListView> dataList = customerSettingTaxService.findAll(pageRequestDTO);

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
	@GetMapping(value = CUSTOMER_SETTING_TAX_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Customer Tax Find Tax By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		CustomerSettingTax customerSettingTax = customerSettingTaxService.findById(id);
		String msg = customerSettingTax != null ? "Tax Found" : "No Tax found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		CustomerSettingTaxDTO mCustomerSettingTaxDTO = new CustomerSettingTaxDTO();
		BeanUtils.copyProperties(customerSettingTax, mCustomerSettingTaxDTO);
		return new ResponseEntity<>(response.getResponse(mCustomerSettingTaxDTO,
				mCustomerSettingTaxDTO != null ? "Tax Found" : "No Tax found", HttpStatus.OK), HttpStatus.OK);
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
	@PutMapping(value = CUSTOMER_SETTING_TAX_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<Long> idsArrays,
			@PathVariable Integer status) {
		log.info("REST Request In Customer Tax Controller to update status {} {},", idsArrays, status);
		int res = customerSettingTaxService.updateStatus(status, idsArrays);
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
