/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 21-Apr-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.BoardUniversity;

public interface BoardUniversityService {

	public Long save(BoardUniversity boardUniversity);

	public List<BoardUniversity> findAll();

	public int delete(List<Long> idsArrays);
	
	public BoardUniversity findById(Long id);
}
