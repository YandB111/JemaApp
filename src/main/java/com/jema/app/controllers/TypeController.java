/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Apr-2023
*
*/

package com.jema.app.controllers;

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

import com.jema.app.dto.TaxDTO;
import com.jema.app.entities.Tax;
import com.jema.app.entities.Type;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.TypeService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Type Controller")
@RestController
public class TypeController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(TypeController.class);

	@Autowired
	TypeService typeService;

	/*
	 * ======================== Type ADD =================
	 */
	@CrossOrigin
	@PostMapping(value = TYPE_ADD, produces = "application/json")
	public ResponseEntity<?> addType(@Valid @RequestBody Type type) {
		logger.info("Request:In Type Controller for Add Type :{} ", type);
		GenericResponse genericResponse = new GenericResponse();

		Long id = typeService.save(type);
		type.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(type, "Successfully added", HttpStatus.OK), HttpStatus.OK);

	}

	/*
	 * ======================== Delete Type ========================
	 */

	@ApiOperation(value = "Delete Type", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = TYPE_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Type Controller Delete Type of ids  :{} ", idsArrays);

		int status = typeService.delete(idsArrays);
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
	 * ======================== Type Edit/Update ====================
	 */
	@CrossOrigin
	@PutMapping(value = TYPE_UPDATE, produces = "application/json")
	public ResponseEntity<?> updateType(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody Type type) {
		logger.info("Request:In Type Controller for Update Type :{} ", type);
		GenericResponse genericResponse = new GenericResponse();

		Type mType = typeService.findById(id);

		if (mType != null) {
			type.setId(id);
			typeService.save(type);

			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(type, "Type successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Type ID", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Get All Type =================
	 */

	@ApiOperation(value = "Get All Type", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = TYPE_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllType() {
		logger.info("Rest request to get all TYPE");

		List<Type> mType = typeService.findAll();
		return onSuccess(mType, Constants.TYPE_FETCHED);
	}
	
	/*
	 * ======================== Find One Type ========================
	 */

	@ApiOperation(value = "Find Type By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = TYPE_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Type Controller Find Type By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		Type type = typeService.findById(id);
		String msg = type != null ? "Type Found" : "No Type found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		return new ResponseEntity<>(
				response.getResponse(type, type != null ? "Type Found" : "No Type found", HttpStatus.OK),
				HttpStatus.OK);
	}

}
