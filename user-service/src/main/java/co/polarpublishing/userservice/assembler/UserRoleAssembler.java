package co.polarpublishing.userservice.assembler;

import co.polarpublishing.common.dto.UserRoleDto;
import co.polarpublishing.userservice.entity.UserRole;

import org.springframework.stereotype.Component;

@Component
public class UserRoleAssembler {

	public UserRoleDto toDto(UserRole userRole) {
		return UserRoleDto.builder()
					.role(userRole.getRole())
					.roleId(userRole.getRoleId())
					.build();
	}

	public UserRole toEntity(UserRoleDto userRoleDto) {
		return UserRole.builder()
					.role(userRoleDto.getRole())
					.roleId(userRoleDto.getRoleId())
					.build();
	}

}
