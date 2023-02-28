package net.miraeit.user.exception;

public class NoUserFoundBySsoIdException extends RuntimeException{
	public NoUserFoundBySsoIdException(String message) {
		super(message);
	}
}
