/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 13-May-2023
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

import com.jema.app.dto.AccountDetailsDTO;
import com.jema.app.dto.AccountDetailsView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.AccountDetails;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.AccountDetailsService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Account Details Controller")
@RestController
public class AccountDetailsController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(AccountDetailsController.class);

	@Autowired
	AccountDetailsService accountDetailsService;

	/*
	 * ======================== Account Details ADD =======================
	 */
	@ApiOperation(value = "Account Details ADD", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully added"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ACCOUNT_DETAILS_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody AccountDetailsDTO AccountDetailsDTO) {
		logger.info("Request:In Account Details Controller for Add Account Details :{} ", AccountDetailsDTO);
		GenericResponse genericResponse = new GenericResponse();

		AccountDetails accountDetails = new AccountDetails();
		BeanUtils.copyProperties(AccountDetailsDTO, accountDetails);
		accountDetails.setCreateTime(new Date());
		accountDetails.setUpdateTime(new Date());
		Long id = accountDetailsService.save(accountDetails);
		AccountDetailsDTO.setId(id);

		return new ResponseEntity<GenericResponse>(
				genericResponse.getResponse(AccountDetailsDTO, "Account Details successfully added", HttpStatus.OK),
				HttpStatus.OK);

	}

	/*
	 * ======================== Account Details Edit/Update ==================
	 */
	@ApiOperation(value = "Account Details Edit/Update", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = ACCOUNT_DETAILS_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody AccountDetailsDTO accountDetailsDTO) {
		logger.info("Request:In Account Details Controller for Update Account Details :{} ", accountDetailsDTO);
		GenericResponse genericResponse = new GenericResponse();

		AccountDetails mAccountDetails = accountDetailsService.findById(id);

		if (mAccountDetails != null) {

			AccountDetails accountDetails = new AccountDetails();
			BeanUtils.copyProperties(accountDetailsDTO, accountDetails);
			accountDetails.setId(id);
			accountDetails.setCreateTime(mAccountDetails.getCreateTime());
			accountDetails.setUpdateTime(new Date());

			Long mID = accountDetailsService.save(accountDetails);
			accountDetailsDTO.setId(mID);
			return new ResponseEntity<GenericResponse>(genericResponse.getResponse(accountDetailsDTO,
					"Account Details successfully Updated", HttpStatus.OK), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse.getResponse("", "Invalid Account", HttpStatus.OK),
					HttpStatus.OK);
		}

	}

	/*
	 * ======================== Find One Account ========================
	 */

	@ApiOperation(value = "Find Account By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = ACCOUNT_DETAILS_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In AccountDetails Controller Find AccountDetails By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		AccountDetails mAccountDetails = accountDetailsService.findById(id);
		String msg = mAccountDetails != null ? "Account Found" : "No Account found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(response.getResponse(mAccountDetails,
				mAccountDetails != null ? "Account Found" : "No Account found", HttpStatus.OK), HttpStatus.OK);
	}

	/*
	 * ======================== Delete Account ========================
	 */

	@ApiOperation(value = "Delete Account", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = ACCOUNT_DETAILS_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In AccountDetails Controller Delete Account of ids  :{} ", idsArrays);

		int status = accountDetailsService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "AccountDetails Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "AccountDetails successfully deleted", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Account");

			return new ResponseEntity<>(response.getResponse("", "Invalid Account", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Update Branch Balance========================
	 */

	@ApiOperation(value = "Update Account Balance", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ACCOUNT_DETAILS_UPDATE_BALANCE, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "balance", required = true) Long balance) {
		log.info("REST Request In Account Details Controller to update balance {} {},", id, balance);
		int res = accountDetailsService.updateBalance(id, balance);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Account Balance Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Account Balance successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Account");

			return new ResponseEntity<>(response.getResponse("", "Invalid Account", HttpStatus.OK), HttpStatus.OK);
		}
	}
	
	
	/*
	 * ======================== Get All Accounts ======================
	 */

	@ApiOperation(value = "Get All Accounts with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = ACCOUNT_DETAILS_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAll(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Accounts {} ", pageRequestDTO);
		Long recordsCount = 0l;

		List<AccountDetailsView> dataList = accountDetailsService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);
		return onSuccess(obj, Constants.ACCOUNTS_FETCHED);
	}


}
