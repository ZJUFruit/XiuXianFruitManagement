package cn.edu.zju.controller;

import cn.edu.zju.bean.OrderBean;
import cn.edu.zju.bean.RankBean;
import cn.edu.zju.database.entity.OrderEntity;
import cn.edu.zju.database.entity.StoreEntity;
import cn.edu.zju.redis.RedisService;
import cn.edu.zju.service.BankAccountService;
import cn.edu.zju.service.OrderService;
import cn.edu.zju.service.StoreService;
import cn.edu.zju.token.TokenModel;
import cn.edu.zju.utils.AuthUtil;
import cn.edu.zju.utils.OrderStatus;
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
 * Created by 51499 on 2017/7/9 0009.
 */
@RestController
@RequestMapping("/fruitmanager/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BankAccountService bankAccountService;

    // 创建订单，操作者买家
    @RequestMapping("/create")
    public ResponseEntity<Map<String,Object>> createOrder(@RequestHeader HttpHeaders headers,
                                                          String json){
        Map<String, Object> result = new HashMap<>();
        String token  = headers.getFirst("token_id");
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            if(AuthUtil.isBuyer(tokenModel)){
                OrderBean orderBean = JSON.parseObject(json, OrderBean.class);
                if(tokenModel.getUid()==orderBean.getBuyerId()){
                    OrderEntity order = orderService.generateOrder(orderBean);//TODO:判断订单能否成功生成(判断库存是不是够)
                    result.put("data", order);
                    return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
                }
                result.put("message", "买家ID和账户Id不对应");
                return new ResponseEntity<Map<String, Object>>(result,HttpStatus.FORBIDDEN);
            }
            result.put("message", "不是买家用户！禁止的操作");
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    // 获取订单信息，操作者买家or卖家
    @RequestMapping("/list")
    public ResponseEntity<Map<String,Object>> getOrders(@RequestHeader HttpHeaders headers){
        Map<String, Object> result = new HashMap<>();
        String token  = headers.getFirst("token_id");
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);

            List<OrderEntity> orders = orderService.getOrders(tokenModel.getUid(), tokenModel.getRole());
            result.put("data", orders);
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    // 评价订单，操作者买家
    @RequestMapping("/rank")
    public ResponseEntity<Map<String,Object>> rankOrder(@RequestHeader HttpHeaders headers,
                          String json){
        Map<String, Object> result = new HashMap<>();
        String token  = headers.getFirst("token_id");
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            if(AuthUtil.isBuyer(tokenModel)){
                RankBean rankBean = JSON.parseObject(json, RankBean.class);
                OrderEntity order = orderService.findOrderEntityByOid(rankBean.getOrderId());
                OrderEntity data = orderService.rankAndSave(order, rankBean);
                result.put("data", data);
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
            }
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    // 状态更新为已接单，操作者卖家
    @RequestMapping("/accept/{orderId}")
    public ResponseEntity<Map<String, Object>> acceptOrder(@RequestHeader HttpHeaders headers,
                                                           @PathVariable String orderId){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        long oid = Long.valueOf(orderId);
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            if(AuthUtil.isSaler(tokenModel)){
                OrderEntity order = orderService.findOrderEntityByOid(oid);
                StoreEntity store = storeService.findStoreByUid(tokenModel.getUid());
                if(store!=null && store.getStoreId()==order.getSalerId()
                        && order.getStatus() == OrderStatus.NOTACCEPTED){
                    order.setStatus(OrderStatus.ACCEPTED_NOTPACKED);
                    orderService.update(order);
                    bankAccountService.transfer(order.getBuyerId(), store.getUid(), order.getPrice());
                    return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
                }
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }
    // 状态更新为已出库，操作者卖家
    @RequestMapping("/packup/{orderId}")
    public ResponseEntity<Map<String, Object>> packupOrder(@RequestHeader HttpHeaders headers,
                                                           @PathVariable String orderId){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        long oid = Long.valueOf(orderId);
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            if(AuthUtil.isSaler(tokenModel)){
                OrderEntity order = orderService.findOrderEntityByOid(oid);
                StoreEntity store = storeService.findStoreByUid(tokenModel.getUid());
                if(store!=null && store.getStoreId()==order.getSalerId()
                        && order.getStatus() == OrderStatus.ACCEPTED_NOTPACKED){
                    order.setStatus(OrderStatus.ACCEPTED_PACKED_NOTRECEIVED);
                    orderService.update(order);
                    orderService.updateVolume(order);
                    return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
                }
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }
    // 状态更新为已收货, 操作者买家
    @RequestMapping("/receive/{orderId}")
    public ResponseEntity<Map<String, Object>> receiveOrder(@RequestHeader HttpHeaders headers,
                                                           @PathVariable String orderId){
        Map<String, Object> result = new HashMap<>();
        String token = headers.getFirst("token_id");
        long oid = Long.valueOf(orderId);
        if(redisService.exists(token)){
            TokenModel tokenModel = redisService.get(token);
            if(AuthUtil.isBuyer(tokenModel)){
                OrderEntity order = orderService.findOrderEntityByOid(oid);
                if(tokenModel.getUid()==order.getBuyerId()
                        && order.getStatus() == OrderStatus.ACCEPTED_PACKED_NOTRECEIVED){
                    order.setStatus(OrderStatus.ACCEPTED_PACKED_RECEIVED);
                    orderService.update(order);
                    return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
                }
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

}
