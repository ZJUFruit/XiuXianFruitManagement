package cn.edu.zju.controller;

import cn.edu.zju.bean.ReservesBean;
import cn.edu.zju.bean.StoreBean;
import cn.edu.zju.database.entity.CurrentStorageEntity;
import cn.edu.zju.database.entity.StoreEntity;
import cn.edu.zju.redis.RedisService;
import cn.edu.zju.service.StoreService;
import cn.edu.zju.token.TokenModel;
import cn.edu.zju.utils.AuthUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@RestController
@RequestMapping("/fruitmanager/store")
public class SalerStoreController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private StoreService storeService;

    @RequestMapping("/price/update")
    public ResponseEntity<Map<String,Object>> updatePrice(@RequestHeader HttpHeaders headers,
                                                          String typeId,
                                                          String nprice){
        Map<String, Object> result = new HashMap<>();
        String token  = headers.getFirst("token_id");
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            if(AuthUtil.isSaler(tokenModel)){
                StoreEntity storeEntity = storeService.findStoreByUid(tokenModel.getUid());
                storeService.updateFruitPrice(storeEntity.getStoreId(), Long.valueOf(typeId), Float.valueOf(nprice));
                return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
            }
            result.put("message", "不是商家用户！禁止的操作");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    //增加商店库存
    @RequestMapping("/reserves/add")
    public ResponseEntity<Map<String,Object>> addReverses(@RequestHeader HttpHeaders headers,
                                                        String json){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            if(AuthUtil.isSaler(tokenModel)){
                ReservesBean bean = JSON.parseObject(json, ReservesBean.class);
                StoreEntity storeEntity = storeService.findStoreByUid(tokenModel.getUid());
                if(storeEntity.getStoreId() == bean.getStoreId()){//判断商店id是否匹配
                    storeService.updateReserves(bean, storeEntity.getStoreId());
                    return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
                }
                result.put("message", "指定的商店Id和绑定的Id不一致");
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
            }
            result.put("message", "不是商家用户！禁止的操作");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping("/reserves/get")
    public ResponseEntity<Map<String,Object>>  getReverses(@RequestHeader HttpHeaders headers){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        if(redisService.exists(token)){
            TokenModel model = redisService.get(token);
            if(AuthUtil.isSaler(model)){
                StoreEntity storeEntity = storeService.findStoreByUid(model.getUid());
                List<CurrentStorageEntity> storages = null;
                if(storeEntity!=null){
                    storages = storeService.findAllStorageByStoreId(storeEntity.getStoreId());
                }
                result.put("data", storages);
                return new ResponseEntity<Map<String, Object>>(result,HttpStatus.OK);
            }
            result.put("message", "不是商家用户！禁止的操作");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }


    //商家用户增加一个商铺（现实中的店）
    @RequestMapping("/bind")
    public ResponseEntity<Map<String, Object>> bindStore(@RequestHeader HttpHeaders headers,
                                                         String json){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            if(AuthUtil.isSaler(tokenModel)){
                StoreBean bean = JSON.parseObject(json, StoreBean.class);
                StoreEntity entity = bean.beanToEntity();
                entity.setUid(tokenModel.getUid());
                storeService.createOrUpdateStore(entity);
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
            }
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }
    @RequestMapping("/get")
    public ResponseEntity<Map<String, Object>> getStoreInfo(@RequestHeader HttpHeaders headers){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            if(AuthUtil.isSaler(tokenModel)){
                StoreEntity store = storeService.findStoreByUid(tokenModel.getUid());
                if(store != null){
                    result.put("success", true);
                    result.put("data", store);
                }else{
                    result.put("success", false);
                    result.put("message", "未绑定商店");
                }
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
            }
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    //查询所有的店,若有typeid根据水果类别查找
    @RequestMapping("/list")
    public ResponseEntity<List<StoreEntity>> getStores(){
        List<StoreEntity> data = getStoreEntity(-1);
        return new ResponseEntity<List<StoreEntity>>(data, HttpStatus.OK);
    }

    @RequestMapping("/list/{typeId}")
    public ResponseEntity<List<StoreEntity>> getStoresByFruitType(@PathVariable  String typeId){
        List<StoreEntity> data = getStoreEntity(Long.valueOf(typeId));
        return new ResponseEntity<List<StoreEntity>>(data, HttpStatus.OK);
    }

    private List<StoreEntity> getStoreEntity(long typeId){
        if(typeId == -1){
            return storeService.getAllStores();
        }else{
            return storeService.getStoresByType(typeId);
        }
    }

}
