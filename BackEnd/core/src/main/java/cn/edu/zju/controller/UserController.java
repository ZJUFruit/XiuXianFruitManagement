package cn.edu.zju.controller;

import cn.edu.zju.bean.UserRegisterBean;
import cn.edu.zju.database.entity.UserEntity;
import cn.edu.zju.redis.RedisEmailService;
import cn.edu.zju.redis.RedisService;
import cn.edu.zju.service.UserService;
import cn.edu.zju.token.TokenModel;
import cn.edu.zju.token.TokenUtil;
import cn.edu.zju.utils.RandomPasswordUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 51499 on 2017/7/7 0007.
 */

@RestController
@RequestMapping("/fruitmanager/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisEmailService redisEmailService;

    @Autowired
    private UserService userService;

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @RequestMapping("/login")
    public Map<String, Object> login(String role,
                                     String username,
                                     String password,
                                     String mac_id){
        Map<String, Object> result = new HashMap<>();
        UserEntity user = null;
        // 检查用户名密码
        if((user = userService.isUserValid(role, username, password))==null){
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return result;
        }
        // 生成token
        String token = TokenUtil.createToken(mac_id, new Date());
        // 保存token到redis缓存
        TokenModel model = new TokenModel(token, user);
        if(!redisService.set(token,model)){
            result.put("success", false);
            result.put("message", "写入token失败");
            return result;
        }

        result.put("success", true);
        result.put("token", token);

        return result;
    }

    @RequestMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader HttpHeaders headers){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");

        if(redisService.exists(token)){
            redisService.delete(token);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);


    }

    @RequestMapping("/info")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestHeader HttpHeaders headers){
        Map<String,Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            UserEntity userEntity = userService.findUserByUid(tokenModel.getUid());
            result.put("data", userEntity);
            return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping("/register")
    public Map<String, Object> register(String json){
        Map<String, Object> result = new HashMap<>();

        UserRegisterBean bean = JSON.parseObject(json, UserRegisterBean.class);
        UserEntity entity = bean.beanToEntity();
        if(entity!=null){
            // 判断是否已经有了相应信息
            boolean valid = true;
            if(userService.isUserNameExist(entity.getUsername())){
                valid = false;
                result.put("username", false);
            }else{
                result.put("username", true);
            }
            if(userService.isPhoneExist(entity.getPhone())){
                valid = false;
                result.put("phone", false);
            }else{
                result.put("phone", true);
            }
            String email = entity.getEmail();
            if(userService.isEmailExist(email)){
                valid = false;
                result.put("email", false);
            }else{
                result.put("email", true);
            }
            if(valid){
                if(redisEmailService.exists(email)){
                    String code = redisEmailService.get(email);
                    if(code.equals(bean.getCode())){
                        //往数据库中插入新的用户
                        userService.createUser(entity);
                        result.put("success", true);
                    }else{
                        result.put("success", false);
                        result.put("code", false);
                        result.put("message", "邮箱验证码错误");
                    }
                    redisEmailService.delete(email);
                }
            }else{
                if(redisEmailService.exists(email)){
                    String code = redisEmailService.get(email);
                    if(code.equals(bean.getCode())){
                        result.put("code", true);
                    }else{
                        result.put("code", false);
                    }
                    redisEmailService.delete(email);
                }
                result.put("success", false);
                result.put("message", "您注册的用户信息已存在");
            }
        }
        return result;
    }

    @RequestMapping("/check/email")
    public ResponseEntity<Map<String, Object>> checkEmail(String email){
        Map<String, Object> result = new HashMap<>();
        String randomCode = new RandomPasswordUtil().createPassWord(4);
        try{
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(from);
            message.setTo(email);
            message.setSubject("修仙水果管家邮箱验证");
            message.setText("您的验证码为：" + randomCode);
            this.mailSender.send(mimeMessage);

            //save to cache and set out of date 10minutes
            redisEmailService.set(email, randomCode, (long)10*60);
            result.put("success", true);
            result.put("message", "请及时查收邮箱！验证码的有效期为10分钟");

            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }catch(MessagingException ex){
            result.put("success",false);
            result.put("message", "发送邮件失败");
            logger.error("发送邮件服务error", ex);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }
    }

    @RequestMapping("/findback/password")
    public ResponseEntity<Map<String, Object>> findBackPassword(String email, String username){
        Map<String, Object> result = new HashMap<>();

        String randomPassword = new RandomPasswordUtil().createPassWord(8);

        UserEntity user = userService.findUserByUsername(username);
        if(user!=null) {
            if(user.getEmail().equals(email)){
                user.setPassword(randomPassword);
                userService.updateUser(user);
            }else{
                result.put("message","用户名和邮箱不匹配");
                result.put("success", false);
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
            }
        }else{
            result.put("message","用户不存在");
            result.put("success",false);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }

        try{
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setFrom(from);
            message.setTo(email);
            message.setSubject("修仙水果管家密码找回");
            message.setText("新的密码为: "+randomPassword+"\n请及时登陆并修改密码");
            this.mailSender.send(mimeMessage);
            result.put("success", true);
            result.put("message", "新的密码已经发送到您的绑定邮箱！请使用新密码登陆并修改密码！");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }catch(MessagingException ex){
            result.put("success",false);
            result.put("message", "发送邮件失败");
            logger.error("发送邮件服务error", ex);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }
    }

    @RequestMapping("/modify/password")
    public ResponseEntity<Map<String,Object>> modifyPassword(String opassword, String npassword,
                                      @RequestHeader HttpHeaders headers){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        //先判断是否已经登陆
        if(redisService.exists(token)) {
            logger.info("get token id: " + token);
            long uid = redisService.get(token).getUid();
            UserEntity entity = userService.findUserByUid(uid);
            if(entity!=null){
                if(opassword.equals(entity.getPassword())){
                    entity.setPassword(npassword);
                    userService.updateUser(entity);
                    result.put("success", true);
                }else{
                    result.put("success", false);
                    result.put("message", "旧密码错误，修改失败！");
                }
            }else{
                // 已经失效的用户需要在缓存中清除
                redisService.delete(token);
                result.put("success", false);
                result.put("message", "用户不存在！");
            }
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }else{
            // 未登录
            logger.info("no token in the redis database");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
        }
    }

}
