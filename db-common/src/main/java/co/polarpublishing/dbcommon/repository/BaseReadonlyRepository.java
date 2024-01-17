package co.polarpublishing.dbcommon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@NoRepositoryBean
@Transactional(transactionManager = "readReplicaTransactionManager", readOnly = true)
public interface BaseReadonlyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

}
