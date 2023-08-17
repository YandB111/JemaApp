/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 15-Jun-2023
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.ElutionCustomerDTO;
import com.jema.app.dto.ElutionCustomerListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.ElutionCustomer;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.ElutionCustomerService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Elution Customer Controller")
@RestController
public class ElutionCustomerController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ElutionCustomerController.class);

	@Autowired
	ElutionCustomerService mElutionCustomerService;

	/*
	 * ======================== Customer ADD ==================================
	 */
	@ApiOperation(value = "Add Customer", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_CUSTOMER_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ElutionCustomerDTO mElutionCustomerDTO) {
		logger.info("Request:In Elution Customer Controller for Add Vendor :{} ", mElutionCustomerDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionCustomer mElutionCustomer = new ElutionCustomer();
		BeanUtils.copyProperties(mElutionCustomerDTO, mElutionCustomer);
		mElutionCustomer.setCreateTime(new Date());
		mElutionCustomer.setUpdateTime(new Date());
		String id = mElutionCustomerService.save(mElutionCustomer);
		mElutionCustomerDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mElutionCustomerDTO, "Customer successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}

	/*
	 * ======================== Get All Customer ==================================
	 */

	@ApiOperation(value = "Get All Customer with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ELUTION_CUSTOMER_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllVendors(@Valid @RequestBody PageRequestDTO pageRequestDTO,
			@RequestParam(name = "status", required = false) Integer status) {
		logger.info("Rest request to get all Elution Customer {} ", pageRequestDTO);

		Long recordsCount = 0l;

		List<ElutionCustomerListView> dataList = mElutionCustomerService.findAll(pageRequestDTO, status);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.CUSTOMER_FETCHED);
	}

	/*
	 * ======================== Customer Edit/Update ========================
	 */
	@ApiOperation(value = "Update Customer", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_CUSTOMER_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) String id,
			@Valid @RequestBody ElutionCustomerDTO mElutionCustomerDTO) {
		logger.info("Request:In Elution Customer Controller for Update Customer :{} ", mElutionCustomerDTO);
		GenericResponse genericResponse = new GenericResponse();

		ElutionCustomer mElutionCustomer = mElutionCustomerService.findById(id);

		if (mElutionCustomer != null) {

			ElutionCustomer elutionCustomer = new ElutionCustomer();
			BeanUtils.copyProperties(mElutionCustomerDTO, elutionCustomer);
			elutionCustomer.setId(id);
			elutionCustomer.setCreateTime(mElutionCustomer.getCreateTime());
			elutionCustomer.setUpdateTime(new Date());
			elutionCustomer.setStatus(mElutionCustomer.getStatus());
			elutionCustomer.setDeleted(mElutionCustomer.getDeleted());
			elutionCustomer.setBlock(mElutionCustomer.getBlock());

			String mID = mElutionCustomerService.save(elutionCustomer);
			mElutionCustomerDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(mElutionCustomerDTO, "Customer successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Customer", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Find One Customer ========================
	 */

	@ApiOperation(value = "Find Customer By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ELUTION_CUSTOMER_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") String id) {
		logger.info("Request: In Elution Customer Controller Find Customer By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		ElutionCustomer mElutionCustomer = mElutionCustomerService.findById(id);
		String msg = mElutionCustomer != null ? "Customer Found" : "No Customer found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(
				response.getResponse(mElutionCustomer,
						mElutionCustomer != null ? "Customer Found" : "No Customer found", HttpStatus.OK),
				HttpStatus.OK);
	}

	/*
	 * ======================== Update Customer Status========================
	 */

	@ApiOperation(value = "Update Customer Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_CUSTOMER_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@PathVariable String id, @PathVariable Integer status) {
		log.info("REST Request In Elution Customer Controller to update status {} {},", id, status);
		int res = mElutionCustomerService.updateStatus(id, status);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Customer Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Customer successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Customer");

			return new ResponseEntity<>(response.getResponse("", "Invalid Customer", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Block/UnBlock Customer ========================
	 */

	@ApiOperation(value = "Block/UnBlock Customer ", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ELUTION_CUSTOMER_BLOCK, produces = "application/json")
	public ResponseEntity<GenericResponse> block(@PathVariable String id, @PathVariable Integer block) {
		log.info("REST Request In Elution Customer Controller to block customer {} {},", id, block);
		int res = mElutionCustomerService.block(id, block);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Customer Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Customer successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Customer");

			return new ResponseEntity<>(response.getResponse("", "Invalid Customer", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Delete Customer ========================
	 */

	@ApiOperation(value = "Delete Customer", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = ELUTION_CUSTOMER_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<String> idsArrays) {
		logger.info("Request: In Elution Customer Controller Delete Customer of ids  :{} ", idsArrays);

		int status = mElutionCustomerService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Customer Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Customer successfully deleted", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Customer");

			return new ResponseEntity<>(response.getResponse("", "Invalid Customer", HttpStatus.OK), HttpStatus.OK);
		}
	}

}
