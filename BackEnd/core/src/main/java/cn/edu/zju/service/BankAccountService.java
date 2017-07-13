package cn.edu.zju.service;

import cn.edu.zju.RestApi.BankRestApi;
import cn.edu.zju.database.entity.BankAccountEntity;
import cn.edu.zju.database.repository.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@Service
public class BankAccountService {
    Logger logger = LoggerFactory.getLogger(BankAccountService.class);
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankRestApi bankRestApi;


    public BankAccountEntity getBankAccount(long uid){
        List<BankAccountEntity> accountEntities = bankAccountRepository.findByUid(uid);
        if(null!=accountEntities && accountEntities.size()>0){
            return accountEntities.get(0);
        }
        return null;
    }

    public Double getBankAccountBalance(String account){
        return bankRestApi.getAccountBalance(account);
    }

    public boolean transfer(long fromId, long toId, double amount){
        List<BankAccountEntity> fromAccounts = bankAccountRepository.findByUid(fromId);
        List<BankAccountEntity> toAccounts = bankAccountRepository.findByUid(toId);
        if(fromAccounts!=null && fromAccounts.size()>0 &&
                toAccounts!=null && toAccounts.size()>0) {
            BankAccountEntity fromAccount = bankAccountRepository.findByUid(fromId).get(0);
            BankAccountEntity toAccount = bankAccountRepository.findByUid(toId).get(0);
            return bankRestApi.transfer(fromAccount, toAccount, amount);
        }
        return false;
    }

    public boolean createOrUpdateBankAccount(BankAccountEntity account) {
        if(null!=account){
            if(!bankRestApi.checkAccount(account)){
                logger.info("账户不合法，银行端验证失败" + account.getCardId());
                return false;
            }
        }
        //先删除原有的银行卡号，再进行插入
        List<BankAccountEntity> accounts = bankAccountRepository.findByUid(account.getUid());
        if(accounts!=null && accounts.size()>0){
            bankAccountRepository.delete(accounts.get(0).getBaid());
        }
        if(null!=(bankAccountRepository.save(account))){
            return true;
        }
        logger.error("创建银行账户失败，绑定失败");
        return false;
    }
}
