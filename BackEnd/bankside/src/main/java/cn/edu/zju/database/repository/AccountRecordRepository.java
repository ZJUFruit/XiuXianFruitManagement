package cn.edu.zju.database.repository;

import cn.edu.zju.database.entity.AccountRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
@Repository
@Transactional
public interface AccountRecordRepository extends CrudRepository<AccountRecord, Long> {

    public List<AccountRecord> findByAccountId(long accountId);

}
