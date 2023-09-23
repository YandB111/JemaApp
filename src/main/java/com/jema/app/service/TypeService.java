/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Apr-2023
*
*/

package com.jema.app.service;

import java.util.List;

import com.jema.app.entities.Type;

public interface TypeService {

	public Long save(Type type);

	public List<Type> findAll();

	public Type findById(Long id);

	public int delete(List<Long> idsArrays);

	public Type findByName(String newTypeName);

	public Type findByNameIgnoreCase(String typeName);


}
