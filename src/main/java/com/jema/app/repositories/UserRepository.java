/* 
*  Project : Jema
*  Author  : Raj Khatri
*  Date    : 12-Mar-2023
*
*/

package com.jema.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jema.app.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
