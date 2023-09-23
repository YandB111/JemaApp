package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.google.gson.Gson;
import com.jema.app.entities.DocumentFinance;
import com.jema.app.entities.ElutionFinanceAddRecord;
import com.jema.app.entities.FinanceAddRecordRequest;
import com.jema.app.entities.TaxForElutonRecord;
import com.jema.app.entities.TaxForRecord;
import com.jema.app.repositories.ElutionAddRecordRepo;
import com.jema.app.repositories.FinanceRecordRepo;
import com.jema.app.service.ElutionAddRecordService;

import springfox.documentation.swagger2.mappers.ModelMapper;

@Service
public class ElutionAddRecordServceImpl implements ElutionAddRecordService {
	@Autowired
	private ElutionAddRecordRepo elutionAddRecordRepo;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ElutionFinanceAddRecord save(ElutionFinanceAddRecord record) {
		return elutionAddRecordRepo.save(record);
	}

	@Override
	public ElutionFinanceAddRecord update(Long id, ElutionFinanceAddRecord updatedRecord) {
		ElutionFinanceAddRecord existingRecord = elutionAddRecordRepo.findById(id)
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
		
		List<TaxForElutonRecord> existingTaxRecords = existingRecord.getTaxInvoices();
		List<TaxForElutonRecord> updatedTaxRecords = updatedRecord.getTaxInvoices();
		
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

		return elutionAddRecordRepo.save(existingRecord);
	}

	@Override
	@Transactional
	public void deleteRecordById(Long id) {
		elutionAddRecordRepo.deleteById(id);
	}

	@Override
	public Page<ElutionFinanceAddRecord> getAllRecords(Pageable pageable) {
		return elutionAddRecordRepo.findAll(pageable);
	}

	@Override
	public List<ElutionFinanceAddRecord> getRecordsByPaymentType(String paymentType) {
		return elutionAddRecordRepo.findByPaymentTypeContainingIgnoreCase(paymentType);
	}

	@Override
	public List<ElutionFinanceAddRecord> getRecordsByRecordType(String recordType) {
		return elutionAddRecordRepo.findByRecordTypeContainingIgnoreCase(recordType);
	}

	@Override
	public List<ElutionFinanceAddRecord> getRecordsByStatus(String status) {
		return elutionAddRecordRepo.findByStatus(status);
	}

	@Override
	public List<ElutionFinanceAddRecord> getRecordsByAddType(String addType) {
		return elutionAddRecordRepo.findByAddType(addType);
	}

	public List<ElutionFinanceAddRecord> getFilteredRecords(String typeName, String status, String recordType,
			String paymentType, String addType) {
		Specification<ElutionFinanceAddRecord> spec = Specification.where(null);

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

		return elutionAddRecordRepo.findAll(spec);
	}

	@Override
	public List<ElutionFinanceAddRecord> findAllFinanceRecords() {
		return (List<ElutionFinanceAddRecord>) elutionAddRecordRepo.findAll();
	}

	@Override
	public Double calculateTotalIncome() {
		return elutionAddRecordRepo.calculateTotalIncome();
	}

	@Override
	public Double calculateTotalExpense() {
		return elutionAddRecordRepo.calculateTotalExpense();
	}

	@Override
	public Optional<ElutionFinanceAddRecord> findById(Long id) {
		return elutionAddRecordRepo.findById(id);
	}

}
