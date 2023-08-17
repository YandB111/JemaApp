/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 19-Mar-2023
*
*/

package com.jema.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jema.app.exceptions.AppRuntimeException;
import com.jema.app.exceptions.InvalidTokenException;
import com.jema.app.exceptions.UserExistanceException;
import com.jema.app.response.ErrorResponse;
import com.jema.app.response.GenericResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@ControllerAdvice
public class ExceptionHandlingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

	@Autowired
	Environment environment;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	ErrorResponse errorResponse;

	/**
	 * This exception is thrown in case of login failure
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> loginFailure(BadCredentialsException exception) {
		return new ResponseEntity<>(errorResponse.getInstance(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				exception.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	/**
	 * This exception is thrown in case of login failure
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponse> loginFailure(UsernameNotFoundException exception) {
		return new ResponseEntity<>(errorResponse.getInstance(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				exception.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> missingServletRequestParameterException(
			MissingServletRequestParameterException exception) {
		return new ResponseEntity<>(errorResponse.getInstance(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				exception.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

	}

	/**
	 * This exception is thrown when Token verified from the confirmation email is
	 * Invalid. (either empty) State invalid is defined by JWT Library
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorResponse> invalidToken(InvalidTokenException exception) {
		return new ResponseEntity<>(errorResponse.getInstance(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				exception.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ErrorResponse> notFormat(NumberFormatException exception) {
		return new ResponseEntity<>(errorResponse.getInstance(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				exception.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ExpiredJwtException.class, UnsupportedJwtException.class, MalformedJwtException.class,
			SignatureException.class, IllegalArgumentException.class, JwtException.class })
	public ResponseEntity<ErrorResponse> jwtTokenExpire(Exception exception) {
		return new ResponseEntity<>(errorResponse.getInstance(HttpStatus.UNAUTHORIZED.getReasonPhrase(),
				"Invalid token", HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(AppRuntimeException.class)
	public ResponseEntity<ErrorResponse> runtimeException(AppRuntimeException exception) {
		return new ResponseEntity<>(errorResponse.getInstance(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				exception.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserExistanceException.class)
	public ResponseEntity<GenericResponse> userExistence(UserExistanceException exception) {
		return new ResponseEntity<>(new GenericResponse().getResponse(exception.getMessage(), exception.getMessage(),
				HttpStatus.UNAUTHORIZED), HttpStatus.OK);
	}

}
