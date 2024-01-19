package co.polarpublishing.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBillingHistoryDto {

    @JsonProperty("user_id")
    private Long userId;

    private Integer amount;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("invoice_link")
    private String invoiceLink;
}
