package net.miraeit.cmm.exception.jwt;

public class JwtNotFoundException extends RuntimeException{
	public JwtNotFoundException(String message) {
		super(message);
	}
}
