/* 
<<<<<<< HEAD
x*  Project : JemaApp
=======
*  Project : JemaApp
>>>>>>> a74bf511ab52fec3349dbcb67c72bd01a6924998
*  Author  : Raj Khatri
*  Date    : 13-May-2023
*
*/

package com.jema.app.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jema.app.entities.AccountDetails;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE AccountDetails SET balance = :balance  WHERE id IN :id ")
	int updateBalance(@Param("id") Long id, @Param("balance") Long balance);


	@Transactional
	@Modifying
	@Query("UPDATE AccountDetails SET totalAmountPaid = :totalAmountPaid WHERE id = :id")
	int updateTotalAmountPaid(@Param("id") Long id, @Param("totalAmountPaid") Long totalAmountPaid);

	@Query("SELECT a.balance FROM AccountDetails a")
	List<Long> getAllBalances();

	@Query("SELECT COALESCE(SUM(a.balance), 0) FROM AccountDetails a")
	Long calculateTotalBalance();


}
