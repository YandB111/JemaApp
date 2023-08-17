package com.jema.app.jwt;
public final class SecurityConstants {

	public static final String AUTH_LOGIN_URL = "/api/authenticate";

	// Signing key for HS512 algorithm
	// You can use the page http://www.allkeysgenerator.com/ to generate all kinds
	// of keys
	public static final String JWT_SECRET = "jema@stackgeeks";

	// JWT token defaults
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";

	// Expiration time of JWT Token
//	public static final long EXPIRATION_TIME = (long) 168 * 60 * 60 * 1000;
	public static final long JWT_TOKEN_VALIDITY = (long) 5 * 60 * 60 * 1000;

	public static final long RESET_EMAIL_EXPIRATION = 10 * 60 * 1000;

}