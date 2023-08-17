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
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.cognitoidp.model.AliasExistsException;
import com.jema.app.dto.CustomerDTO;
import com.jema.app.dto.CustomerListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.Customer;
import com.jema.app.repositories.CustomerRepository;
import com.jema.app.response.DepartmentErrorResponse;
import com.jema.app.response.GenericResponse;
import com.jema.app.response.ResponseErrorChemical;
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

	@Autowired
	CustomerService mCustomerService;
	
	@Autowired
	CustomerRepository mCustomerRepository;

	/*
	 * ======================== Customer ADD ==================================
	 */
	@ApiOperation(value = "Add Customer", response = Iterable.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully Updated."),
	        @ApiResponse(code = 400, message = "Bad Request: Invalid input or duplicate email/name."),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
	        @ApiResponse(code = 500, message = "Internal Server Error: An unexpected error occurred.")
	})
	@CrossOrigin
	@PostMapping(value = CUSTOMER_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody CustomerDTO mCustomerDTO) {
	    logger.info("Request: In Customer Controller for Add Vendor: {}", mCustomerDTO);
	    GenericResponse genericResponse = new GenericResponse();

	    try {
	        Customer customer = new Customer();
	        BeanUtils.copyProperties(mCustomerDTO, customer);
	        customer.setCreateTime(new Date());
	        customer.setUpdateTime(new Date());

	        if (isEmailOrNameExists(customer.getEmail(), customer.getName())) {
	            // Customer data conflict
	            DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
	            customResponse.setStatus(HttpStatus.CONFLICT.value());
	            customResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
	            customResponse.setMessage("Customer with the same email or name already exists");
	            customResponse.setTimestamp(new Date());
	            return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
	        }

	        String id = mCustomerService.save(customer);
	        mCustomerDTO.setId(id);

	        return new ResponseEntity<>(
	                genericResponse.getResponse(mCustomerDTO, "Customer successfully added", HttpStatus.OK),
	                HttpStatus.OK
	        );
	    } catch (ResponseStatusException e) {
	        // Handle the ResponseStatusException and send a custom error response
	        DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
	        customResponse.setStatus(e.getStatus().value());
	        customResponse.setError(e.getStatus().getReasonPhrase());
	        customResponse.setMessage(e.getReason());
	        customResponse.setTimestamp(new Date());
	        return new ResponseEntity<>(customResponse, e.getStatus());
	    } catch (IllegalArgumentException e) {
	        // Handle the IllegalArgumentException and send a custom error response
	        DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
	        customResponse.setStatus(HttpStatus.BAD_REQUEST.value());
	        customResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
	        customResponse.setMessage(e.getMessage());
	        customResponse.setTimestamp(new Date());
	        return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        // Handle any other unexpected exceptions and send a custom error response
	        DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
	        customResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	        customResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	        customResponse.setMessage("An unexpected error occurred.");
	        customResponse.setTimestamp(new Date());
	        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}




	

	/*
	 * ======================== Get All Customer ==================================
	 */

	private boolean isEmailOrNameExists(String email, String name) {
	    return mCustomerRepository.existsByEmail(email) || mCustomerRepository.existsByName(name);
	}






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
	    logger.info("Request:In Customer Controller for Update Customer :{} ", customerDTO);
	    GenericResponse genericResponse = new GenericResponse();

	    Customer mCustomer = mCustomerService.findById(id);

	    if (mCustomer != null) {
	        String newEmail = customerDTO.getEmail();
	        String newName = customerDTO.getName();

	        // Check if the new name is different and not already taken
	        if (newName != null && !newName.equalsIgnoreCase(mCustomer.getName())) {
	            newName = getUniqueName(newName, id);
	        }

	        // Check if the new email is different and not already taken
	        if (newEmail != null && !newEmail.equalsIgnoreCase(mCustomer.getEmail())) {
	            newEmail = getUniqueEmail(newEmail, id);
	        }

	        Customer customer = new Customer();
	        BeanUtils.copyProperties(customerDTO, customer);
	        customer.setId(id);
	        customer.setCreateTime(mCustomer.getCreateTime());
	        customer.setUpdateTime(new Date());
	        customer.setStatus(mCustomer.getStatus());
	        customer.setDeleted(mCustomer.getDeleted());
	        customer.setBlock(mCustomer.getBlock());

	        if (newEmail != null) {
	            customer.setEmail(newEmail);
	        }
	        if (newName != null) {
	            customer.setName(newName);
	        }

	        String mID = mCustomerService.save(customer);
	        customerDTO.setId(mID);
	        return new ResponseEntity<GenericResponse>(
	                genericResponse.getResponse(customerDTO, "Customer successfully Updated", HttpStatus.OK),
	                HttpStatus.OK);

	    } else {
	        return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Customer", HttpStatus.OK),
	                HttpStatus.OK);
	    }
	}

	private String getUniqueName(String newName, String id) {
	    int appendCounter = 1;
	    String originalName = newName;
	    
	    while (mCustomerService.isEmailOrNameExists(null, newName, id)) {
	        newName = originalName.toLowerCase() + "_" + appendCounter;
	        appendCounter++;
	    }
	    
	    return newName;
	}

	private String getUniqueEmail(String newEmail, String id) {
	    int appendCounter = 1;
	    String originalEmail = newEmail;
	    
	    while (mCustomerService.isEmailOrNameExists(newEmail, null, id)) {
	        newEmail = originalEmail.toLowerCase() + "_" + appendCounter;
	        appendCounter++;
	    }
	    
	    return newEmail;
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

		return new ResponseEntity<>(
				response.getResponse(mCustomer, mCustomer != null ? "Customer Found" : "No Customer found", HttpStatus.OK),
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
	@PutMapping(value = CUSTOMER_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@PathVariable String id, @PathVariable Integer status) {
		log.info("REST Request In Customer Controller to update status {} {},", id, status);
		int res = mCustomerService.updateStatus(id, status);
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
	@PutMapping(value = CUSTOMER_BLOCK, produces = "application/json")
	public ResponseEntity<GenericResponse> block(@PathVariable String id, @PathVariable Integer block) {
		log.info("REST Request In Customer Controller to block customer {} {},", id, block);
		int res = mCustomerService.block(id, block);
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
