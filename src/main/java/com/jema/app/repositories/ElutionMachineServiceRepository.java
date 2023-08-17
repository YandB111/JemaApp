/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 22-Jul-2023
*
*/

package com.jema.app.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.ElutionMachineServiceEntity;

@Repository
public interface ElutionMachineServiceRepository extends CrudRepository<ElutionMachineServiceEntity, Long> {

}
