package co.polarpublishing.userservice.service;

import co.polarpublishing.userservice.dto.TrialUpgradeDto;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.entity.UserBillingHistory;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Future;

public interface WpDataService {

	Future<User> getUserWpData(User user);

	Future<List<UserBillingHistory>> getBillingHistory(User user);

	User changeUserSubscription(User user, String subscriptionType);

	String unsubscribeUser(User user, Integer immediately);

	String getSubscriptionPlanId(String nickname);

	String trialUpgrade(TrialUpgradeDto upgradeDto);

	JSONObject checkCoupon(String coupon);

	String createCustomerPortal(String email, String returnUrl);
	
}
