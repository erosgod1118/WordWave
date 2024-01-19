package co.polarpublishing.userservice.assembler;

import co.polarpublishing.common.dto.UserDto;
import co.polarpublishing.common.constant.RoleName;
import co.polarpublishing.common.dto.UserRoleDto;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.entity.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class UserAssembler {

	private final UserRoleAssembler userRoleAssembler;
	private final AmazonKeysAssembler amazonKeysAssembler;

	@Autowired
	public UserAssembler(UserRoleAssembler userRoleAssembler, AmazonKeysAssembler amazonKeysAssembler) {
		this.userRoleAssembler = userRoleAssembler;
		this.amazonKeysAssembler = amazonKeysAssembler;
	}

	public UserDto toDto(User user) {
		return UserDto.builder()
						.userId(user.getId())
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.email(user.getEmail())
						.password(user.getPassword())
						.creationTime(user.getCreationTimestamp())
						.userCurrentPlan(user.getUserCurrentPlan())
						.roles(user.getRoles() == null ? new ArrayList<>() : user.getRoles().stream().map(userRoleAssembler::toDto).collect(Collectors.toList()))
						.avatarLink(user.getAvatarLink())
						.build();
	}

	public User toEntity(UserDto user) {
		user.setRoles(Collections.singletonList(userRoleAssembler.toDto(UserRole.builder().roleId(2L).role(RoleName.ROLE_USER).build())));

		return User.builder()
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.email(user.getEmail())
						.password(user.getPassword())
						//.roles(user.getRoles().stream().map(userRoleAssembler::toEntity).collect(Collectors.toList()))
						.banned(false)
						.resetToken(user.getResetToken())
						.avatarLink(user.getAvatarLink())
						.build();
	}

}
