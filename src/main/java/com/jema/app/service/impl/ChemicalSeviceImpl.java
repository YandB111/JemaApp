/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.jema.app.dto.ChemicalDTO;
import com.jema.app.dto.ChemicalListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Chemical;
import com.jema.app.repositories.ChemicalRepository;
import com.jema.app.service.ChemicalService;
import com.jema.app.utils.AppUtils;

@Service
public class ChemicalSeviceImpl implements ChemicalService {

	@Autowired
	ChemicalRepository chemicalRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private Gson gson;

	// Save method with the check for existing chemical name
	@Override
	public Long save(Chemical chemical) {
		String code = chemical.getCode();
		String name = chemical.getName();

		boolean codeExists = chemicalRepository.existsByCodeIgnoreCase(code);
		boolean nameExists = chemicalRepository.existsByNameIgnoreCase(name);

		if (codeExists) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chemical with the same code already exists");
		}

		if (nameExists) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chemical with the same name already exists");
		}
		Chemical savedChemical = chemicalRepository.save(chemical);
		return savedChemical.getId();
	}

	@Override
	public List<ChemicalListView> findAll(PageRequestDTO pageRequestDTO) {
		// TODO Auto-generated method stub
		String baseBuery = "select count(*) over() as total, c.name as name, c.id as id, c.status as status, "
				+ "c.description as description, c.code as code, c.quantity as quantity, c.price as price, "
				+ "c.mssd as mssd, c.image as image, c.hs_code as hs_code, c.expiredate as expiredate "
				+ "from chemical c ";
		if (pageRequestDTO.getKeyword() != null && !pageRequestDTO.getKeyword().trim().isEmpty()) {
			baseBuery = baseBuery + " where c.deleted!=1 and c.name ilike '%" + pageRequestDTO.getKeyword().trim()
					+ "%'";
		} else {
			baseBuery = baseBuery + "  where c.deleted!=1 ";
		}

		baseBuery = baseBuery
				+ " group by c.name, c.id, c.status, c.description, c.code, c.quantity, c.price, c.mssd, c.image, c.hs_code, c.expiredate order by c.id DESC";
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

		List<ChemicalListView> dataList = AppUtils.parseTuple(tuples, ChemicalListView.class, gson);
//		attendanceView.convertIntoDTO(tuples);

		return dataList;

	}

	@Override
	public Chemical findById(Long id) {
		// TODO Auto-generated method stub
		Chemical chemical = chemicalRepository.findChemicalById(id);
		return chemical;
	}

	@Override
	public int delete(List<Long> ids) {
		// TODO Auto-generated method stub
		return chemicalRepository.delete(ids);
	}

	@Override
	public int updateStatus(Long id, Integer status) {
		// TODO Auto-generated method stub
		return chemicalRepository.updateStatus(id, status);
	}

	@Override
	public ResponseEntity<?> updateChemicalWithoutUniquenessCheck(Long id, ChemicalDTO chemicalDTO) {
		Chemical existingChemical = chemicalRepository.findById(id).orElse(null);

		if (existingChemical != null) {
			// Proceed with the update logic
			updateChemicalFields(existingChemical, chemicalDTO);

			// Save the modified chemical entity
			Chemical updatedChemical = chemicalRepository.save(existingChemical);

			// Return the updated chemical
			return ResponseEntity.ok(updatedChemical);
		} else {
			// Handle the situation where the chemical with the given id doesn't exist
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chemical not found with ID: " + id);
		}
	}


	@Override
	public void updateChemicalFields(Chemical existingChemical, ChemicalDTO chemicalDTO) {
	    int appendCounter = 1;
	    
	    // Check if the new code already exists
	    boolean newCodeExists = chemicalRepository.existsByCodeIgnoreCaseAndIdNot(chemicalDTO.getCode(), existingChemical.getId());

	    while (newCodeExists) {
	        // Append "_<appendCounter>" to the new code
	        chemicalDTO.setCode(chemicalDTO.getCode() + "_" + appendCounter);
	        appendCounter++;

	        newCodeExists = chemicalRepository.existsByCodeIgnoreCaseAndIdNot(chemicalDTO.getCode(), existingChemical.getId());
	    }

	    // Check if the new name already exists
	    boolean newNameExists = chemicalRepository.existsByNameIgnoreCaseAndIdNot(chemicalDTO.getName(), existingChemical.getId());

	    while (newNameExists) {
	        // Append "_<appendCounter>" to the new name
	        chemicalDTO.setName(chemicalDTO.getName() + "_" + appendCounter);
	        appendCounter++;

	        newNameExists = chemicalRepository.existsByNameIgnoreCaseAndIdNot(chemicalDTO.getName(), existingChemical.getId());
	    }

	    // Update other fields from chemicalDTO as needed
	    existingChemical.setCode(chemicalDTO.getCode());
	    existingChemical.setName(chemicalDTO.getName());
	    existingChemical.setDescription(chemicalDTO.getDescription());
	    existingChemical.setPrice(chemicalDTO.getPrice());
	    // ... update other fields as needed
	}

}