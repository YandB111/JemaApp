package com.jema.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.entities.Customer;
import com.jema.app.entities.CustomerOrder;
import com.jema.app.entities.Employee;
import com.jema.app.entities.FinanceAddRecordRequest;
import com.jema.app.entities.InventoryRequest;
import com.jema.app.entities.ReconsileVerifySystem;
import com.jema.app.entities.SalaryDetails;
import com.jema.app.entities.UpdateVerificationRequest;
import com.jema.app.repositories.CustomerRepository;
import com.jema.app.repositories.ReconsileAndVerifySystemRepo;
import com.jema.app.service.AddRecordFinanceService;
import com.jema.app.service.CustomerOrderService;
import com.jema.app.service.EmployeeService;
import com.jema.app.service.InventoryRequestService;
import com.jema.app.service.ReconsileVerifyService;
import com.jema.app.service.VendorService;

import io.swagger.annotations.Api;

@Api(value = "AuditAndReconsile Controller")
@RestController
public class ReconsileAndSystemController extends ApiController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CustomerOrderService customerOrderService;

	@Autowired
	private AddRecordFinanceService financeService;

	@Autowired
	private InventoryRequestService inventoryRequestService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	ReconsileAndVerifySystemRepo reconsileAndVerifySystemRepo;

	@Autowired
	ReconsileVerifyService reconsileVerifyService;

	@CrossOrigin
	@GetMapping(value = RECONSILE_GETALLDATA, produces = "application/json")
	public ResponseEntity<List<Map<String, Object>>> getAllData() {
		List<InventoryRequest> inventoryRequests = inventoryRequestService.findAllInventoryRequests();
		List<CustomerOrder> customerOrders = customerOrderService.findAllCustomerOrders();
		List<Employee> employees = employeeService.findAllEmployees();
		List<FinanceAddRecordRequest> financeRecords = financeService.findAllFinanceRecords();
		List<Map<String, Object>> selectedDataList = new ArrayList<>();

		for (InventoryRequest request : inventoryRequests) {
			Map<String, Object> selectedData = new HashMap<>();

			// Populate selectedData with InventoryRequest values
			selectedData.put("id", request.getId());

			// Check if entity already exists based on the ID
			Optional<ReconsileVerifySystem> existingEntityOptional = reconsileAndVerifySystemRepo
					.findById(request.getId());
			ReconsileVerifySystem entity;

			if (existingEntityOptional.isPresent()) {
				entity = existingEntityOptional.get();
			} else {
				entity = new ReconsileVerifySystem();
				entity.setId(request.getId());
				entity.setVerified("No");
				entity.setTypeOfPayment("InventoryPayement");
			}
			if (!existingEntityOptional.isPresent()) {
				entity.setTotalPrice(request.getTotalPrice());
				entity.setCreateDate(request.getCreateTime());
				entity.setPaymentMode(request.getPaymentMode());
				entity.setVendorName(vendorService.getVendorNameById(request.getVendorId()));
				entity.setRecordsId(request.getId());
				reconsileAndVerifySystemRepo.save(entity);
			} else if (entity.getVerified() == null) {
				entity.setVerified("No");
				reconsileAndVerifySystemRepo.save(entity); 
			}
			selectedData.put("price", request.getPrice());
			selectedData.put("totalPrice", entity.getTotalPrice()); 
			selectedData.put("createTime", entity.getCreateDate());
			selectedData.put("paymentMode", entity.getPaymentMode()); 
			selectedData.put("vendorId", request.getVendorId());
			selectedData.put("In Id", request.getId());

			String vendorName = vendorService.getVendorNameById(request.getVendorId());
			selectedData.put("vendorName", vendorName);
			selectedDataList.add(selectedData);
		}

		for (CustomerOrder order : customerOrders) {
			Map<String, Object> selectedData = new HashMap<>();

			selectedData.put("id", order.getId());
			Optional<ReconsileVerifySystem> existingEntity = reconsileAndVerifySystemRepo.findById(order.getId());
			ReconsileVerifySystem entity;

			if (existingEntity.isPresent()) {
				entity = existingEntity.get();
			} else {
				entity = new ReconsileVerifySystem();
				entity.setId(order.getId());
				entity.setVerified(null); 
				entity.setTypeOfPayment("OrderPayment");
				
			}
			String existingVerified = entity.getVerified();
			entity.setTotalPrice(order.getTotalPrice());
			entity.setCreateDate(order.getCreateTime());
			entity.setPaymentMode(order.getPaymentMode());
			
			Customer customer = customerRepository.findCustomerById(order.getCustomerId());

			if (customer != null) {
				entity.setVendorName(customer.getName());
			} else {
				entity.setVendorName("Unknown Vendor"); 
			}
			if (existingVerified == null) {
				entity.setVerified("No");
			}
			reconsileAndVerifySystemRepo.save(entity);
			selectedData.put("totalPrice", order.getTotalPrice());
			selectedData.put("createTime", order.getCreateTime());
			selectedData.put("paymentMode", order.getPaymentMode());
			selectedData.put("CustomerID", order.getCustomerId());

			if (customer != null) {
				selectedData.put("customerName", customer.getName());
			} else {
				selectedData.put("customerName", "Unknown Customer");
			}

			selectedDataList.add(selectedData);
		}

		for (Employee employee : employees) {
			Map<String, Object> selectedData = new HashMap<>();
			selectedData.put("id", employee.getId());
			Optional<ReconsileVerifySystem> existingEntityOptional = reconsileAndVerifySystemRepo
					.findById(employee.getEmployeeId());
			ReconsileVerifySystem entity;

			if (existingEntityOptional.isPresent()) {
				entity = existingEntityOptional.get();
			} else {
				entity = new ReconsileVerifySystem();
				entity.setId(employee.getEmployeeId());
				entity.setVerified("No");
				entity.setTypeOfPayment("Salary");
			}
			if (!existingEntityOptional.isPresent()) {
				entity.setCreateDate(employee.getCreateTime());
				entity.setVendorName(employee.getName());

				// Accessing SalaryDetails to get total salary
				SalaryDetails salaryDetails = employee.getSalaryDetails();
				if (salaryDetails != null) {
					entity.setTotalPrice(salaryDetails.getTotalSalary());
				} else {
					entity.setTotalPrice(0.0);
				}
				reconsileAndVerifySystemRepo.save(entity);
			}
			selectedData.put("name", employee.getName());
			selectedData.put("employeeId", employee.getEmployeeId());
			selectedData.put("totalSalary", entity.getTotalPrice());
			selectedData.put("createTime", entity.getCreateDate());
			selectedDataList.add(selectedData);
		}

		for (FinanceAddRecordRequest financeRecord : financeRecords) {
			Map<String, Object> selectedData = new HashMap<>();
			selectedData.put("id", financeRecord.getId());

			// Check if entity already exists based on the ID
			Optional<ReconsileVerifySystem> existingEntityOptional = reconsileAndVerifySystemRepo
					.findById(financeRecord.getEmployeeIds());
			ReconsileVerifySystem entity;

			if (existingEntityOptional.isPresent()) {
				entity = existingEntityOptional.get();
			} else {
				entity = new ReconsileVerifySystem();
				entity.setId(financeRecord.getEmployeeIds());
				entity.setVerified("No");
				entity.setTypeOfPayment("FinanacePayment");
			}

			if (!existingEntityOptional.isPresent()) {
				entity.setTotalPrice(financeRecord.getTotaPrice());
				entity.setForLocalcreateDate(financeRecord.getDueDate());
				entity.setPaymentMode(financeRecord.getPaymentType());
				entity.setVendorName(financeRecord.getEmployeeName());
				
			
				reconsileAndVerifySystemRepo.save(entity);
			}
			selectedData.put("paymentType", financeRecord.getPaymentType());
			selectedData.put("employeeIds", financeRecord.getEmployeeIds());
			selectedData.put("employeeName", financeRecord.getEmployeeName());
			selectedData.put("totaPrice", financeRecord.getTotaPrice());
			selectedData.put("dueDate", financeRecord.getDueDate());

			selectedDataList.add(selectedData);
		}

		return ResponseEntity.ok(selectedDataList);

	}

	@CrossOrigin
	@GetMapping(value = RECONSILE_GETALLRecords, produces = "application/json")
	public ResponseEntity<List<ReconsileVerifySystem>> getAllRecords() {
		List<ReconsileVerifySystem> records = reconsileVerifyService.getAllRecords();
		return ResponseEntity.ok(records);
	}

	@CrossOrigin
	@PostMapping(value = RECONSILE_GETVERICATION, produces = "application/json")
	public ResponseEntity<String> updateVerifiedStatus(@RequestBody UpdateVerificationRequest request) {
		List<String> updatedIds = new ArrayList<>();

		for (String id : request.getIds()) {
			ReconsileVerifySystem entity = reconsileAndVerifySystemRepo.findById(id).orElse(null);
			if (entity != null) {
				entity.setVerified(request.getVerified());
				reconsileAndVerifySystemRepo.save(entity);
				updatedIds.add(id);
			}
		}

		if (!updatedIds.isEmpty()) {
			String response = "Verified status updated to '" + request.getVerified() + "' for IDs: "
					+ String.join(", ", updatedIds);
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin
	@GetMapping(value = RECONSILE_FILTER_VERIFY, produces = "application/json")
	public ResponseEntity<List<ReconsileVerifySystem>> getEntitiesByVerified(@RequestParam String verified) {
		List<ReconsileVerifySystem> entities = reconsileAndVerifySystemRepo.findByVerified(verified);

		if (!entities.isEmpty()) {
			return ResponseEntity.ok(entities);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
