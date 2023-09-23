/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 29-Apr-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.Document;


public interface DocumentService {

	public Long save(Document document);

	public List<Document> findAll();

	public Document findById(Long id);
	
	public int delete(List<Long> idsArrays);
	
	public int updateStatus(int status, List<Long> idsArrays);


	public Long updateDocument(Document documentToUpdate);

}
