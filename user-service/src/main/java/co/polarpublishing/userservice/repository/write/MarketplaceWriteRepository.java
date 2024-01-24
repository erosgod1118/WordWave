package co.polarpublishing.userservice.repository.write;

import co.polarpublishing.dbcommon.entity.Marketplace;
import co.polarpublishing.dbcommon.repository.BaseReadWriteRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface MarketplaceWriteRepository extends BaseReadWriteRepository<Marketplace, Long> {
  
}
