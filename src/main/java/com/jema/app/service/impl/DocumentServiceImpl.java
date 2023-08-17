/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 29-Apr-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jema.app.entities.Document;
import com.jema.app.repositories.DocumentRepository;
import com.jema.app.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	DocumentRepository documentRepository;
	
	@Override
	public Long save(Document document) {
		// TODO Auto-generated method stub
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
}
