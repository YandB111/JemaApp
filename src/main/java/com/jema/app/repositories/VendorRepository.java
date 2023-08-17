/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 27-May-2023
*
*/

package com.jema.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.Vendor;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE Vendor SET status = :status  WHERE id IN :id ")
	int updateStatus(@Param("id") Long id, @Param("status") int status);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Vendor  SET deleted = '1'  WHERE id IN ?1 ", nativeQuery = true)
	int delete(@Param("id") List<Long> idsArrays);
	
	@Query(value = "SELECT * FROM Vendor WHERE id=?1 and deleted != '1'", nativeQuery = true)
	Vendor findVendorById(@Param("id") Long id);
	
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Vendor e WHERE e.email = :email")
	boolean existsEmail(String email);
}
