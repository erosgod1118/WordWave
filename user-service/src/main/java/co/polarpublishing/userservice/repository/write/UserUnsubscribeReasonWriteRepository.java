package co.polarpublishing.userservice.repository.write;

import co.polarpublishing.dbcommon.repository.BaseReadWriteRepository;
import co.polarpublishing.userservice.entity.UserUnsubscribeReason;

import java.util.List;

public interface UserUnsubscribeReasonWriteRepository extends BaseReadWriteRepository<UserUnsubscribeReason, Long> {
    List<UserUnsubscribeReason> findByUserId(Long userId);

    List<UserUnsubscribeReason> findAllByCreationTimestampAfter(Long time);
}
