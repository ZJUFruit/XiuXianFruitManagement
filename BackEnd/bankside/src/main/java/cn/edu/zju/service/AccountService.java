package cn.edu.zju.service;

import cn.edu.zju.database.entity.Account;
import cn.edu.zju.database.entity.AccountRecord;
import cn.edu.zju.database.entity.FlowRecord;
import cn.edu.zju.database.repository.AccountRecordRepository;
import cn.edu.zju.database.repository.AccountRepository;
import cn.edu.zju.database.repository.FlowRecordRepository;
import cn.edu.zju.utils.CardUtil;
import cn.edu.zju.utils.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
@Service
public class AccountService {
    private Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountRecordRepository accountRecordRepository;
    @Autowired
    private FlowRecordRepository flowRecordRepository;

    //转账
    public boolean transfer(Account from, Account to, double money){
        if(null == from || null == to){
            logger.error("转账用户为空！");
            return false;
        }
        double pmoney = MoneyUtil.toPositive(money);
        double nmoney = MoneyUtil.toNegtive(money);
        AccountRecord fromRecord = new AccountRecord(from.getAccountId(), nmoney,new Date());
        AccountRecord toRecord   = new AccountRecord(to.getAccountId(), pmoney, new Date());
        FlowRecord    flowRecord = new FlowRecord(from.getAccountId(),to.getAccountId(),pmoney,new Date());
        from.transOut(pmoney);
        to.transIn(pmoney);
        //TODO:包装为一个完整的事务，使之具有原子性
        accountRepository.save(from);
        accountRepository.save(to);
        flowRecordRepository.save(flowRecord);
        accountRecordRepository.save(fromRecord);
        accountRecordRepository.save(toRecord);
        return true;
    }
    //获取账户信息
    public Account findAccount(String cardId){
        if(!CardUtil.isCardIdValid(cardId)){
            logger.error("卡号为空或不满足条件！");
            return null;
        }
        return accountRepository.findByCardNumber(cardId);
    }

}
