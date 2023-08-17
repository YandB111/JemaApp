/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 06-May-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	Page<Attendance> findAll(Pageable pageable);
	
//	Page<Attendance> findByNameContainingIgnoreCase(String name, Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Attendance  SET leave_type=?1  WHERE id IN ?2 ", nativeQuery = true)
	int updateLeaveType(@Param("leave_type") int status, @Param("id") List<Long> idsArrays);
	
}
