package co.polarpublishing.userservice.service.impl;

import co.polarpublishing.common.util.DateAndTimeUtil;
import co.polarpublishing.common.vo.DateAndTimeRange;
import co.polarpublishing.dbcommon.entity.FeatureUsageLimit;
import co.polarpublishing.dbcommon.entity.SubscriptionPlan;
import co.polarpublishing.userservice.dto.FeaturesUsageState;
import co.polarpublishing.userservice.dto.UsageState;
import co.polarpublishing.userservice.dto.UserState;
import co.polarpublishing.userservice.entity.Feature;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.entity.UserLimit;
import co.polarpublishing.userservice.exception.UserNotFoundException;
import co.polarpublishing.userservice.repository.read.FeatureUsageRecordReadRepository;
import co.polarpublishing.userservice.repository.read.SubscriptionPlanReadRepository;
import co.polarpublishing.userservice.repository.read.UserReadRepository;
import co.polarpublishing.userservice.repository.write.UserLimitRepository;
import co.polarpublishing.userservice.service.StateManagementService;
import co.polarpublishing.userservice.service.dto.ChromeExtensionStateDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateManagementServiceImpl implements StateManagementService {

  private final UserReadRepository userReadRepository;
  private final UserLimitRepository userLimitRepository;
  private final FeatureUsageRecordReadRepository featureUsageRecordReadRepository;
  private final SubscriptionPlanReadRepository subscriptionPlanReadRepository;

  @Override
  public ChromeExtensionStateDto getChromeExtensionState(long userId) throws UserNotFoundException {

    User foundUser = this.userReadRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
    if (foundUser.getUserCurrentPlan() != null
        && !foundUser.getUserCurrentPlan().trim().isEmpty()
        && foundUser.getUserCurrentPlan().trim().equalsIgnoreCase("cancelled")) {
      return ChromeExtensionStateDto.builder()
          .remainingTokensForBsrHistory(0)
          .remainingTokensForChromeExtensionUsage(0)
          .build();
    }

    Integer bsrHistoryFeatureUsageRemainingToken = null;
    DateAndTimeRange dateAndTimeRange = null;
    if (foundUser.getUserCurrentPlan().trim().equalsIgnoreCase("trial")) {
      dateAndTimeRange = new DateAndTimeRange(1420070400000l, System.currentTimeMillis());
    } else {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      long userLastBillingDateTimestamp = 0;

      try {
        userLastBillingDateTimestamp =
            formatter.parse(foundUser.getUserLastBillingDate()).getTime();
      } catch (ParseException e) {
        throw new RuntimeException(e.getMessage());
      }

      dateAndTimeRange = DateAndTimeUtil.getCurrentMonthDateAndTimeRangeWithRespectToReferenceDateAndTime(userLastBillingDateTimestamp);
    }

    int bsrHistoryFeatureUsageCount = this.featureUsageRecordReadRepository
                                            .countByUserIdAndFeatureAndTimestampRange(
                                              userId, 
                                              Feature.CE_BSR_HISTORY, 
                                              dateAndTimeRange.getFrom(), 
                                              dateAndTimeRange.getTo()
                                            );
    if (foundUser.getUserCurrentPlan().equalsIgnoreCase("Starter Annual") || foundUser.getUserCurrentPlan().equalsIgnoreCase("Starter Monthly")) {
      bsrHistoryFeatureUsageRemainingToken = 120 - bsrHistoryFeatureUsageCount;
    } else if (foundUser.getUserCurrentPlan().equalsIgnoreCase("Basic Annual") || foundUser.getUserCurrentPlan().equalsIgnoreCase("Basic Monthly")) {
      bsrHistoryFeatureUsageRemainingToken = 250 - bsrHistoryFeatureUsageCount;
    } else if (foundUser.getUserCurrentPlan().equalsIgnoreCase("Pro Annual")
        || foundUser.getUserCurrentPlan().equalsIgnoreCase("Pro Monthly")
        || foundUser.getUserCurrentPlan().equalsIgnoreCase("Custom Annual")
        || foundUser.getUserCurrentPlan().equalsIgnoreCase("Custom Monthly")) {
      bsrHistoryFeatureUsageRemainingToken = 500 - bsrHistoryFeatureUsageCount;
    } else if (foundUser.getUserCurrentPlan().equalsIgnoreCase("Trial")) {
      bsrHistoryFeatureUsageRemainingToken = 5 - bsrHistoryFeatureUsageCount;
    }

    bsrHistoryFeatureUsageRemainingToken =
        bsrHistoryFeatureUsageRemainingToken != null && bsrHistoryFeatureUsageRemainingToken >= 0
            ? bsrHistoryFeatureUsageRemainingToken
            : 0;

    // Getting chrome extension usage remaining tokens.
    Integer remainingTokensForChromeExtension = null;
    UserLimit userLimit = this.userLimitRepository.findByUserIdAndType(userId, Feature.CHROME_EXTENSION.getName());
    if (userLimit != null
        && foundUser.getUserCurrentPlan() != null
        && foundUser.getUserCurrentPlan().trim().equalsIgnoreCase("trial")) {
      remainingTokensForChromeExtension = userLimit.getMax() - userLimit.getValue();
      remainingTokensForChromeExtension = remainingTokensForChromeExtension >= 0 ? remainingTokensForChromeExtension : 0;
    }

    Integer maxChromeExtensionUsage =
        foundUser.getUserCurrentPlan().trim().equalsIgnoreCase("trial") && userLimit != null
            ? userLimit.getMax()
            : null;

    return ChromeExtensionStateDto.builder()
        .remainingTokensForBsrHistory(bsrHistoryFeatureUsageRemainingToken)
        .remainingTokensForChromeExtensionUsage(remainingTokensForChromeExtension)
        .maxChromeExtensionUsageQuantity(maxChromeExtensionUsage)
        .build();
  }

  @Override
  @Transactional(transactionManager = "readReplicaTransactionManager", readOnly = true)
  public UserState getUserState(long userId) throws UserNotFoundException {
    log.info("Getting user state for user {}.", userId);

    UserState userState = new UserState();
    userState.setFeaturesUsageState(this.getFeaturesUsageState(userId));

    return userState;
  }

  @Override
  @Transactional(transactionManager = "readReplicaTransactionManager", readOnly = true)
  public FeaturesUsageState getFeaturesUsageState(long userId) throws UserNotFoundException {
    log.info("Getting feature usage state for user {}.", userId);

    FeaturesUsageState featuresUsageState = new FeaturesUsageState();
    featuresUsageState.setListingAnalysisFeatureUsageState(
        this.getFeatureUsageState(userId, Feature.LISTING_ANALYSIS));

    return featuresUsageState;
  }

  @Override
  @Transactional(transactionManager = "readReplicaTransactionManager", readOnly = true)
  public UsageState getFeatureUsageState(long userId, Feature feature) throws UserNotFoundException {
    log.info("Getting feature usage state for user {} and feature {}.", userId, feature);

    User foundUser = this.userReadRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
    UsageState featureUsageState = new UsageState();
    
    if (foundUser.getUserCurrentPlan() != null
        && !foundUser.getUserCurrentPlan().trim().isEmpty()
        && foundUser.getUserCurrentPlan().trim().equalsIgnoreCase("cancelled")) {
      return featureUsageState;
    }

    SubscriptionPlan subscriptionPlan = this.subscriptionPlanReadRepository
            .findByNameIgnoreCase(foundUser.getUserCurrentPlan())
            .orElse(null);
    if (subscriptionPlan == null) {
      return featureUsageState;
    }

    FeatureUsageLimit listingAnalysisUsageLimit =
        subscriptionPlan.getFeatureUsageLimit(co.polarpublishing.dbcommon.entity.Feature.valueOf(feature.name()));
    if (listingAnalysisUsageLimit == null) {
      featureUsageState.setMaxAllowedQuantity(Integer.MAX_VALUE);
    } else {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      long userLastBillingDateTimestamp = 0;

      try {
        userLastBillingDateTimestamp = formatter.parse(foundUser.getUserLastBillingDate()).getTime();
      } catch (ParseException e) {
        throw new RuntimeException(e.getMessage());
      }

      DateAndTimeRange dateAndTimeRange =
          DateAndTimeUtil.getCurrentMonthDateAndTimeRangeWithRespectToReferenceDateAndTime(userLastBillingDateTimestamp);
      int count =
          this.featureUsageRecordReadRepository.countByUserIdAndFeatureAndTimestampRange(userId, feature, dateAndTimeRange.getFrom(), dateAndTimeRange.getTo());
      
      featureUsageState.setMaxAllowedQuantity(listingAnalysisUsageLimit.getMaxAllowedUsage());
      featureUsageState.setRemainingQuantity(listingAnalysisUsageLimit.getMaxAllowedUsage() - count);
      featureUsageState.setUsedQuantity(count);
    }

    return featureUsageState;
  }

}