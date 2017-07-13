package cn.edu.zju.database.repository;

import cn.edu.zju.database.entity.FlowRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
@Repository
@Transactional
public interface FlowRecordRepository extends CrudRepository<FlowRecord, Long> {

    public List<FlowRecord> findByFromAccountId(long fromAccountId);

    public List<FlowRecord> findByToAccountId(long toAccountId);

}
