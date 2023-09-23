package com.jema.app.controllers;

import java.util.List;

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

import com.jema.app.entities.AdminAddType;
import com.jema.app.service.AddAdminTypeService;

import io.swagger.annotations.Api;

@Api(value = "AddType Account Details Controller")
@RestController
public class AddTypeAdminController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(AddTypeAdminController.class);

	@Autowired
	private AddAdminTypeService addTypeService;

	@CrossOrigin
	@PostMapping(value = ADMIN_ADD_TYPE_ADD, produces = "application/json")
	public ResponseEntity<AdminAddType> addAddType(@RequestBody AdminAddType addType) {

		logger.info("Request: In Finance Controller for Add Finance: {}", addType);
		AdminAddType savedAddType = addTypeService.addAddType(addType);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedAddType);
	}

	@CrossOrigin
	@PutMapping(value = ADMIN_ADD_TYPE_UPDATE, produces = "application/json")
	public ResponseEntity<AdminAddType> updateAddType(@PathVariable Long id, @RequestBody AdminAddType updatedAddType) {
		logger.info("Request: In Finance Controller for Add Finance: {}", updatedAddType);
		AdminAddType updatedType = addTypeService.updateAddType(id, updatedAddType);
		return ResponseEntity.ok(updatedType);
	}

	@CrossOrigin
	@DeleteMapping(value = ADMIN_ADD_TYPE_DELETE, produces = "application/json")
	public ResponseEntity<Void> deleteAddType(@PathVariable Long id) {
		addTypeService.deleteAddType(id);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@GetMapping(value = ADMIN_ADD_TYPE_FINDALL, produces = "application/json")
	public ResponseEntity<List<AdminAddType>> getAllAddTypes() {
		List<AdminAddType> addTypes = addTypeService.getAllAddTypes();
		return ResponseEntity.ok(addTypes);
	}
}
