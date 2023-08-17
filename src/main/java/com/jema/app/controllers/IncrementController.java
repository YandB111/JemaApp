/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.entities.Increment;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.IncrementService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Increment Controller")
@RestController
public class IncrementController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(IncrementController.class);

	@Autowired
	IncrementService incrementService;

	/*
	 * ========================ADD Increment=================
	 */
	@CrossOrigin
	@PostMapping(value = INCREMENT_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody Increment increment) {
		logger.info("Request:In Increment Controller for Add Type :{} ", increment);
		GenericResponse genericResponse = new GenericResponse();

		Long id = incrementService.save(increment);
		increment.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(increment, "Successfully added", HttpStatus.OK), HttpStatus.OK);

	}

	/*
	 * ======================== Get All Increment =================
	 */

	@ApiOperation(value = "Get All Increment", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = INCREMENT_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@PathVariable(name = "employeeid", required = true) Long employeeid) {
		logger.info("Rest request to get all Increment");

		List<Increment> mType = incrementService.findAll(employeeid);

		return onSuccess(mType, Constants.INCREMENT_FETCHED);

	}

}
