package net.miraeit.cmm.exception.jwt;

public class InvalidJwtException extends RuntimeException {
	public InvalidJwtException(String message) {
		super(message);
	}
}
