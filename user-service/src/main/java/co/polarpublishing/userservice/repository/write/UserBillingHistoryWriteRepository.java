package co.polarpublishing.userservice.repository.write;

import co.polarpublishing.dbcommon.repository.BaseReadWriteRepository;
import co.polarpublishing.userservice.entity.User;
import co.polarpublishing.userservice.entity.UserBillingHistory;

import java.util.List;

public interface UserBillingHistoryWriteRepository extends BaseReadWriteRepository<UserBillingHistory, Long> {

    List<UserBillingHistory> findAllByUser(User user);
}
