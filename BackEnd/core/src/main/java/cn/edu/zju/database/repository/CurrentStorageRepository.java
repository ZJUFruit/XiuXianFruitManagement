package cn.edu.zju.database.repository;

import cn.edu.zju.database.entity.CurrentStorageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
@Repository
@Transactional
public interface CurrentStorageRepository extends CrudRepository<CurrentStorageEntity, Long> {

    public CurrentStorageEntity findByTypeIdAndStoreId(long typeId, long storeId);

    public List<CurrentStorageEntity> findByTypeId(long typeId);

    public List<CurrentStorageEntity> findByStoreId(long storeId);

}
