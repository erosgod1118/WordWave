package co.polarpublishing.userservice.repository.read;

import co.polarpublishing.dbcommon.repository.BaseReadonlyRepository;
import co.polarpublishing.userservice.entity.Feature;
import co.polarpublishing.userservice.entity.FeatureUsageRecord;
import org.springframework.data.jpa.repository.Query;

public interface FeatureUsageRecordReadRepository extends
    BaseReadonlyRepository<FeatureUsageRecord, FeatureUsageRecord.Id> {

  @Query("select count(fur) from FeatureUsageRecord fur where fur.id.user.id = :userId and fur.id.feature = :feature and fur.id.timestamp >= :from and fur.id.timestamp < :to")
  int countByUserIdAndFeatureAndTimestampRange(long userId, Feature feature, long from, long to);
}
