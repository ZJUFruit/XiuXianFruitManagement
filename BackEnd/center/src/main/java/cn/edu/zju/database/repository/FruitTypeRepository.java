package cn.edu.zju.database.repository;

import cn.edu.zju.database.entity.FruitType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by 51499 on 2017/7/6 0006.
 */
@Repository
@Transactional
public interface FruitTypeRepository extends CrudRepository<FruitType, Long> {

    public FruitType findByTypeId(long typeId);

}
