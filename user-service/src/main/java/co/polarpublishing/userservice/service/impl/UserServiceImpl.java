package co.polarpublishing.userservice.service.impl;

import co.polarpublishing.common.constant.RoleName;
import co.polarpublishing.common.dto.MailDto;
import co.polarpublishing.common.dto.UserDto;
import co.polarpublishing.common.dto.UserRoleDto;
import co.polarpublishing.userservice.assembler.UserAssembler;
import co.polarpublishing.userservice.convertor.UserConverter;
import co.polarpublishing.userservice.dto.ChangePasswordDto;
import co.polarpublishing.userservice.dto.ConfirmAccountDto;
import co.polarpublishing.userservice.dto.ResetPasswordDto;
import co.polarpublishing.userservice.dto.TokenInfo;
import co.polarpublishing.userservice.dto.TrialQuestionnaireDto;
import co.polarpublishing.userservice.dto.TrialUpgradeDto;
import co.polarpublishing.userservice.dto.UnsubscribeDto;
import co.polarpublishing.userservice.dto.UserBillingHistoryDto;
import co.polarpublishing.userservice.dto.UserWpDataDto;
import co.polarpublishing.userservice.entity.*;
import co.polarpublishing.userservice.exception.AccountAlreadyConfirmedException;
import co.polarpublishing.userservice.exception.UserNotFoundException;
import co.polarpublishing.userservice.repository.read.UserReadRepository;
import co.polarpublishing.userservice.repository.write.TrialQuestionnaireWriteRepository;
import co.polarpublishing.userservice.repository.write.UserBillingHistoryWriteRepository;
import co.polarpublishing.userservice.repository.write.UserUnsubscribeReasonWriteRepository;
import co.polarpublishing.userservice.repository.write.UserWriteRepository;
import co.polarpublishing.userservice.service.JwtService;
import co.polarpublishing.userservice.service.MailService;
import co.polarpublishing.userservice.service.UserService;
import co.polarpublishing.userservice.service.WpDataService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserReadRepository userReadRepository;
  private UserWriteRepository userWriteRepository;
  private TrialQuestionnaireWriteRepository trialQuestionnaireWriteRepository;
  private UserUnsubscribeReasonWriteRepository userUnsubscribeReasonWriteRepository;
  private UserBillingHistoryWriteRepository userBillingHistoryWriteRepository;
  private UserAssembler userAssembler;
  private JwtService jwtService;

  private MailService mailService;

  @Autowired private final UserConverter userConverter;

  @Autowired private final WpDataService wpDataService;

  @Override
  public UserDto findByEmail(String email) throws UserNotFoundException {
    User user = findModelByEmail(email);

    return userAssembler.toDto(user);
  }

  @Override
  public User findModelByEmail(String email) throws UserNotFoundException {
    return Optional.ofNullable(userReadRepository.findByEmailAndBannedIsFalseAllIgnoreCase(email))
        .orElseThrow(UserNotFoundException::new);
  }

  @Override
  public UserDto findById(Long id) throws UserNotFoundException {
    User user =
        Optional.ofNullable(userReadRepository.findByIdAndBannedIsFalse(id))
            .orElseThrow(UserNotFoundException::new);

    return userAssembler.toDto(user);
  }

  @Override
  public User findByResetToken(String resetToken) throws UserNotFoundException {
    return Optional.ofNullable(userReadRepository.findByResetTokenAndBannedIsFalse(resetToken))
            .orElseThrow(UserNotFoundException::new);
  }

  public UserDto resetPassword(ResetPasswordDto resetPasswordDto) throws UserNotFoundException {
    User user = this.findByResetToken(resetPasswordDto.getResetToken());
    user.setPassword(new BCryptPasswordEncoder().encode(resetPasswordDto.getNewPassword()));
    user.setResetToken(null);
    this.update(user);

    UserDto userDto = userAssembler.toDto(user);
    TokenInfo tokenInfo = TokenInfo.builder()
            .userId(userDto.getUserId())
            .email(userDto.getEmail())
            .build();
    userDto.setAccessToken(jwtService.generateAccessToken(tokenInfo, user.getUserCurrentPlan()));

    return userDto;
  }

  @Override
  public User findByConfirmationToken(String confirmationToken) throws UserNotFoundException {
    return Optional.ofNullable(userReadRepository.findByConfirmationTokenAndBannedIsFalse(confirmationToken))
            .orElseThrow(UserNotFoundException::new);
  }

  public UserDto confirmAccount(ConfirmAccountDto confirmAccountDto) throws UserNotFoundException, AccountAlreadyConfirmedException {
    User user = this.findByConfirmationToken(confirmAccountDto.getConfirmationToken());
    if (user.getUserSubscriptionStatus().equals("1")) {
      throw new AccountAlreadyConfirmedException();
    }

    user.setUserSubscriptionStatus("1");
    this.update(user);

    UserDto userDto = userAssembler.toDto(user);
    TokenInfo tokenInfo = TokenInfo.builder()
            .userId(userDto.getUserId())
            .email(userDto.getEmail())
            .build();
    userDto.setAccessToken(jwtService.generateAccessToken(tokenInfo, user.getUserCurrentPlan()));

    return userDto;
  }

  @Override
  public UserDto update(Long userId, UserDto userDto) throws UserNotFoundException {
    try {
      Optional.ofNullable(userWriteRepository.findByIdAndBannedIsFalse(userId))
          .orElseThrow(UserNotFoundException::new);

      User user = userAssembler.toEntity(userDto);
      user.setId(userId);

      User updatedUser = userWriteRepository.save(user);

      return userAssembler.toDto(updatedUser);

    } catch (Exception ex) {
      throw new UserNotFoundException();
    }
  }

  @Override
  public User update(User user) throws UserNotFoundException {
      Optional.ofNullable(userWriteRepository.findByIdAndBannedIsFalse(user.getId()))
              .orElseThrow(UserNotFoundException::new);
      return userWriteRepository.save(user);
  }

  @Override
  public void changePassword(Long userId, ChangePasswordDto changePasswordDto)
      throws UserNotFoundException {
    User user;

    try {
      user =
          Optional.ofNullable(userWriteRepository.findByIdAndBannedIsFalse(userId))
              .orElseThrow(UserNotFoundException::new);

    } catch (Exception ex) {
      throw new UserNotFoundException();
    }

    if (!BCrypt.checkpw(changePasswordDto.getCurrentPassword(), user.getPassword())) {
      throw new UserNotFoundException("User with such password does not exist.");
    }

    user.setPassword(new BCryptPasswordEncoder().encode(changePasswordDto.getNewPassword()));
    userWriteRepository.save(user);
  }

  @Override
  public UserWpDataDto getUserWpData(String email) throws UserNotFoundException {
    UserWpDataDto userWpDataDto = new UserWpDataDto();
    User user = findModelByEmail(email);

    userWpDataDto.setUserSubscriptionStatus(user.getUserSubscriptionStatus());
    userWpDataDto.setUserLastBillingDate(user.getUserLastBillingDate());
    userWpDataDto.setUserNextBillingDate(user.getUserNextBillingDate());
    userWpDataDto.setUserCurrentPlan(user.getUserCurrentPlan());

    return userWpDataDto;
  }

  @Override
  public List<UserBillingHistoryDto> getUserBillingHistory(String email) throws UserNotFoundException {
    User user = findModelByEmail(email);
    this.updateBillingHistory(user);

    return userConverter.convertFromListBillingHistoryDao(
        userBillingHistoryWriteRepository.findAllByUser(user));
  }

  @Override
  public void updateUserWpData() {
    List<User> users = userReadRepository.findAll();
    for (User u : users) {
      u = getDataFromWp(u);
      if (u != null) {
        userWriteRepository.save(u);
      }
    }
  }

  @Override
  public void updateUserWpDataForRequest(String email) throws UserNotFoundException {
    User user = findModelByEmail(email);
    user = getDataFromWp(user);
    if (user != null) {
      userWriteRepository.save(user);
    }
  }

  @Override
  public boolean isAdmin(Long userId) {
    try {
      List<RoleName> roles = this.findById(userId).getRoles()
              .stream()
              .map(UserRoleDto::getRole)
              .collect(toList());

     return roles.contains(RoleName.ROLE_ADMIN);
    } catch (UserNotFoundException e) {
      return false;
    }
  }

  private User getDataFromWp(User user) {
    Future<User> futureUser;

    try {
      futureUser = wpDataService.getUserWpData(user);
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }

    try {
      user = futureUser.get();
    } catch (CancellationException | InterruptedException | ExecutionException e) {
      log.error(e.getMessage());
      return null;
    }
    return user;
  }

  @Override
  public String changeUserSubscription(String email, String planType) throws UserNotFoundException {
    User user = findModelByEmail(email);
    String planId = wpDataService.getSubscriptionPlanId(planType);
    user = wpDataService.changeUserSubscription(user, planId);
    if (user == null) {
      throw new UserNotFoundException();
    }
    userWriteRepository.save(user);
    return user.getUserCurrentPlan();
  }

  @Override
  public UnsubscribeDto unsubscribeUser(String email, String reason) throws UserNotFoundException {
    UnsubscribeDto.UnsubscribeDtoBuilder response = UnsubscribeDto.builder();
    User user = findModelByEmail(email);
    long first = userBillingHistoryWriteRepository.findAllByUser(user)
            .stream()
            .map(UserBillingHistory::getCreatedAt)
            .min(Long::compareTo)
            .orElse(-1L);
    if (first == -1L) {
      return response.status(false)
              .title("Cancellation failed")
              .message("First billing date could not be determined for user: " + email)
              .build();
    }
    LocalDate date = LocalDateTime.ofEpochSecond(first, 0, ZoneOffset.UTC).toLocalDate();
    Instant firstPayment = date.atStartOfDay(ZoneId.of("UTC")).toInstant();
    Instant currentTime = Instant.now();
    Instant fourteenDaysCheck = firstPayment.plus(Duration.ofDays(14));
    int immediately;
    if (currentTime.compareTo(fourteenDaysCheck) < 0) {
      immediately = 1; // Cancel Immediately
      response.message("Your subscription has been successfully cancelled and your refund has been issued. \n" +
              "Thank you for giving our tools a try!");
    } else {
      immediately = 2; // Cancel at the end of current period
      response.message("Your subscription has been successfully cancelled. You won't be billed again and your account " +
              "will remain active until the end of the current subscription period (" + user.getUserNextBillingDate() + ").\n" +
              "Thank you for using our tools, we hope to see you again in the future!");
    }
    response.title("Cancellation Successful");
    String success = wpDataService.unsubscribeUser(user, immediately);
    if (success != null && success.equals("success") && immediately == 1) {
      user.setUserCurrentPlan("Cancelled");
      userWriteRepository.save(user);
    }
    response.status(immediately == 1);

    UserUnsubscribeReason unsubscribeReason = UserUnsubscribeReason.builder()
            .userId(user.getId())
            .reason(reason)
            .build();
    userUnsubscribeReasonWriteRepository.save(unsubscribeReason);

    return response.build();
  }

  @Override
  public TrialQuestionnaireDto answerTrialQuestionnaire(String email, TrialQuestionnaireDto questionnaire) throws UserNotFoundException {
    User user = findModelByEmail(email);
    TrialQuestionnaire entity = TrialQuestionnaire.builder()
            .userId(user.getId())
            .answers(questionnaire.getAnswers())
            .build();
    trialQuestionnaireWriteRepository.save(entity);
    questionnaire.setStatus(true);

    return questionnaire;
  }

  @Override
  public TrialQuestionnaireDto checkTrialQuestionnaire(String email) throws UserNotFoundException {
    User user = findModelByEmail(email);
    TrialQuestionnaire entity = trialQuestionnaireWriteRepository.findByUserId(user.getId());

    return TrialQuestionnaireDto.builder().status(entity != null).build();
  }

  @Override
  public String trialUpgrade(TrialUpgradeDto upgradeDto) throws UserNotFoundException {
    User user = Optional.ofNullable(userReadRepository.findByEmailAndBannedIsFalseAllIgnoreCase(upgradeDto.getEmail()))
                    .orElseThrow(UserNotFoundException::new);

    String result = wpDataService.trialUpgrade(upgradeDto);

    if ("success".equals(result)) {
      user.setUserCurrentPlan(upgradeDto.getPlan());
      userWriteRepository.save(user);
    }

    return result;
  }

  @Override
  public JSONObject checkCoupon(String coupon) {
      return  wpDataService.checkCoupon(coupon);
  }

  @Override
  public void saveBillingHistory(UserBillingHistoryDto userBillingHistoryDto)
      throws UserNotFoundException {
    User user =
        Optional.ofNullable(
                userReadRepository.findByIdAndBannedIsFalse(userBillingHistoryDto.getUserId()))
            .orElseThrow(UserNotFoundException::new);

    UserBillingHistory userBillingHistory =
        userConverter.convertFromBillingHistoryDto(userBillingHistoryDto, user);

    userBillingHistoryWriteRepository.save(userBillingHistory);
  }

  @Override
  public void updateAllBillingHistory() {
    List<User> users = userReadRepository.findAll();
    for (User u : users) {
      try {
        updateBillingHistory(u);
      } catch (UserNotFoundException e) {
          log.info("Failed to update subscription for user: {}", u.getEmail());
       }
    }
  }

  @Override
  public void updateBillingHistory(User user) throws UserNotFoundException {
    if (user.getUserCurrentPlan().equals("Trial")) {
      throw new UserNotFoundException();
    }

    Future<List<UserBillingHistory>> futureBillingHistory;
    List<UserBillingHistory> billingHistoryList = null;
    try {
      futureBillingHistory = wpDataService.getBillingHistory(user);
      billingHistoryList = futureBillingHistory.get();
    } catch (InterruptedException | ExecutionException e) {
      log.error(e.getMessage());
    }

    if (billingHistoryList == null || billingHistoryList.size() == 0) {
      throw new UserNotFoundException();
    }

    List<UserBillingHistory> oldRecords = userBillingHistoryWriteRepository.findAllByUser(user);
    List<UserBillingHistory> newRecords = billingHistoryList.stream()
            .filter(o1 -> oldRecords.stream().noneMatch(o2 -> o2.getCreatedAt().equals(o1.getCreatedAt())))
            .collect(Collectors.toList());

    for (UserBillingHistory ubh : newRecords) {
      if (ubh != null) {
        userBillingHistoryWriteRepository.save(ubh);
      }
    }
  }

  @Override
  public void sendUnsubscribeReasons(String cancellationMail) {
    Long timestamp = LocalDate.now().minusDays(14).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    List<UserUnsubscribeReason> reasons = userUnsubscribeReasonWriteRepository.findAllByCreationTimestampAfter(timestamp);

    StringBuilder message = new StringBuilder("<table><tr> <th>ID</th> <th>USER_ID</th> <th>REASON</th> <th>DATE</th></tr>");
    
    reasons.forEach(reason -> message
            .append("<tr><td>").append(reason.getId()).append("</td>")
            .append("<td>").append(reason.getUserId()).append("</td>")
            .append("<td>").append(reason.getReason()).append("</td>")
            .append("<td>").append(new Date(reason.getCreationTimestamp())).append("</td></tr>")
    );

    message.append("</table>");

    MailDto mail = MailDto
            .builder()
            .subject("Cancellation Reasons")
            .message(message.toString())
            .to(cancellationMail)
            .build();

    mailService.sendHtml(mail, "BookBeam Cancellations <support@bookbeam.io>");
  }

  @Override
  public String crateCustomerPortal(String email, String returnUrl) {
    String data = wpDataService.createCustomerPortal(email, returnUrl);
    return data;
  }

}