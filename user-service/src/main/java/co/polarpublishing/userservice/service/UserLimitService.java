package co.polarpublishing.userservice.service;

import co.polarpublishing.userservice.entity.UserLimit;

import java.util.List;

public interface UserLimitService {

    boolean exceeds(String endpoint, String type, Long userId, String plan);

    List<UserLimit> getAllLimits(Long userId);

    UserLimit getSpecificLimit(Long userId, String type);

    void insertUserLimits(Long userId);

}
