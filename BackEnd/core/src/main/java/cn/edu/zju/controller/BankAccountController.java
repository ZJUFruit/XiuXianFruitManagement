package cn.edu.zju.controller;

import cn.edu.zju.database.entity.BankAccountEntity;
import cn.edu.zju.database.entity.UserEntity;
import cn.edu.zju.redis.RedisService;
import cn.edu.zju.service.BankAccountService;
import cn.edu.zju.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@RestController
@RequestMapping("/fruitmanager/account")
public class BankAccountController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private BankAccountService bankService;

    @Autowired
    private UserService userService;

    @RequestMapping("/bind")
    public ResponseEntity<Map<String, Object>> bindAccount(String cardId,
                                                           String cardPwd,
                                                           @RequestHeader HttpHeaders headers){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        if(redisService.exists(token)){
            long uid = redisService.get(token).getUid();
            BankAccountEntity account = new BankAccountEntity(uid,cardId,cardPwd);
            boolean flag = bankService.createOrUpdateBankAccount(account);
            result.put("success", flag);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
        }
    }
    @RequestMapping("/get")
    public ResponseEntity<Map<String,Object>> getAccountInfo(@RequestHeader HttpHeaders headers){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        if(redisService.exists(token)){
            long uid = redisService.get(token).getUid();
            BankAccountEntity entity = bankService.getBankAccount(uid);
            if(entity!=null) {
                Double balance = bankService.getBankAccountBalance(entity.getCardId());
                result.put("cardId", entity.getCardId());
                result.put("balance", balance);
                result.put("success", true);
            }else{
                result.put("success", false);
                result.put("message","还未绑定银行卡");
            }
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }else{
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
        }
    }

}
