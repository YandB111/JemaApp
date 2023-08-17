/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 11-Apr-2023
*
*/

package com.jema.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jema.app.response.GenericResponse;
import com.jema.app.service.impl.AwsS3BucketEngine;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Document Upload Controller")
@RestController
public class DocumentUploadController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(DocumentUploadController.class);

	@Autowired
	AwsS3BucketEngine awsS3BucketEngine;

	@Value("${AWS_PUBLIC_CONTENT_BUCKET}")
	private String publicBucket;

	/*
	 * ======================== Document Upload ==================================
	 */
	@ApiOperation(value = "Document Upload", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = DOCUMENT_UPLOAD, produces = "application/json")
	public ResponseEntity<?> uploadImage(@RequestParam(value = "image", required = true) MultipartFile image,
			@RequestParam(value = "type", required = true) String type) {
		logger.info("Request:In Image Controller for Upload Document : {} ", type);
		GenericResponse genericResponse = new GenericResponse();
		String imgURL = "";
		if (type.equalsIgnoreCase(Constants.IMAGE)) {
			imgURL = awsS3BucketEngine.uploadDocument(image, publicBucket, Constants.S3_IMAGES_FOLDER);
		} else if (type.equalsIgnoreCase(Constants.DOCUMENT)) {
			imgURL = awsS3BucketEngine.uploadDocument(image, publicBucket, Constants.S3_DOCUMENTS_FOLDER);
		} else {
			return onFailure("Invalid Type");
		}
		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(imgURL, "Document successfully uploaded", HttpStatus.OK), HttpStatus.OK);

	}

}
