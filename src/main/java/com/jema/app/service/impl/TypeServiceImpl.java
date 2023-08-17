/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Apr-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jema.app.entities.Type;
import com.jema.app.repositories.TypeRepository;
import com.jema.app.service.TypeService;

@Service
public class TypeServiceImpl implements TypeService {

	@Autowired
	TypeRepository typeRepository;

	@Override
	public Long save(Type type) {
		// TODO Auto-generated method stub
		return typeRepository.save(type).getId();
	}

	@Override
	public List<Type> findAll() {
		// TODO Auto-generated method stub
		Sort sortBy = Sort.by(new Sort.Order(Sort.Direction.DESC, "id").ignoreCase());
		return typeRepository.findAll(sortBy);
	}

	@Override
	public Type findById(Long id) {
		// TODO Auto-generated method stub
		Optional<Type> type = typeRepository.findById(id);
		if (type.isPresent()) {
			return type.get();
		}
		return null;
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		typeRepository.deleteAllById(idsArrays);
		return 1;
	}



}
