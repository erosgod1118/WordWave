package co.polarpublishing.common.exception;

import co.polarpublishing.common.constant.ExceptionCode;

public class UnknownSubscriptionPlanException extends CustomRuntimeException {

  public UnknownSubscriptionPlanException(String subscriptionPlan) {
    super(ExceptionCode.UNKNOWN_SUBSCRIPTION_PLAN_EXCEPTION.getCode(),
        String.format("%s is not a known subscription plan!", subscriptionPlan));
  }

}
