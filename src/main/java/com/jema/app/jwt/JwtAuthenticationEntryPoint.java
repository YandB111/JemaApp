package com.jema.app.jwt;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jema.app.response.ErrorResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

	@Autowired
	ErrorResponse errorResponse;

	protected Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Autowired
	Gson gson;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

		logger.info("RestAuthenticationEntryPoint: " + request.getRequestURI() + " Header: "
				+ request.getHeader(SecurityConstants.TOKEN_HEADER));
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getOutputStream()
				.print(gson.toJson(errorResponse.getInstance(authException.getMessage(), HttpStatus.UNAUTHORIZED)));

	}

}
