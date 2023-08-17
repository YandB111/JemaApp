/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 07-Jun-2023
*
*/

package com.jema.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.jema.app.entities.OrderReason;
import com.jema.app.entities.Type;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.OrderReasonService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Order Reason Controller")
@RestController
public class OrderReasonController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(OrderReasonController.class);

	@Autowired
	OrderReasonService orderReasonService;

	/*
	 * ======================== Order Reason ADD =================
	 */
	@CrossOrigin
	@PostMapping(value = ORDER_REASON_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody OrderReason orderReason) {
		logger.info("Request:In OrderReason Controller for Add OrderReason :{} ", orderReason);
		GenericResponse genericResponse = new GenericResponse();

		Long id = orderReasonService.save(orderReason);
		orderReason.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(orderReason, "Successfully added", HttpStatus.OK), HttpStatus.OK);

	}

	/*
	 * ======================== Delete OrderReason ========================
	 */

	@ApiOperation(value = "Delete OrderReason", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = ORDER_REASON_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In OrderReason Controller Delete OrderReason of ids  :{} ", idsArrays);

		int status = orderReasonService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Successfully deleted", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Type ID");

			return new ResponseEntity<>(response.getResponse("", "Invalid Type ID", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== OrderReason Edit/Update ====================
	 */
	@CrossOrigin
	@PutMapping(value = ORDER_REASON_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody OrderReason orderReason) {
		logger.info("Request:In OrderReason Controller for Update OrderReason :{} ", orderReason);
		GenericResponse genericResponse = new GenericResponse();

		OrderReason mOrderReason = orderReasonService.findById(id);

		if (mOrderReason != null) {
			orderReason.setId(id);
			orderReasonService.save(orderReason);

			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(orderReason, "Order Reason successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Order Reason ID", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Get All OrderReason =================
	 */

	@ApiOperation(value = "Get All OrderReason", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ORDER_REASON_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllType() {
		logger.info("Rest request to get all OrderReason");

		List<OrderReason> mOrderReason = orderReasonService.findAll();
		return onSuccess(mOrderReason, Constants.ORDER_REASON_FETCHED);
	}
	
	/*
	 * ======================== Find One OrderReason ========================
	 */

	@ApiOperation(value = "Find OrderReason By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ORDER_REASON_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In OrderReason Controller Find OrderReason By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		OrderReason mOrderReason = orderReasonService.findById(id);
		String msg = mOrderReason != null ? "OrderReason Found" : "No OrderReason found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		return new ResponseEntity<>(
				response.getResponse(mOrderReason, mOrderReason != null ? "OrderReason Found" : "No OrderReason found", HttpStatus.OK),
				HttpStatus.OK);
	}

}
