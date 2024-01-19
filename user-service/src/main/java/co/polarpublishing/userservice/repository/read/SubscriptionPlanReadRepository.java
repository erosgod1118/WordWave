package co.polarpublishing.userservice.repository.read;

import co.polarpublishing.dbcommon.entity.SubscriptionPlan;
import co.polarpublishing.dbcommon.repository.BaseReadonlyRepository;
import java.util.Optional;

public interface SubscriptionPlanReadRepository
    extends BaseReadonlyRepository<SubscriptionPlan, Long> {

  Optional<SubscriptionPlan> findByNameIgnoreCase(String name);
}
