/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 01-May-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.LeaveType;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {

	LeaveType findByName(String name);

	boolean existsByName(String name);
}
