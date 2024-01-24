package co.polarpublishing.userservice.service;

import co.polarpublishing.userservice.dto.FeaturesUsageState;
import co.polarpublishing.userservice.dto.UsageState;
import co.polarpublishing.userservice.dto.UserState;
import co.polarpublishing.userservice.entity.Feature;
import co.polarpublishing.userservice.exception.UserNotFoundException;
import co.polarpublishing.userservice.service.dto.ChromeExtensionStateDto;

public interface StateManagementService {

  ChromeExtensionStateDto getChromeExtensionState(long userId) throws UserNotFoundException;

  UserState getUserState(long userId) throws UserNotFoundException;

  FeaturesUsageState getFeaturesUsageState(long userId) throws UserNotFoundException;

  UsageState getFeatureUsageState(long userId, Feature feature) throws UserNotFoundException;
  
}
