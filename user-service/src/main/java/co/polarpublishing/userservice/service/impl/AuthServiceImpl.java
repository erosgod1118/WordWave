package co.polarpublishing.userservice.service.impl;

import co.polarpublishing.userservice.assembler.UserAssembler;
import co.polarpublishing.common.dto.UserDto;
import co.polarpublishing.userservice.dto.SignUpDto;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.exception.UserAlreadyExistsException;
import co.polarpublishing.userservice.exception.UserNotFoundException;
import co.polarpublishing.userservice.repository.read.UserReadRepository;
import co.polarpublishing.userservice.repository.write.UserWriteRepository;
import co.polarpublishing.userservice.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserWriteRepository userWriteRepository;

	@Autowired
	private UserReadRepository userReadRepository;

	@Autowired
	private UserAssembler userAssembler;

	@Override
	public UserDto signUp(SignUpDto userDto) throws UserAlreadyExistsException {
		try {
			userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));

			User user = userAssembler.toEntity(userDto);

			user.setUserCurrentPlan(userDto.getUserCurrentPlan());
			user.setUserLastBillingDate(userDto.getUserLastBillingDate());
			user.setUserNextBillingDate(userDto.getUserNextBillingDate());
			user.setUserSubscriptionStatus(userDto.getUserSubscriptionStatus());
			user.setConfirmationToken(userDto.getConfirmationToken());

			User savedUser = userWriteRepository.save(user);
			return userAssembler.toDto(savedUser);
		} catch (Exception exception) {
			throw new UserAlreadyExistsException();
		}
	}

	@Override
	public UserDto signIn(String email, String password) throws UserNotFoundException {
		User user = userReadRepository.findByEmailAndBannedIsFalseAllIgnoreCase(email);
		if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
			throw new UserNotFoundException("User with such email and password does not exist.");
		}

		return userAssembler.toDto(user);
	}

}
