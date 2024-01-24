package co.polarpublishing.userservice.service;

import co.polarpublishing.common.dto.UserDto;
import co.polarpublishing.userservice.dto.ChangePasswordDto;
import co.polarpublishing.userservice.dto.ConfirmAccountDto;
import co.polarpublishing.userservice.dto.ResetPasswordDto;
import co.polarpublishing.userservice.dto.TrialQuestionnaireDto;
import co.polarpublishing.userservice.dto.TrialUpgradeDto;
import co.polarpublishing.userservice.dto.UnsubscribeDto;
import co.polarpublishing.userservice.dto.UserBillingHistoryDto;
import co.polarpublishing.userservice.dto.UserWpDataDto;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.exception.AccountAlreadyConfirmedException;
import co.polarpublishing.userservice.exception.UserNotFoundException;

import org.json.JSONObject;

import java.util.List;

/**
 * The {@code UserService} interface provides methods in order to find, save and update user(s).
 */
public interface UserService {

	/**
	 * Returns the {@link UserDto} object by {@code email}.
	 *
	 * @param email {@code String} value specifying the email address of the user.
	 * @return the {@link UserDto} object by {@code email}.
	 * @throws UserNotFoundException if user does not exists by specifying {@code email}.
	 */
	UserDto findByEmail(String email) throws UserNotFoundException;

	User findModelByEmail(String email) throws UserNotFoundException;

	/**
	 * Returns the {@link UserDto} object by {@code id}.
	 *
	 * @param id {@code Long} value specifying the unique identifier of the user.
	 * @return the {@link UserDto} object by {@code id}.
	 * @throws UserNotFoundException if user does not exists by specifying {@code email}.
	 */
	UserDto findById(Long id) throws UserNotFoundException;

	/**
	 * Returns the {@link UserDto} object by {@code resetToken}.
	 *
	 * @param resetToken {@code String} value specifying the token reset.
	 * @return the {@link UserDto} object by {@code resetToken}.
	 * @throws UserNotFoundException if user does not exists by specifying {@code resetToken}.
	 */
	User findByResetToken(String resetToken) throws UserNotFoundException;


	/**
	 * Returns the {@link UserDto} object by {@code resetPasswordDto}.
	 *
	 * @param resetPasswordDto {@code ResetPasswordDto} value specifying the token reset.
	 * @return the {@link UserDto} object by {@code resetPasswordDto}.
	 * @throws UserNotFoundException if user does not exists by specifying {@code resetPasswordDto}.
	 */
	UserDto resetPassword(ResetPasswordDto resetPasswordDto) throws UserNotFoundException;

	/**
	 * Returns the {@link UserDto} object by {@code confirmationToken}.
	 *
	 * @param confirmationToken {@code String} value specifying the account confirmation token.
	 * @return the {@link UserDto} object by {@code confirmationToken}.
	 * @throws UserNotFoundException if user does not exists by specifying {@code confirmationToken}.
	 */
	User findByConfirmationToken(String confirmationToken) throws UserNotFoundException;


	/**
	 * Returns the {@link UserDto} object by {@code resetPasswordDto}.
	 *
	 * @param confirmAccountDto {@code ConfirmAccountDto} value specifying the token reset.
	 * @return the {@link UserDto} object by {@code resetPasswordDto}.
	 * @throws UserNotFoundException if user does not exists by specifying {@code resetPasswordDto}.
	 */
	UserDto confirmAccount(ConfirmAccountDto confirmAccountDto) throws UserNotFoundException, AccountAlreadyConfirmedException;

	/**
	 * Updates and returns the {@link UserDto} object.
	 *
	 * @param userId {@code Long} value specifying the unique identifier of the user.
	 * @param userDto the {@link UserDto} object.
	 * @return the {@link UserDto} object.
	 * @throws UserNotFoundException if user does not exists by specifying {@code userId}.
	 */
	UserDto update(Long userId, UserDto userDto) throws UserNotFoundException;

	/**
	 * Updates and returns the {@link User} object.
	 *
	 * @param user the {@link User} object.
	 * @return the {@link User} object.
	 * @throws UserNotFoundException if user does not exists by specifying {@code userId}.
	 */
	User update(User user) throws UserNotFoundException;

	/**
	 * Changes user's current password.
	 *
	 * @param userId {@code Long} value specifying the unique identifier of the user.
	 * @param changePasswordDto the {@link ChangePasswordDto} object, that contains current and old passwords.
	 * @throws UserNotFoundException if user does not exists by specifying {@code userId}.
	 */
	void changePassword(Long userId, ChangePasswordDto changePasswordDto) throws UserNotFoundException;

	// TODO commented out until the impl class is sorted out (refer to comments there)
	/*
	void promoteUser(long userId) throws UserNotFoundException;

	void demoteUser(long userId) throws UserNotFoundException;

	void ban(long userId) throws UserNotFoundException;

	void unban(long userId) throws UserNotFoundException;
	*/

	UserWpDataDto getUserWpData(String email) throws UserNotFoundException;

	List<UserBillingHistoryDto> getUserBillingHistory(String email) throws UserNotFoundException;

	void updateUserWpData() throws UserNotFoundException;

	void updateBillingHistory(User user) throws UserNotFoundException;

	void updateAllBillingHistory();

	void saveBillingHistory(UserBillingHistoryDto userBillingHistoryDto) throws UserNotFoundException;

	String changeUserSubscription(String email, String planType) throws UserNotFoundException;

	UnsubscribeDto unsubscribeUser(String email, String reason) throws UserNotFoundException;

	TrialQuestionnaireDto answerTrialQuestionnaire(String email, TrialQuestionnaireDto questionnaire) throws UserNotFoundException;

	TrialQuestionnaireDto checkTrialQuestionnaire(String email) throws UserNotFoundException;

	String trialUpgrade(TrialUpgradeDto upgradeDto) throws UserNotFoundException;

	JSONObject checkCoupon(String coupon);

	void updateUserWpDataForRequest(String email) throws UserNotFoundException;

	boolean isAdmin(Long userId);

	void sendUnsubscribeReasons(String cancellationMail);

	String crateCustomerPortal(String email, String returnUrl);

}