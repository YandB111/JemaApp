/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-Apr-2023
*
*/

package com.jema.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jema.app.entities.BoardUniversity;
import com.jema.app.repositories.BoardUniversityRepository;
import com.jema.app.service.BoardUniversityService;

@Service
public class BoardUniversityServiceImpl implements BoardUniversityService {

	@Autowired
	BoardUniversityRepository boardUniversityRepository;

	@Override
	public Long save(BoardUniversity boardUniversity) {
		// TODO Auto-generated method stub
		return boardUniversityRepository.save(boardUniversity).getId();
	}

	@Override
	public List<BoardUniversity> findAll() {
		// TODO Auto-generated method stub
		return boardUniversityRepository.findAll();
	}

	@Override
	public int delete(List<Long> idsArrays) {
		// TODO Auto-generated method stub
		boardUniversityRepository.deleteAllById(idsArrays);
		return 1;
	}

	@Override
	public BoardUniversity findById(Long id) {
		// TODO Auto-generated method stub
		Optional<BoardUniversity> boardUniversity = boardUniversityRepository.findById(id);
		if (boardUniversity.isPresent()) {
			return boardUniversity.get();
		}
		return null;
	}

}
