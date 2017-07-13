package cn.edu.zju.controller;

import cn.edu.zju.bean.CashFlowBean;
import cn.edu.zju.database.entity.Account;
import cn.edu.zju.service.AccountService;
import cn.edu.zju.utils.CardUtil;
import cn.edu.zju.utils.JsonUtil;
import cn.edu.zju.utils.MoneyUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
@Controller
@RequestMapping("bank/account")
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping("/transfer")
    @ResponseBody
    public Map<String, Object> createTransfer(String json){
        Map<String, Object> result = new HashMap<>();
        logger.info("transfer get json:"+json);
        if(null == json || json.length()==0){
            packupResult(result, JsonUtil.JSONINVALID, false);
            return result;
        }
        CashFlowBean bean = JSON.parseObject(json, CashFlowBean.class);
        if(CardUtil.isCardIdValid(bean.getCardFrom()) && CardUtil.isCardIdValid(bean.getCardTo())){
            if(MoneyUtil.isMoneyAmountValid(bean.getAmount())){
                Account from = accountService.findAccount(bean.getCardFrom());
                Account to   = accountService.findAccount(bean.getCardTo());
                if(accountService.transfer(from, to, bean.getAmount())){
                    packupResult(result, JsonUtil.SUCCESS, true);
                }else{
                    packupResult(result, JsonUtil.FAIL, false);
                }
            }else{
                packupResult(result, JsonUtil.MONEYAMOUNTINVALID, false);
            }
        }else{
            packupResult(result, JsonUtil.CARDIDINVALID, false);
        }
        return result;
    }
    @RequestMapping("/balance/{cardId}")
    @ResponseBody
    public Map<String, Object> getAccountInfo(@PathVariable String cardId){
        Map<String, Object> result = new HashMap<>();
        logger.info("balance get cardid:"+cardId);
        if(CardUtil.isCardIdValid(cardId)){
            Account account = accountService.findAccount(cardId);
            if(account!=null){
                result.put("success", true);
                result.put("balance", account.getBalance());
            }else{
                result.put("success", false);
                result.put("message", "账户不存在");
            }
        }else{
            result.put("success", false);
            result.put("message", "卡号不合法");
        }
        return result;
    }

    @RequestMapping("/check")
    @ResponseBody
    public Map<String, Object> checkAccount(String cardId, String cardPwd){
        Map<String,Object> result = new HashMap<>();
        logger.info("check card,ID={},pwd={}", cardId,cardPwd);
        if(CardUtil.isCardIdValid(cardId)){
            Account account = accountService.findAccount(cardId);
            if(account!=null){
                if(account.getPassword().equals(cardPwd)){
                    result.put("success", true);
                }else{
                    result.put("success", false);
                    result.put("message", "验证失败");
                }
            }else{
                result.put("success", false);
                result.put("message", "银行账号不存在");
            }
        }else{
            result.put("success", false);
            result.put("message", "卡号不合法");
        }
        return result;
    }

    public void packupResult(Map<String, Object> result, String info, boolean success){
        result.put("success", success);
        result.put("message", info);
    }

}
