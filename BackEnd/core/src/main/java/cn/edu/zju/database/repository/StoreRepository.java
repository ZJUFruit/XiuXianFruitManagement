package cn.edu.zju.database.repository;

import cn.edu.zju.database.entity.StoreEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@Repository
@Transactional
public interface StoreRepository extends CrudRepository<StoreEntity, Long> {

    public List<StoreEntity> findByUid(long uid);

}
