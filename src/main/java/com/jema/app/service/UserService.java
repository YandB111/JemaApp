/* 
*  Project : Jema
*  Author  : Raj Khatri
*  Date    : 12-Mar-2023
*
*/

package com.jema.app.service;

import com.jema.app.dto.UserDTO;
import com.jema.app.entities.User;

public interface UserService {

	public Long save(User user);

	public User findById(int id);
	
	public User findByEmail(String email);
	
	public UserDTO login(String username, String password);


	boolean changePassword(String oldPassword, String newPassword);


}
