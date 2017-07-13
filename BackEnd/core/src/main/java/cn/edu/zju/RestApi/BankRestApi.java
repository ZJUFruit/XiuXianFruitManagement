package cn.edu.zju.RestApi;

import cn.edu.zju.database.entity.BankAccountEntity;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@Service
public class BankRestApi {
    Logger logger = LoggerFactory.getLogger(BankRestApi.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${micropay.bank.host}")
    private String server;

    private static final String BALANCE_URI = "/balance/";
    private static final String CHECKVALID_URI = "/check";
    private static final String TRANSFER_URI  = "/transfer";

    // 完成转账
    public boolean transfer(BankAccountEntity from, BankAccountEntity to, double amount){
        String url = server + TRANSFER_URI;
        MultiValueMap<String,Object> postData = new LinkedMultiValueMap<>();
        JSONObject data = new JSONObject();
        data.put("cardFrom", from.getCardId());
        data.put("cardTo", to.getCardId());
        data.put("amount", amount);
        data.put("time", new Date());
        postData.add("json", data.toJSONString());
        JSONObject json = restTemplate.postForEntity(url,postData,JSONObject.class).getBody();
        boolean success = json.getBoolean("success");
        if(success){
            return true;
        }else {
            logger.error("Rest请求失败！"+json.getString("message"));
            return false;
        }
    }

    // 查询账户余额
    //TODO:具体的原因应该可以显示在客户端上
    public Double getAccountBalance(String account){
        String url = server+BALANCE_URI+account;
        JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();
        boolean success = json.getBoolean("success");
        if(success){
            return json.getDouble("balance");
        }else{
            logger.error("Rest请求失败！"+json.getString("message"));
            return null;
        }
    }
    // 查询账户是否合法
    //TODO:具体的原因应该可以显示在客户端上
    public boolean checkAccount(BankAccountEntity accountEntity){
        String url = server + CHECKVALID_URI;
        MultiValueMap<String,Object> postData = new LinkedMultiValueMap<>();
        postData.add("cardId", accountEntity.getCardId());
        postData.add("cardPwd", accountEntity.getCardPwd());
        JSONObject json = restTemplate.postForEntity(url,postData,JSONObject.class).getBody();
        boolean success = json.getBoolean("success");
        if(success){
            return true;
        }else {
            logger.error("Rest请求失败！"+json.getString("message"));
            return false;
        }
    }
}
