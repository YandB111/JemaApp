/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Apr-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

	Type findByNameIgnoreCase(String typeName);

	Type findByName(String newTypeName);

}
