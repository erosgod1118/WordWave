package co.polarpublishing.userservice.service;

import co.polarpublishing.common.dto.UserDto;
import co.polarpublishing.userservice.dto.SignUpDto;
import co.polarpublishing.userservice.exception.UserAlreadyExistsException;
import co.polarpublishing.userservice.exception.UserNotFoundException;

/**
 * The {@code UserService} interface provides methods in order to sign-in, sign-up and find user by access token.
 */
public interface AuthService {

	/**
	 * Adds a new user into the system.
	 *
	 * @param userDto {@link UserDto} object, that contains email, password and etc.
	 * @return the {@link UserDto} object.
	 * @throws UserAlreadyExistsException if user is already exists with such {@code email}.
	 */
	UserDto signUp(SignUpDto userDto) throws UserAlreadyExistsException;

	/**
	 * Returns the {@link UserDto} object by {@code email} and {@code password}.
	 *
	 * @param email    {@code String} value specifying the email address of the user.
	 * @param password {@code String} value specifying the password of the user.
	 * @return the {@link UserDto} object by {@code email} and {@code password}.
	 * @throws UserNotFoundException if user does not exists by specifying {@code email} and {@code password}.
	 */
	UserDto signIn(String email, String password) throws UserNotFoundException;
	
}
