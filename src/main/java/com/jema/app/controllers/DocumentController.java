/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 29-Apr-2023
*
*/

package com.jema.app.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.jema.app.dto.DocumentDTO;
import com.jema.app.entities.Document;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.DocumentService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Document Controller")
@RestController
public class DocumentController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(DocumentController.class);

	@Autowired
	DocumentService documentService;

	/*
	 * ======================== Document ADD =================
	 */
	@ApiOperation(value = "Add Document", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
	@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = DOCUMENT_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody DocumentDTO documentDTO) {
		logger.info("Request:In Document Controller for Add Document :{} ", documentDTO);
		GenericResponse genericResponse = new GenericResponse();

		Document document = new Document();
		BeanUtils.copyProperties(documentDTO, document);
		document.setCreateTime(new Date());
		document.setUpdateTime(new Date());
		Long id = documentService.save(document);
		documentDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(documentDTO, "Successfully added", HttpStatus.OK), HttpStatus.OK);

	}

	/*
	 * ======================== Delete Document ========================
	 */

	@ApiOperation(value = "Delete Document", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = DOCUMENT_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Document Controller Delete Document of ids  :{} ", idsArrays);

		int status = documentService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Successfully deleted", HttpStatus.OK), HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Document ID");

			return new ResponseEntity<>(response.getResponse("", "Invalid Document ID", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Document Edit/Update ====================
	 */
	@CrossOrigin
	@PutMapping(value = DOCUMENT_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody DocumentDTO DocumentDTO) {
		logger.info("Request:In Document Controller for Update Document :{} ", DocumentDTO);
		GenericResponse genericResponse = new GenericResponse();

		Document mDocument = documentService.findById(id);

		if (mDocument != null) {

			Document document = new Document();
			BeanUtils.copyProperties(DocumentDTO, document);
			document.setId(id);
			document.setCreateTime(mDocument.getCreateTime());
			document.setUpdateTime(new Date());

			Long mID = documentService.save(document);
			DocumentDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(DocumentDTO, "Document successfully Updated", HttpStatus.OK),
					HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Document", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Get All Document =================
	 */

	@ApiOperation(value = "Get All Document", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = DOCUMENT_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll() {
		logger.info("Rest request to get all Document");

		List<Document> mDocument = documentService.findAll();
		List<DocumentDTO> mDocumentList = new ArrayList<>();
		mDocumentList = mDocument.stream().map(document -> {
			DocumentDTO mDocumentDTO = new DocumentDTO();
			BeanUtils.copyProperties(document, mDocumentDTO);
			return mDocumentDTO;
		}).collect(Collectors.toList());

		return onSuccess(mDocumentList, Constants.DOCUMENT_FETCHED);
	}

	/*
	 * ======================== Find One Document ========================
	 */

	@ApiOperation(value = "Find Document By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = DOCUMENT_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Document Controller Find Document By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		Document document = documentService.findById(id);
		String msg = document != null ? "Document Found" : "No Document found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);
		DocumentDTO mDocumentDTO = new DocumentDTO();
		BeanUtils.copyProperties(document, mDocumentDTO);
		return new ResponseEntity<>(response.getResponse(mDocumentDTO,
				mDocumentDTO != null ? "Document Found" : "No Document found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Select Document========================
	 */

	@ApiOperation(value = "Update Document Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = DOCUMENT_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestBody List<Long> idsArrays,
			@PathVariable Integer status) {
		log.info("REST Request In Document Controller to update status {} {},", idsArrays, status);
		int res = documentService.updateStatus(status, idsArrays);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Document Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Document successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Document");

			return new ResponseEntity<>(response.getResponse("", "Invalid Document", HttpStatus.OK), HttpStatus.OK);
		}
	}

}
