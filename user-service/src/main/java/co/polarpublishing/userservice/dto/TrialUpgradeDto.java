package co.polarpublishing.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrialUpgradeDto {

	@NotNull(message = "Missing required parameter 'plan'.")
	String plan;

	@NotNull(message = "Missing required parameter 'token'.")
	String token;

	@NotNull(message = "Missing required parameter 'email'.")
	String email;

	String period;
	String coupon;

}
