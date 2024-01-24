package co.polarpublishing.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInDto {

	@NotNull(message = "Missing required parameter 'email'.")
	@Pattern(regexp = "^.+@.+\\..+$", message = "Invalid email address.")
	private String email;

	@NotNull(message = "Missing required parameter 'password'.")
	@Size(min = 4, message = "Password should be at least 4 characters")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

}
