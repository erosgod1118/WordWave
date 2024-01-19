package co.polarpublishing.common.dto;

import co.polarpublishing.common.dto.keys.AmazonKeysDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

	@ApiModelProperty(hidden = true)
	private Long userId;

	private String firstName;
	private String lastName;

	@NotNull(message = "Missing required parameter 'email'.")
	@Pattern(regexp = "^.+@.+\\..+$", message = "Invalid email address.")
	private String email;

	@ApiModelProperty(hidden = true)
	private List<UserRoleDto> roles;

	@ApiModelProperty(hidden = true)
	private String accessToken;

	@ApiModelProperty(hidden = true)
	private String refreshToken;

	@ApiModelProperty(hidden = true)
	private String confirmationToken;

	private String userCurrentPlan;

	private Long creationTime;

	private List<?> limits;

	@NotNull(message = "Missing required parameter 'password'.")
	@Size(min = 4, message = "Password should be at least 4 characters")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private Boolean banned;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String resetToken;

	@ApiModelProperty(hidden = true)
	private String avatarLink;

	@ApiModelProperty(hidden = true)
	private List<AmazonKeysDto> amazonKeys;

}
