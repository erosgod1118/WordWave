package co.polarpublishing.userservice.dto;

import co.polarpublishing.common.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpDto extends UserDto {

    @JsonProperty("user_current_plan")
    private String userCurrentPlan;

    @JsonProperty("user_subscription_status")
    private String userSubscriptionStatus;

    @JsonProperty("user_last_billing_date")
    private String userLastBillingDate;

    @JsonProperty("user_next_billing_date")
    private String userNextBillingDate;

    public String getUserCurrentPlan() {
        return userCurrentPlan;
    }

    public void setUserCurrentPlan(String userCurrentPlan) {
        this.userCurrentPlan = userCurrentPlan;
    }

    public String getUserSubscriptionStatus() {
        return userSubscriptionStatus;
    }

    public void setUserSubscriptionStatus(String userSubscriptionStatus) {
        this.userSubscriptionStatus = userSubscriptionStatus;
    }

    public String getUserLastBillingDate() {
        return userLastBillingDate;
    }

    public void setUserLastBillingDate(String userLastBillingDate) {
        this.userLastBillingDate = userLastBillingDate;
    }

    public String getUserNextBillingDate() {
        return userNextBillingDate;
    }

    public void setUserNextBillingDate(String userNextBillingDate) {
        this.userNextBillingDate = userNextBillingDate;
    }
}
