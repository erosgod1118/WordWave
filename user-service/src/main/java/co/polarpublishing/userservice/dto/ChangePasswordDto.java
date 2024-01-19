package co.polarpublishing.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordDto {

    @NotNull(message = "Missing required parameter 'currentPassword'.")
    @Size(min = 4, message = "Current password should be at least 4 characters")
    private String currentPassword;

    @NotNull(message = "Missing required parameter 'newPassword'.")
    @Size(min = 4, message = "New password should be at least 4 characters")
    private String newPassword;

}
