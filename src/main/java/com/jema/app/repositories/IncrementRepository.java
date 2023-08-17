/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.Increment;

@Repository
public interface IncrementRepository extends JpaRepository<Increment, Long> {

	List<Increment> findByEmployeeIdOrderByIdDesc(Long employeeId);
}
