package co.polarpublishing.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgotPasswordDto {

    @NotNull(message = "Missing required parameter 'email'.")
    @Pattern(regexp = "^.+@.+\\..+$", message = "Invalid email address.")
    private String email;

}
