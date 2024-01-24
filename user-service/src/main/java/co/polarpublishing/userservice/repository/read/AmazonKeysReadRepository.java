package co.polarpublishing.userservice.repository.read;

import co.polarpublishing.dbcommon.repository.BaseReadonlyRepository;
import co.polarpublishing.userservice.entity.AmazonKeys;
import co.polarpublishing.userservice.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmazonKeysReadRepository extends BaseReadonlyRepository<AmazonKeys, Long> {

	List<AmazonKeys> findByUser(User user);

	@Query("select k from AmazonKeys k join k.user u join k.marketplace m " +
					"where u.id = ?1 and m.id = ?2")
	AmazonKeys findByUserAndMarketplace(long userId, long marketplaceId);

}
