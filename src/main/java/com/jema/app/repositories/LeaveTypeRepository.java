/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 01-May-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.LeaveType;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {

	LeaveType findByName(String name);


	@Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM LeaveType l WHERE LOWER(l.name) = LOWER(:leaveTypeName)")
    boolean existsByNameIgnoreCase(@Param("leaveTypeName") String leaveTypeName);
	
	


}
