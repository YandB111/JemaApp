/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 29-Apr-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jema.app.entities.Document;
import com.jema.app.repositories.DocumentRepository;
import com.jema.app.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentRepository documentRepository;


	@Override
	public Long save(Document document) {
		boolean nameExists = documentRepository.existsByNameIgnoreCase(document.getName());

		if (nameExists) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Document name already exists");
		}


		return documentRepository.save(document).getId();
	}

	@Override
	public List<Document> findAll() {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "id").ignoreCase());
		return documentRepository.findAll(sortBy);
	}

	@Override
	public Document findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Document> document = documentRepository.findById(id);
		if (document.isPresent()) {
			return document.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		documentRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public int updateStatus(int status, List<Long> idsArrays) {
		// TODO Auto-generated method stub
		documentRepository.updateStatus(status, idsArrays);
		return 1;
	}


	@Override
	@Transactional
	public Long updateDocument(Document documentToUpdate) {
		boolean nameExistsInOtherDocuments = documentRepository
				.existsByNameIgnoreCaseAndIdNot(documentToUpdate.getName(), documentToUpdate.getId());

		if (nameExistsInOtherDocuments) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Document name already exists.");
		}

		Document existingDocument = documentRepository.findById(documentToUpdate.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

		// Update other fields as needed
		existingDocument.setName(documentToUpdate.getName());

		existingDocument.setDescription(documentToUpdate.getDescription());
		existingDocument.setType(documentToUpdate.getType());
		existingDocument.setStatus(documentToUpdate.getStatus());

		return documentRepository.save(existingDocument).getId();
	}


}
