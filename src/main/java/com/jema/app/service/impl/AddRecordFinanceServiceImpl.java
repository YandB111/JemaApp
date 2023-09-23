package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.persistence.Query;
import javax.persistence.Tuple;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.jema.app.dto.PageRequestDTO;
import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.jema.app.dto.FinanaceAddListView;
import com.jema.app.entities.DocumentFinance;
import com.jema.app.entities.Employee;
import com.jema.app.entities.FinanceAddRecordRequest;
import com.jema.app.entities.TaxForRecord;
import com.jema.app.repositories.FinanceRecordRepo;
import com.jema.app.service.AddRecordFinanceService;
import com.jema.app.utils.AppUtils;

import springfox.documentation.swagger2.mappers.ModelMapper;

@Service
public class AddRecordFinanceServiceImpl implements AddRecordFinanceService {

	@Autowired
	private FinanceRecordRepo financeRecordRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public FinanceAddRecordRequest save(FinanceAddRecordRequest record) {
		return financeRecordRepo.save(record);
	}

	@Override
	public FinanceAddRecordRequest update(Long id, FinanceAddRecordRequest updatedRecord) {
	    FinanceAddRecordRequest existingRecord = financeRecordRepo.findById(id)
	            .orElseThrow(() -> new NotFoundException("Record not found with id: " + id));

	    existingRecord.setPaymentType(updatedRecord.getPaymentType());
	    existingRecord.setRecordType(updatedRecord.getRecordType());
	    existingRecord.setAddType(updatedRecord.getAddType());
	    existingRecord.setEmployeeIds(updatedRecord.getEmployeeIds());
	    existingRecord.setEmployeeName(updatedRecord.getEmployeeName());
	    existingRecord.setDescription(updatedRecord.getDescription());
	    existingRecord.setDueDate(updatedRecord.getDueDate());
	    existingRecord.setAmount(updatedRecord.getAmount());
	    existingRecord.setTotalTax(updatedRecord.getTotalTax());
	    existingRecord.setTotaPrice(updatedRecord.getTotaPrice());
	    existingRecord.setStatus(updatedRecord.getStatus());
	    existingRecord.setSelectTaxType(updatedRecord.getSelectTaxType());
	    // Clear and update the existing TaxForRecord objects
	    List<TaxForRecord> existingTaxRecords = existingRecord.getTaxInvoices();
	    List<TaxForRecord> updatedTaxRecords = updatedRecord.getTaxInvoices();

	    existingTaxRecords.clear();
	    if (updatedTaxRecords != null) {
	        existingTaxRecords.addAll(updatedTaxRecords);
	    }

	    List<DocumentFinance> existingInvoiceDocs = existingRecord.getInvoiceDocs();
	    List<DocumentFinance> updatedInvoiceDocs = updatedRecord.getInvoiceDocs();

	    existingInvoiceDocs.clear();
	    if (updatedInvoiceDocs != null) {
	        existingInvoiceDocs.addAll(updatedInvoiceDocs);
	    }

	    List<DocumentFinance> existingReceiptDocs = existingRecord.getReceiptDocs();
	    List<DocumentFinance> updatedReceiptDocs = updatedRecord.getReceiptDocs();

	    existingReceiptDocs.clear();
	    if (updatedReceiptDocs != null) {
	        existingReceiptDocs.addAll(updatedReceiptDocs);
	    }

	    return financeRecordRepo.save(existingRecord);
	}


	@Override
	@Transactional
	public void deleteRecordById(Long id) {
		financeRecordRepo.deleteById(id);
	}

	@Override
	public Page<FinanceAddRecordRequest> getAllRecords(Pageable pageable) {
		return financeRecordRepo.findAll(pageable);
	}

	@Override
	public List<FinanceAddRecordRequest> getRecordsByPaymentType(String paymentType) {
		return financeRecordRepo.findByPaymentTypeContainingIgnoreCase(paymentType);
	}

	@Override
	public List<FinanceAddRecordRequest> getRecordsByRecordType(String recordType) {
		return financeRecordRepo.findByRecordTypeContainingIgnoreCase(recordType);
	}

	@Override
	public List<FinanceAddRecordRequest> getRecordsByStatus(String status) {
		return financeRecordRepo.findByStatus(status);
	}

	@Override
	public List<FinanceAddRecordRequest> getRecordsByAddType(String addType) {
		return financeRecordRepo.findByAddType(addType);
	}

	public List<FinanceAddRecordRequest> getFilteredRecords(String typeName, String status, String recordType,
			String paymentType, String addType) {
		Specification<FinanceAddRecordRequest> spec = Specification.where(null);

		if (typeName != null) {
			spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("typeName"), typeName));
		}

		if (status != null) {
			spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status));
		}

		if (recordType != null) {
			spec = spec
					.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("recordType"), recordType));
		}

		if (paymentType != null) {
			spec = spec
					.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("paymentType"), paymentType));
		}

		if (addType != null) {
			spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("addType"), addType));
		}

		return financeRecordRepo.findAll(spec);
	}

	@Override
	public List<FinanceAddRecordRequest> findAllFinanceRecords() {
		return (List<FinanceAddRecordRequest>) financeRecordRepo.findAll();
	}

	@Override
	public Double calculateTotalIncome() {
		return financeRecordRepo.calculateTotalIncome();
	}

	@Override
	public Double calculateTotalExpense() {
		return financeRecordRepo.calculateTotalExpense();
	}

	@Override
	public Optional<FinanceAddRecordRequest> findById(Long id) {
		return financeRecordRepo.findById(id);
	}

}
