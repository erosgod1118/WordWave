package co.polarpublishing.userservice.repository.write;

import co.polarpublishing.dbcommon.repository.BaseReadWriteRepository;
import co.polarpublishing.userservice.entity.UserLimit;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLimitRepository extends BaseReadWriteRepository<UserLimit, Long> {

	List<UserLimit> findAllByUserId(Long userId);

	UserLimit findByUserIdAndName(Long userId, String name);

	UserLimit findByUserIdAndType(Long userId, String type);

}
