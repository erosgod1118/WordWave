package co.polarpublishing.userservice.service.impl;

import co.polarpublishing.userservice.dto.TrialUpgradeDto;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.entity.UserBillingHistory;
import co.polarpublishing.userservice.service.WpDataService;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Service
@Slf4j
public class WpDataServiceImpl implements WpDataService {

  @Value("${wp.username}")
  private String wpUsername;

  @Value("${wp.password}")
  private String wpPassword;

  @Value("${wp.apiUrl}")
  private String wpApiUrl;

  @Override
  @Async("wpThreadPoolTaskExecutor")
  public Future<User> getUserWpData(User user) {
    HttpPost post = new HttpPost(wpApiUrl + "/check_users_subscription");

    log.info("Get data from WP");

    List<NameValuePair> urlParameters = new ArrayList<>();

    urlParameters.add(new BasicNameValuePair("username", wpUsername));
    urlParameters.add(new BasicNameValuePair("password", wpPassword));
    urlParameters.add(new BasicNameValuePair("userEmail", user.getEmail()));

    try {
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    post.addHeader("Content-Type", "application/x-www-form-urlencoded");

    String jsonResponse = "";
    try {
      try (CloseableHttpClient httpClient = HttpClients.createDefault();
          CloseableHttpResponse response = httpClient.execute(post)) {
        jsonResponse = EntityUtils.toString(response.getEntity());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    JSONObject obj = new JSONObject(jsonResponse);

    log.info("WP Api response code: " + obj.get("code"));
    log.info("WP Api response message: " + obj.get("message"));

    if (obj.get("data") == JSONObject.NULL || obj.get("message").equals("No record found")) {
      return null;
    }

    JSONObject data = obj.getJSONObject("data");
    JSONObject userData = data.getJSONObject("user");

    log.info("Json object is not empty");

    String subscriptionStatus = data.getString("hasValidSubscription");
    String lastBillingDate = userData.getString("last_billing_date");
    String nextBillingDate = userData.getString("next_billing_date");
    String currentPlanName = userData.getString("current_plan_name");

    user.setUserSubscriptionStatus(subscriptionStatus);
    user.setUserLastBillingDate(lastBillingDate);
    user.setUserNextBillingDate(nextBillingDate);
    user.setUserCurrentPlan(currentPlanName);

    return new AsyncResult<User>(user);
  }

  @Override
  @Async("wpThreadPoolTaskExecutor")
  public Future<List<UserBillingHistory>> getBillingHistory(User user) {
    HttpPost post = new HttpPost(wpApiUrl + "/get_user_billing_history");
    List<NameValuePair> urlParameters = new ArrayList<>();

    urlParameters.add(new BasicNameValuePair("username", wpUsername));
    urlParameters.add(new BasicNameValuePair("password", wpPassword));
    urlParameters.add(new BasicNameValuePair("userEmail", user.getEmail()));

    try {
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    post.addHeader("Content-Type", "application/x-www-form-urlencoded");

    String jsonResponse = "";
    try {
      try (CloseableHttpClient httpClient = HttpClients.createDefault();
          CloseableHttpResponse response = httpClient.execute(post)) {
        jsonResponse = EntityUtils.toString(response.getEntity());
      }
    } catch (IOException e) {
     log.error(e.getMessage(), e);
    }

    JSONObject obj = new JSONObject(jsonResponse);

    log.info("WP Api response code: " + obj.get("code"));
    log.info("WP Api response message: " + obj.get("message"));

    if (obj.get("data") == JSONObject.NULL || obj.get("message").equals("No record found")) {
      return null;
    }

    JSONArray billingList = obj.getJSONArray("data");

    List<UserBillingHistory> userBillingHistoryList = new ArrayList<>();
    
    for (int i = 0; i < billingList.length(); i++) {
      UserBillingHistory record = new UserBillingHistory();
      JSONObject billingObject = billingList.getJSONObject(i);
      Integer amount = billingObject.getInt("amount");
      Long createdAt = billingObject.getLong("created");
      String invoiceLink = billingObject.getString("receipt_url");

      record.setAmount(amount);
      record.setCreatedAt(createdAt);
      record.setInvoiceLink(invoiceLink);
      record.setUser(user);
      userBillingHistoryList.add(record);
    }

    return new AsyncResult<List<UserBillingHistory>>(userBillingHistoryList);
  }

  @Override
  public User changeUserSubscription(User user, String planType) {
    HttpPost post = new HttpPost(wpApiUrl + "/change_user_subscription_plan");
    List<NameValuePair> urlParameters = new ArrayList<>();

    urlParameters.add(new BasicNameValuePair("username", wpUsername));
    urlParameters.add(new BasicNameValuePair("password", wpPassword));
    urlParameters.add(new BasicNameValuePair("userEmail", user.getEmail()));
    urlParameters.add(new BasicNameValuePair("plan_id", planType));

    try {
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    post.addHeader("Content-Type", "application/x-www-form-urlencoded");

    String jsonResponse = "";
    try {
      try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
        jsonResponse = EntityUtils.toString(response.getEntity());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    JSONObject obj = new JSONObject(jsonResponse);
    if (obj.get("data") == JSONObject.NULL || obj.get("message").equals("No record found")) {
      return null;
    }

    JSONObject data = obj.getJSONObject("data");
    JSONObject items = data.getJSONObject("items");
    JSONArray itemsData = items.getJSONArray("data");
    JSONObject item = itemsData.getJSONObject(0);
    JSONObject plan = item.getJSONObject("plan");
    String newSubscriptionName = plan.getString("nickname");

    user.setUserCurrentPlan(newSubscriptionName);
    return user;
  }

  @Override
  public String unsubscribeUser(User user, Integer immediately) {
    HttpPost post = new HttpPost(wpApiUrl + "/cancel_user_subscription");
    List<NameValuePair> urlParameters = new ArrayList<>();

    urlParameters.add(new BasicNameValuePair("username", wpUsername));
    urlParameters.add(new BasicNameValuePair("password", wpPassword));
    urlParameters.add(new BasicNameValuePair("userEmail", user.getEmail()));
    urlParameters.add(new BasicNameValuePair("immediate", immediately.toString()));

    try {
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    post.addHeader("Content-Type", "application/x-www-form-urlencoded");

    String jsonResponse = "";
    try {
      try (CloseableHttpClient httpClient = HttpClients.createDefault();
           CloseableHttpResponse response = httpClient.execute(post)) {
        jsonResponse = EntityUtils.toString(response.getEntity());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    JSONObject obj = new JSONObject(jsonResponse);
    if (obj.get("data") == JSONObject.NULL || obj.get("message").equals("No record found")) {
      return null;
    }

    return obj.getString("code");
  }

  @Override
  public String getSubscriptionPlanId(String nickname) {
    HttpPost post = new HttpPost(wpApiUrl + "/get_all_subscription_plans");
    List<NameValuePair> urlParameters = new ArrayList<>();

    urlParameters.add(new BasicNameValuePair("username", wpUsername));
    urlParameters.add(new BasicNameValuePair("password", wpPassword));

    String jsonResponse = "";
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
      post.addHeader("Content-Type", "application/x-www-form-urlencoded");

      HttpResponse response = httpClient.execute(post);
      jsonResponse = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      log.error("", e);
    }

    JSONObject res = new JSONObject(jsonResponse);
    if (res.get("data") == JSONObject.NULL || res.get("message").equals("No record found")) {
      return null;
    }

    // search plan id by nickname
    for(Object o: res.getJSONArray("data")) {
      if (o instanceof JSONObject) {
        JSONObject plan = (JSONObject) o;
        if (plan.getString("nickname").equals(nickname)) {
          return plan.getString("id");
        }
      }
    }

    return null;
  }

  @Override
  public String trialUpgrade(TrialUpgradeDto upgradeDto) {
    HttpPost post = new HttpPost(wpApiUrl + "/trial_upgrade");
    List<NameValuePair> urlParameters = new ArrayList<>();

    urlParameters.add(new BasicNameValuePair("username", wpUsername));
    urlParameters.add(new BasicNameValuePair("password", wpPassword));
    urlParameters.add(new BasicNameValuePair("plan", upgradeDto.getPlan()));
    urlParameters.add(new BasicNameValuePair("period", upgradeDto.getPeriod()));
    urlParameters.add(new BasicNameValuePair("coupon", upgradeDto.getCoupon()));
    urlParameters.add(new BasicNameValuePair("userEmail", upgradeDto.getEmail()));
    urlParameters.add(new BasicNameValuePair("stripeToken", upgradeDto.getToken()));

    String jsonResponse = "";
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
      post.addHeader("Content-Type", "application/x-www-form-urlencoded");

      HttpResponse response = httpClient.execute(post);
      jsonResponse = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      log.error("", e);
    }

    JSONObject res = new JSONObject(jsonResponse);
    if (res.get("data") == JSONObject.NULL || res.get("message").equals("No record found")) {
      return null;
    }

    return res.getString("code");
  }

  @Override
  public JSONObject checkCoupon(String coupon) {
    HttpPost post = new HttpPost(wpApiUrl.replace("wp-json/auth-api", "wp-admin/admin-ajax.php"));
    List<NameValuePair> urlParameters = new ArrayList<>();

    urlParameters.add(new BasicNameValuePair("coupon", coupon));
    urlParameters.add(new BasicNameValuePair("username", wpUsername));
    urlParameters.add(new BasicNameValuePair("password", wpPassword));
    urlParameters.add(new BasicNameValuePair("action", "checkCoupon"));

    String jsonResponse = "";
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
      post.addHeader("Content-Type", "application/x-www-form-urlencoded");

      HttpResponse response = httpClient.execute(post);
      jsonResponse = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      log.error("", e);
    }

    JSONObject res = new JSONObject(jsonResponse);

    return res;
  }

  @Override
  public String createCustomerPortal(String email, String returnUrl) {
    HttpPost post = new HttpPost(wpApiUrl + "/create-customer-portal-session");
    List<NameValuePair> urlParameters = new ArrayList<>();

    urlParameters.add(new BasicNameValuePair("username", wpUsername));
    urlParameters.add(new BasicNameValuePair("password", wpPassword));
    urlParameters.add(new BasicNameValuePair("userEmail", email));
    urlParameters.add(new BasicNameValuePair("returnUrl", returnUrl));

    try {
      post.setEntity(new UrlEncodedFormEntity(urlParameters));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    post.addHeader("Content-Type", "application/x-www-form-urlencoded");

    String jsonResponse = "";
    try {
      try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {
        jsonResponse = EntityUtils.toString(response.getEntity());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    JSONObject obj = new JSONObject(jsonResponse);
    if (obj.get("data") == JSONObject.NULL || obj.get("message").equals("No record found")) {
      return null;
    }

    String data = obj.getString("data");
    return data;
  }

}