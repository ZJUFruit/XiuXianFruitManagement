package cn.edu.zju.database.repository;

import cn.edu.zju.database.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
@Repository
@Transactional
public interface OrderRepository extends CrudRepository<OrderEntity, Long>{

    public List<OrderEntity> findByBuyerId(long buyerId);

    public List<OrderEntity> findBySalerId(long salerId);

}
