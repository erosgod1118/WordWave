package co.polarpublishing.userservice.repository.write;

import co.polarpublishing.dbcommon.repository.BaseReadWriteRepository;
import co.polarpublishing.userservice.entity.User;

public interface UserWriteRepository extends BaseReadWriteRepository<User, Long> {

	User findByEmailAndBannedIsFalseAllIgnoreCase(String email);
	User findByIdAndBannedIsFalse(Long userId);
	User findByResetTokenAndBannedIsFalse(String resetToken);

}
