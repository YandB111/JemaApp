/* 
*  Project : Jema
*  Author  : Raj Khatri
*  Date    : 10-Mar-2023
*
*/

package com.jema.app.controllers;

import java.util.Date;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jema.app.dto.ChangePasswordRequest;
import com.jema.app.dto.LoginDTO;
import com.jema.app.dto.UserDTO;
import com.jema.app.entities.User;
import com.jema.app.exceptions.DepartmentErrorResponse;
import com.jema.app.jwt.JwtTokenUtil;
import com.jema.app.jwt.JwtUserDetailsService;
import com.jema.app.response.GenericResponse;
import com.jema.app.response.JwtResponse;
import com.jema.app.response.PasswordChangeResponse;
//import com.jema.app.service.impl.TokenBuilderService;
import com.jema.app.service.impl.UserServiceImpl;

import io.swagger.annotations.Api;

@Api(value = "Login Controller")
@RestController
public class LoginController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(LoginController.class);

//	@Autowired
//	private TokenBuilderService tokenBuilderService;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@CrossOrigin
	@RequestMapping(value = LOGIN, method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody LoginDTO loginDTO) {
		logger.info("Request: In Login Controller login with username: {}", loginDTO.getUsername());
		GenericResponse genericResponse = new GenericResponse();

		try {
			// Call the authenticate method, it will throw exceptions for invalid
			// credentials
			authenticate(loginDTO.getUsername().toLowerCase(), loginDTO.getPassword());

			// If authentication is successful, generate JWT token and return it in the
			// response
			final UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername().toLowerCase());
			final String token = jwtTokenUtil.generateToken(userDetails);

			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(new JwtResponse(token), "User successfully login", HttpStatus.OK),
					HttpStatus.OK);
		} catch (BadCredentialsException e) {
			// Handle invalid credentials exception and send a custom error response
			DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
			customResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
			customResponse.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
			customResponse.setMessage("Invalid email or password. Please check your credentials.");
			customResponse.setTimestamp(new Date());
			return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// Handle any other unexpected exceptions and send a custom error response
			DepartmentErrorResponse customResponse = new DepartmentErrorResponse();
			customResponse.setStatus(HttpStatus.BAD_REQUEST.value());
			customResponse.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
			customResponse.setMessage("Invalid email or password. Please check your credentials.");
			customResponse.setTimestamp(new Date());
			return new ResponseEntity<>(customResponse, HttpStatus.CONFLICT);
		}
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

//	@RequestMapping(value = "api/v1/hello", method = RequestMethod.GET)
//	public String firstPage() {
//		return "Hello World";
//	}

	/*
	 * ======================== Log In ==================================
	 */
//	@CrossOrigin
//	@PostMapping(value = LOGIN_URL, produces = "application/json")
//	public ResponseEntity<?> logIn(@RequestParam(name = "username", required = true) String username,
//			@RequestParam(name = "password", required = true) String password) {
//		logger.info("Request:In Login Controller login with  username :{} ", username);
//		GenericResponse genericResponse = new GenericResponse();
//
//		if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
//			logger.info("Response:User successfully login");
//			return new ResponseEntity<GenericResponse>(genericResponse.getResponse(loginUser(username, password),
//					"User successfully login", HttpStatus.OK), HttpStatus.OK);
//
//		}
//
//		logger.info("Response:invalid type");
//		return new ResponseEntity<GenericResponse>(genericResponse.getResponse(null, "Invalid type", HttpStatus.OK),
//				HttpStatus.OK);
//	}

	private UserDTO loginUser(String username, String password) {

		UserDTO user = userServiceImpl.login(username, password);
//		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		List<String> role = new ArrayList<>();
////		role.add(Constants.ROLE_STAFF);
//		String token = tokenBuilderService.generateToken(user.getId() + "", username, 1, role);
//		user.setToken(token);

		return user;
	}

	/*
	 * ======================== Sign UP ==================================
	 */
	@CrossOrigin
	@PostMapping(value = SIGNUP, produces = "application/json")
	public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
		logger.info("Request:In Login Controller for Signup  User :{} ", userDTO);
		GenericResponse genericResponse = new GenericResponse();

		if (StringUtils.hasText(userDTO.getEmail())) {
			logger.info("Response:User successfully Signup");
			User user = new User();
			userDTO.setEmail(userDTO.getEmail().toLowerCase());
			BeanUtils.copyProperties(userDTO, user);
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			Long id = userServiceImpl.save(user);
			userDTO.setId(id);
			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(userDTO, "User successfully Signup", HttpStatus.OK), HttpStatus.OK);
		}

		logger.info("Response:invalid type");
		return new ResponseEntity<GenericResponse>(genericResponse.getResponse(null, "Invalid type", HttpStatus.OK),
				HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = PASSWORD_CHANGE, produces = "application/json")
	public ResponseEntity<PasswordChangeResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
	    try {
	        boolean passwordChanged = userServiceImpl.changePassword(changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
	        if (passwordChanged) {
	            return ResponseEntity.ok(new PasswordChangeResponse(true, "Password changed successfully."));
	        } else {
	            return ResponseEntity.badRequest().body(new PasswordChangeResponse(false, "Invalid old password."));
	        }
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.badRequest().body(new PasswordChangeResponse(false, "Invalid old password."));
	    }
	}



}
