/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 08-May-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.LeaveManagement;

@Repository
public interface LeaveManagementRepository extends JpaRepository<LeaveManagement, Long> {

	List<LeaveManagement> findByEmployeeId(Long id);
	
	
}
