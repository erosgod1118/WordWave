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
public class UserWpDataDto {

    @JsonProperty("user_current_plan")
    private String userCurrentPlan;

    @JsonProperty("user_subscription_status")
    private String userSubscriptionStatus;

    @JsonProperty("user_last_billing_date")
    private String userLastBillingDate;

    @JsonProperty("user_next_billing_date")
    private String userNextBillingDate;
}
