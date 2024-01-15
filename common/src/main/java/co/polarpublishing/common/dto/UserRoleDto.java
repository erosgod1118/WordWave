package co.polarpublishing.common.dto;

import co.polarpublishing.common.constant.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleDto {

	private Long roleId;
	private RoleName role;

}
