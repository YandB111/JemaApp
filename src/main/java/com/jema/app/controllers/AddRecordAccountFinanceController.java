package com.jema.app.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.jema.app.entities.AdminAddType;
import com.jema.app.entities.FinanceAddRecordRequest;
import com.jema.app.repositories.AccountDetailsRepository;
import com.jema.app.repositories.FinanceRecordRepo;
import com.jema.app.response.FinanceRecordResponse;
import com.jema.app.response.FinanceResponse;
import com.jema.app.response.GenericResponse;
import com.jema.app.service.AccountDetailsService;
import com.jema.app.service.AddAdminTypeService;
import com.jema.app.service.AddRecordFinanceService;
import com.jema.app.service.CustomerOrderService;
import com.jema.app.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Add Record Account Details Controller")
@RestController
public class AddRecordAccountFinanceController extends ApiController {
	@Autowired
	FinanceRecordRepo financeRecordRepo;

	@Autowired
	private AccountDetailsService accountDetailsService;

	protected Logger logger = LoggerFactory.getLogger(AddRecordAccountFinanceController.class);

	@Autowired
	private AddAdminTypeService addTypeService;

	@Autowired
	private AddRecordFinanceService financeService;

	@Autowired
	AccountDetailsRepository accountDetailsRepository;

	@Autowired
	private CustomerOrderService customerServiceImpl;

	@Autowired
	private EmployeeService employeeService; // Inject EmployeeService

	@CrossOrigin
	@PostMapping(value = Finanace_Account_ADD, produces = "application/json")
	public ResponseEntity<?> saveRecord(@RequestBody FinanceAddRecordRequest record) {
		logger.info("Request: In Finance Controller for Add Finance: {}", record);
		try {
			FinanceAddRecordRequest addedRecord = financeService.save(record);
			return ResponseEntity.status(HttpStatus.CREATED).body(addedRecord);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee ID already exists.");
		}
	}

	@CrossOrigin
	@PutMapping(value = Finanace_Account_Update, produces = "application/json")
	public ResponseEntity<GenericResponse> updateRecord(@PathVariable Long id,
			@RequestBody FinanceAddRecordRequest updatedRecord) {
		logger.info("Request:In Finanace Controller for Update Finance :{} ", updatedRecord);
		try {
			FinanceAddRecordRequest updated = financeService.update(id, updatedRecord);
			return ResponseEntity.ok(new GenericResponse().getSuccess(updated, HttpStatus.OK));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GenericResponse().getResponse("Record not found with id: " + id, HttpStatus.NOT_FOUND));
		}
	}

	@CrossOrigin
	@DeleteMapping(value = Finanace_Account_Delete, produces = "application/json")
	public ResponseEntity<FinanceRecordResponse> deleteRecord(@PathVariable Long id) {
		logger.info("Request:In Finanace Controller for Delete Finance :{} ", id);
		financeService.deleteRecordById(id);
		return ResponseEntity.ok(new FinanceRecordResponse(true, "Record deleted successfully", null));
	}

	@ApiOperation(value = "Get All Finance Records with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = Finanace_Account_FindAll, produces = "application/json")
	public ResponseEntity<FinanceResponse> getAllRecords(
			@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		logger.info("Request:In GettingAllfinance Controller for gettingall Finance :{} ", pageable);

		Page<FinanceAddRecordRequest> records = financeService.getAllRecords(pageable);

		// Get the total number of records
		long totalRecords = records.getTotalElements();

		// Create a custom response object that includes the records and the total count
		FinanceResponse response = new FinanceResponse(records.getContent(), totalRecords);

		return ResponseEntity.ok(response);
	}

	@CrossOrigin
	@GetMapping(value = Finanace_Account_PayementType, produces = "application/json")
	public ResponseEntity<List<FinanceAddRecordRequest>> getRecordsByPaymentType(@RequestParam String paymentType) {
		List<FinanceAddRecordRequest> records = financeService.getRecordsByPaymentType(paymentType);
		return ResponseEntity.ok(records);
	}

	@CrossOrigin
	@GetMapping(value = Finanace_Account_RecordType, produces = "application/json")
	public ResponseEntity<List<FinanceAddRecordRequest>> getRecordsByRecordType(@RequestParam String recordType) {
		List<FinanceAddRecordRequest> records = financeService.getRecordsByRecordType(recordType);
		return ResponseEntity.ok(records);
	}

	@CrossOrigin
	@GetMapping(value = Finanace_Account_StatusType, produces = "application/json")
	public ResponseEntity<List<FinanceAddRecordRequest>> getRecordsByStatus(@RequestParam String status) {
		List<FinanceAddRecordRequest> records = financeService.getRecordsByStatus(status);
		return ResponseEntity.ok(records);
	}

	@CrossOrigin
	@GetMapping(value = Finanace_Account_AddType, produces = "application/json")
	public ResponseEntity<List<FinanceAddRecordRequest>> getRecordsByAddType(@RequestParam String addType) {
		List<FinanceAddRecordRequest> records = financeService.getRecordsByAddType(addType);
		return ResponseEntity.ok(records);
	}

	// getting all types from admin list
	@CrossOrigin
	@GetMapping(value = Finanace_Account_GETADMINTYPESALL, produces = "application/json")
	public ResponseEntity<List<AdminAddType>> getAllAddTypes() {
		List<AdminAddType> addTypes = addTypeService.getAllAddTypes();
		return ResponseEntity.ok(addTypes);
	}

	@CrossOrigin
	@GetMapping(value = Finanace_Account_FILTER, produces = "application/json")
	public ResponseEntity<List<FinanceAddRecordRequest>> getFilteredRecords(
			@RequestParam(name = "typeName", required = false) String typeName,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "recordType", required = false) String recordType,
			@RequestParam(name = "paymentType", required = false) String paymentType,
			@RequestParam(name = "addType", required = false) String addType) {

		List<FinanceAddRecordRequest> filteredRecords = financeService.getFilteredRecords(typeName, status, recordType,
				paymentType, addType);

		return ResponseEntity.ok(filteredRecords);
	}

	@CrossOrigin
	@GetMapping(value = Finanace_Account_FINANCERECORD, produces = "application/json")
	public ResponseEntity<Map<String, Object>> getRecordsAndTotals() {
		Double totalIncome = financeService.calculateTotalIncome();
		Double totalExpense = financeService.calculateTotalExpense();

		Map<String, Object> response = new HashMap<>();
		response.put("totalIncome", totalIncome);
		response.put("totalExpense", totalExpense);

		return ResponseEntity.ok(response);
	}

	@CrossOrigin
	@GetMapping(value = Finanace_Account_CALCULATE_BALANCE_ACCOUNT, produces = "application/json")
	public ResponseEntity<Long> getTotalBalance() {
		Long totalBalance = accountDetailsService.calculateTotalBalance();
		return ResponseEntity.ok(totalBalance);
	}

	@CrossOrigin
	@GetMapping(value = Finanace_Account_BALANCE_CUSTOMERORDER_PRICE, produces = "application/json")
	public ResponseEntity<Double> calculateTotalPriceSum() {
		Double totalPriceSum = customerServiceImpl.calculateTotalAmountReceived();
		return ResponseEntity.ok(totalPriceSum);
	}

	@CrossOrigin
	@GetMapping(value = Finanace_Account_BALANCE_EMPLOYEE_TOTALSALARY, produces = "application/json")
	public ResponseEntity<Double> getTotalSalarySum() {
		Double totalSalarySum = employeeService.calculateTotalSalarySum();
		return ResponseEntity.ok(totalSalarySum);
	}

	@ApiOperation(value = "Get a Finance Record by ID", response = FinanceAddRecordRequest.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved the Finance Record"),
			@ApiResponse(code = 404, message = "Finance Record not found") })
	@CrossOrigin
	@GetMapping(value = Finanace_Account_RECORD_FINDBYID, produces = "application/json")
	public ResponseEntity<FinanceAddRecordRequest> getFinanceRecordById(
			@ApiParam(value = "ID of the Finance Record to retrieve", required = true) @PathVariable Long id) {
		Optional<FinanceAddRecordRequest> record = financeService.findById(id);

		if (record.isPresent()) {
			return ResponseEntity.ok(record.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
