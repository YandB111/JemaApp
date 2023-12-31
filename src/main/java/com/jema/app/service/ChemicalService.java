/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.dto.ChemicalListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.entities.Chemical;

public interface ChemicalService {

	public Long save(Chemical chemical);

	public List<ChemicalListView> findAll(PageRequestDTO pageRequestDTO);

	public Chemical findById(Long id);

	public int delete(List<Long> ids);

	public int updateStatus(Long id, Integer status);

}
