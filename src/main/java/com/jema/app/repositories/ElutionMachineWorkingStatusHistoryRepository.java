/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 23-Jul-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.ElutionMachineWorkingStatusHistory;

@Repository
public interface ElutionMachineWorkingStatusHistoryRepository extends CrudRepository<ElutionMachineWorkingStatusHistory, Long> {

}

