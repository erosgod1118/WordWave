package co.polarpublishing.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordDto {

	@NotNull(message = "Missing required parameter 'resetToken'.")
	private String resetToken;

	@NotNull(message = "Missing required parameter 'newPassword'.")
	@Size(min = 4, message = "Password should be at least 4 characters")
	private String newPassword;

}
