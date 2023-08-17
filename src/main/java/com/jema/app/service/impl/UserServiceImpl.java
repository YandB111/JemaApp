/* 
*  Project : Jema
*  Author  : Raj Khatri
*  Date    : 12-Mar-2023
*
*/

package com.jema.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jema.app.dto.UserDTO;
import com.jema.app.entities.User;
import com.jema.app.repositories.UserRepository;
import com.jema.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Override
	public Long save(User user) {
		// TODO Auto-generated method stub
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user).getId();
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public UserDTO login(String username, String password) {
		// TODO Auto-generated method stub
		UserDTO userModel = null;
		User optionalUser = userRepository.findByEmail(username).orElse(null);

//		if (optionalUser == null) {
//			throw new UsernameNotFoundException("No such user exists. ");
//		}
//		userModel = convertInModel(optionalUser);
//		if (userModel != null) {
//			if (bCryptPasswordEncoder.matches(password, userModel.getPassword())) {
//				return userModel;
//			} else {
//				throw new BadCredentialsException("Email address or Password does not match.");
//			}
//		} else
//			throw new UsernameNotFoundException("No such user exists.");

		return userModel;
	}

	private UserDTO convertInModel(User optionalUser) {

		UserDTO userModel = new UserDTO();
		userModel.setId(optionalUser.getId());
		userModel.setEmail(optionalUser.getEmail());
		userModel.setPassword(optionalUser.getPassword());
		userModel.setFirstName(optionalUser.getFirstName());
		userModel.setLastName(optionalUser.getLastName());
		userModel.setBranch(optionalUser.getBranch());
		userModel.setDepartment(optionalUser.getDepartment());
		userModel.setUserType(optionalUser.getUserType());

		return userModel;
	}

}
