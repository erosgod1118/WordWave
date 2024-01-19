package co.polarpublishing.userservice.repository.read;

import co.polarpublishing.dbcommon.repository.BaseReadonlyRepository;
import co.polarpublishing.userservice.entity.User;

public interface UserReadRepository extends BaseReadonlyRepository<User, Long> {

    User findByEmailAndBannedIsFalseAllIgnoreCase(String email);

    User findByIdAndBannedIsFalse(Long userId);

    User findByResetTokenAndBannedIsFalse(String resetToken);

    User findByConfirmationTokenAndBannedIsFalse(String confirmationToken);

}
