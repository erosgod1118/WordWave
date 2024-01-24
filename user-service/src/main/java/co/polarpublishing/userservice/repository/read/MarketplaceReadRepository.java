package co.polarpublishing.userservice.repository.read;

import co.polarpublishing.dbcommon.entity.Marketplace;
import co.polarpublishing.dbcommon.repository.BaseReadonlyRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface MarketplaceReadRepository extends BaseReadonlyRepository<Marketplace, Long> {
  
}
