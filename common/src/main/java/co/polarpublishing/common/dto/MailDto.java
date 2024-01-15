package co.polarpublishing.common.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailDto {

	@NotNull(message = "Missing required param 'message'.")
	private String message;

	@NotNull(message = "Missing required param 'subject'.")
	private String subject;

	@NotNull(message = "Missing required param 'to'.")
	@Pattern(regexp = "^.+@.+\\..+$", message = "Invalid email address.")
	private String to;

}
