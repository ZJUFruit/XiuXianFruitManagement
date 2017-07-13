package cn.edu.zju.database.repository;

import cn.edu.zju.database.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
@Repository
@Transactional
public interface AccountRepository extends CrudRepository<Account, Long> {

    public Account findByCardNumber(String cardNumber);

}
