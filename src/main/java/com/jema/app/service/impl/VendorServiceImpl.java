/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 27-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.VendorListView;
import com.jema.app.dto.VendorListViewByChemicalID;
import com.jema.app.entities.Vendor;
import com.jema.app.repositories.VendorRepository;
import com.jema.app.service.VendorService;
import com.jema.app.utils.AppUtils;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	VendorRepository vendorRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	@Override
	public Long save(Vendor vendor) {
		String email = vendor.getEmail();
		if (vendorRepository.existsEmail(email)) {
			throw new IllegalArgumentException("Vendor with the same email already exists.");
		}
		return vendorRepository.save(vendor).getId();
	}

	@Override
	public List<VendorListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, v.name as name, v.id as id, v.status as status, "
				+ "v.description as description, v.email as email, v.contact as contact, v.address as address "
				+ "from vendor v ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where v.deleted!=1 and v.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%'";
		} else {
			baseBuery = baseBuery + "  where v.deleted!=1 ";
		}

		baseBuery = baseBuery
				+ " group by v.name, v.id, v.status, v.description, v.email, v.contact, v.address order by v.id DESC";
		// create a query to retrieve MyEntity objects
		Query query = null;
		try {
			query = entityManager.createNativeQuery(baseBuery, Tuple.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set the maximum number of results to retrieve (i.e., the page size)
		query.setMaxResults(pageRequestDTO.getPageSize());

		// set the index of the first result to retrieve (i.e., the offset)
		query.setFirstResult(pageRequestDTO.getPageNumber() * pageRequestDTO.getPageSize());

		// execute the query and obtain the list of entities for the requested page
		List<Tuple> tuples = query.getResultList();

		List<VendorListView> dataList = AppUtils.parseTuple(tuples, VendorListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public Vendor findById(Long id) {
		// TODO Auto-generated method stub
		Vendor vendor = vendorRepository.findVendorById(id);
		return vendor;
	}

	@Override
	public int delete(List<Long> ids) {
		// TODO Auto-generated method stub
		return vendorRepository.delete(ids);
	}

	@Override
	public int updateStatus(Long id, Integer status) {
		// TODO Auto-generated method stub
		return vendorRepository.updateStatus(id, status);
	}

	@Override
	public List<VendorListViewByChemicalID> findAllVendorByChemicalId(PageRequestDTO pageRequestDTO, Long id) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, v.name as name, v.id as id, v.status as status, v.contact as contact, v.address as address "
				+ "from vendor_chemical vc " + "left join vendor v on vc.vendor = v.id ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where v.deleted!=1 and v.status!=1 and v.name ilike '%"
					+ pageRequestDTO.getKeyword().trim() + "%' and vc.chemical_id=" + id;
		} else {
			baseBuery = baseBuery + " where v.deleted!=1 and v.status!=1 and vc.chemical_id=" + id;
		}

		baseBuery = baseBuery + " group by v.name, v.id,v.status, v.contact, v.address order by v.id DESC";
		// create a query to retrieve MyEntity objects
		Query query = null;
		try {
			query = entityManager.createNativeQuery(baseBuery, Tuple.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set the maximum number of results to retrieve (i.e., the page size)
		query.setMaxResults(pageRequestDTO.getPageSize());

		// set the index of the first result to retrieve (i.e., the offset)
		query.setFirstResult(pageRequestDTO.getPageNumber() * pageRequestDTO.getPageSize());

		// execute the query and obtain the list of entities for the requested page
		List<Tuple> tuples = query.getResultList();

		List<VendorListViewByChemicalID> dataList = AppUtils.parseTuple(tuples, VendorListViewByChemicalID.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public Long update(Vendor vendor) {
		if (existsWithDuplicateEmail(vendor)) {
			String newEmail = generateUniqueEmail(vendor.getEmail());
			vendor.setEmail(newEmail);
		}

		Vendor updatedVendor = vendorRepository.save(vendor);
		return updatedVendor.getId();
	}

	private boolean existsWithDuplicateEmail(Vendor vendor) {
		return vendorRepository.existsByEmail(vendor.getEmail())
				&& !vendorRepository.findById(vendor.getId()).get().getEmail().equals(vendor.getEmail());
	}

	private String generateUniqueEmail(String email) {
		int appendCounter = 1;
		String originalEmail = email;

		while (vendorRepository.existsByEmail(email)) {
			email = originalEmail + "_" + appendCounter;
			appendCounter++;
		}

		return email;
	}

}
