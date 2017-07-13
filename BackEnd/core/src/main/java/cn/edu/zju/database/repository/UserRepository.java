package cn.edu.zju.database.repository;

import cn.edu.zju.database.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@Repository
@Transactional
public interface UserRepository extends CrudRepository<UserEntity, Long>{

    public UserEntity findByUsername(String username);

    public UserEntity findByPhone(String phone);

    public UserEntity findByEmail(String email);

}
