package com.jema.app.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.jema.app.dto.AboutUsDTO;
import com.jema.app.entities.AboutPolcies;
import com.jema.app.entities.AboutPoliciesUpdateRequest;
import com.jema.app.repositories.AboutPoliciesRepo;
import com.jema.app.service.AboutPolicyService;

import io.swagger.annotations.Api;

@Api(value = "About Policy Controller")
@RestController
public class SettingAgreementPolicy extends ApiController {
	protected Logger logger = LoggerFactory.getLogger(SettingAgreementPolicy.class);

	@Autowired
	private AboutPolicyService aboutPolicyService;

	@Autowired
	private AboutPoliciesRepo aboutPoliciesRepo;

	@CrossOrigin
	@PostMapping(value = AboutAgreement, produces = "application/json")
	public ResponseEntity<Object> updateContent(@RequestBody AboutPoliciesUpdateRequest request) {
		 try {
		
			 AboutPolcies updatedPolcies = aboutPolicyService.updateContent(request);
		        return ResponseEntity.ok(updatedPolcies);
		    } catch (NotFoundException e) {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND)
		                .body("AboutPolcies not found with id: " + request.getId());
		    }
	}

	@CrossOrigin
	@GetMapping(value = About_add_get, produces = "application/json")
	public ResponseEntity<List<AboutPolcies>> getAllAboutPolcies() {
		List<AboutPolcies> allAboutPolcies = aboutPolicyService.getAllAboutPolcies();
		return ResponseEntity.ok(allAboutPolcies);
	}
}
