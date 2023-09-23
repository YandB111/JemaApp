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
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.CustomerDTO;
import com.jema.app.dto.CustomerListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.Customer;
import com.jema.app.repositories.CustomerRepository;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.CustomerService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Customer Controller")
@RestController
public class CustomerController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(CustomerController.class);

	//customService class
	@Autowired
	CustomerService mCustomerService;

	@Autowired
	CustomerRepository mCustomerRepository;

	/*
	 * ======================== Customer ADD ==================================
	 */

	@ApiOperation(value = "Add Customer", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = CUSTOMER_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody CustomerDTO mCustomerDTO) {
		logger.info("Request:In Customer Controller for Add Vendor :{} ", mCustomerDTO);
		GenericResponse genericResponse = new GenericResponse();

		Customer customer = new Customer();
		BeanUtils.copyProperties(mCustomerDTO, customer);
		customer.setCreateTime(new Date());
		customer.setUpdateTime(new Date());
		String id = mCustomerService.save(customer);
		mCustomerDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(mCustomerDTO, "Customer successfully added", HttpStatus.OK), HttpStatus.OK);

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
	@PostMapping(value = CUSTOMER_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllVendors(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Customer {} ", pageRequestDTO);

		Long recordsCount = 0l;

		List<CustomerListView> dataList = mCustomerService.findAll(pageRequestDTO);

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
	@PutMapping(value = CUSTOMER_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) String id,
			@Valid @RequestBody CustomerDTO customerDTO) {
		logger.info("Request: In Customer Controller for Update Customer :{} ", customerDTO);
		GenericResponse genericResponse = new GenericResponse();

		Customer existingCustomer = mCustomerService.findById(id);

		if (existingCustomer != null) {
			// Check if the updated email belongs to the same customer (same ID)
			String updatedEmail = customerDTO.getEmail();

			// Fetch the customer by email, excluding the current customer's ID
			Customer existingCustomerWithSameEmail = mCustomerRepository.findByEmailIgnoreCaseAndIdNot(updatedEmail,
					id);

			if (existingCustomerWithSameEmail == null) {
				// No conflict, proceed with the update
				Customer customerToUpdate = new Customer();
				BeanUtils.copyProperties(customerDTO, customerToUpdate);
				customerToUpdate.setId(id);
				customerToUpdate.setCreateTime(existingCustomer.getCreateTime());
				customerToUpdate.setUpdateTime(new Date());
				customerToUpdate.setStatus(existingCustomer.getStatus());
				customerToUpdate.setDeleted(existingCustomer.getDeleted());
				customerToUpdate.setBlock(existingCustomer.getBlock());

				String updatedCustomerId = mCustomerService.save(customerToUpdate);
				customerDTO.setId(updatedCustomerId);

				return new ResponseEntity<GenericResponse>(
						genericResponse.getResponse(customerDTO, "Customer successfully Updated", HttpStatus.OK),
						HttpStatus.OK);
			} else if (existingCustomerWithSameEmail.getId().equals(id)) {
				// The updated email belongs to the same customer, proceed with the update
				Customer customerToUpdate = new Customer();
				BeanUtils.copyProperties(customerDTO, customerToUpdate);
				customerToUpdate.setId(id);
				customerToUpdate.setCreateTime(existingCustomer.getCreateTime());
				customerToUpdate.setUpdateTime(new Date());
				customerToUpdate.setStatus(existingCustomer.getStatus());
				customerToUpdate.setDeleted(existingCustomer.getDeleted());
				customerToUpdate.setBlock(existingCustomer.getBlock());

				String updatedCustomerId = mCustomerService.save(customerToUpdate);
				customerDTO.setId(updatedCustomerId);

				return new ResponseEntity<GenericResponse>(
						genericResponse.getResponse(customerDTO, "Customer successfully Updated", HttpStatus.OK),
						HttpStatus.OK);
			} else {
				// A customer with the updated email already exists for a different ID, throw a
				// conflict exception
				return new ResponseEntity<>(genericResponse.getResponse("", "Email conflicts with another customer ",
						HttpStatus.CONFLICT), HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Customer ID", HttpStatus.OK),
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
	@GetMapping(value = CUSTOMER_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") String id) {
		logger.info("Request: In Customer Controller Find Customer By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		Customer mCustomer = mCustomerService.findById(id);
		String msg = mCustomer != null ? "Customer Found" : "No Customer found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(response.getResponse(mCustomer,
				mCustomer != null ? "Customer Found" : "No Customer found", HttpStatus.OK), HttpStatus.OK);
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
	@PutMapping(value = CUSTOMER_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<String> idsArrays,
			@PathVariable Integer status) {
		log.info("REST Request In Customer Controller to update status {} {},", idsArrays, status);
		int res = mCustomerService.updateStatus(idsArrays, status);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Customer Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Customer successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Customer");

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
	@PutMapping(value = CUSTOMER_BLOCK, produces = "application/json")
	public ResponseEntity<GenericResponse> block(@RequestBody List<String> idsArrays, @PathVariable Integer block) {
		log.info("REST Request In Customer Controller to block customer {} {},", idsArrays, block);
		int res = mCustomerService.block(idsArrays, block);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Customer Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Customer successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Customer");

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
	@DeleteMapping(value = CUSTOMER_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<String> idsArrays) {
		logger.info("Request: In Customer Controller Delete Customer of ids  :{} ", idsArrays);

		int status = mCustomerService.delete(idsArrays);
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
