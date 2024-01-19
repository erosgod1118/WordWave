package co.polarpublishing.userservice.repository.write;

import co.polarpublishing.userservice.entity.AmazonKeys;
import co.polarpublishing.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmazonKeysWriteRepository extends JpaRepository<AmazonKeys, Long> {

    List<AmazonKeys> findByUser(User user);

    @Query("select k from AmazonKeys k join k.user u join k.marketplace m " +
            "where u.id = ?1 and m.id = ?2")
    AmazonKeys findByUserAndMarketplace(long userId, long marketplaceId);

}
