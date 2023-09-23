/* 
*  Project : Jema
*  Author  : Raj Khatri
*  Date    : 12-Mar-2023
*
*/

package com.jema.app.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new RuntimeException("Email address is incorrect.");
		}

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


//	 @Override
//	 public UserDTO login(String username, String password) {
//	     UserDTO userModel = null;
//	     User optionalUser = userRepository.findByEmail(username).orElse(null);
//
//	     if (optionalUser == null) {
//	         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with the given email.");
//	     }
//
//	     userModel = convertInModel(optionalUser);
//	     if (userModel != null) {
//	         if (bCryptPasswordEncoder.matches(password, userModel.getPassword())) {
//	             return userModel;
//	         } else {
//	             throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email address or password does not match.");
//	         }
//	     } else {
//	         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with the given email.");
//	     }
//	 }


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


	@Override
	public boolean changePassword(String oldPassword, String newPassword) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String userEmail = authentication.getName();

	    User user = userRepository.findByEmail(userEmail)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found."));

	    if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
	        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
	        userRepository.save(user);
	        return true;
	    } else {
	        return false;
	    }
	}

}
