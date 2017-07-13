package cn.edu.zju.service;

import cn.edu.zju.bean.GoodsBean;
import cn.edu.zju.bean.OrderBean;
import cn.edu.zju.bean.RankBean;
import cn.edu.zju.database.entity.CurrentStorageEntity;
import cn.edu.zju.database.entity.OrderEntity;
import cn.edu.zju.database.entity.StoreEntity;
import cn.edu.zju.database.repository.CurrentStorageRepository;
import cn.edu.zju.database.repository.OrderRepository;
import cn.edu.zju.database.repository.StoreRepository;
import cn.edu.zju.utils.OrderStatus;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CurrentStorageRepository storageRepository;

    @Autowired
    private StoreRepository storeRepository;

    public OrderEntity generateOrder(OrderBean orderBean) {
        OrderEntity entity = new OrderEntity();
        entity.setSalerId(orderBean.getStoreId());
        entity.setBuyerId(orderBean.getBuyerId());
        entity.setAddress(orderBean.getAddress());
        entity.setDate(new Date());
        entity.setStatus(OrderStatus.NOTACCEPTED);
        float price = 0.0f;
        List<GoodsBean> goods = orderBean.getGoods();
        for(GoodsBean good: goods){
            CurrentStorageEntity goodStorageEntity = storageRepository.findByTypeIdAndStoreId(good.getTypeId(), orderBean.getStoreId());
            good.setPrice(goodStorageEntity.getPrice());
            good.setDesc(null);
            good.setRank(null);
            float p = goodStorageEntity.getPrice()*good.getNumber();
            price += p;
        }
        entity.setPrice(price);
        entity.setGoods(JSON.toJSONString(goods));
        return orderRepository.save(entity);
    }

    public List<OrderEntity> getOrders(long uid, String role) {
        if(role.equalsIgnoreCase("buyer")){
            return orderRepository.findByBuyerId(uid);
        }else if(role.equalsIgnoreCase("saler")){
            StoreEntity store = storeRepository.findByUid(uid).get(0);
            return orderRepository.findBySalerId(store.getStoreId());
        }
        return null;
    }

    public OrderEntity findOrderEntityByOid(long orderId) {
        return orderRepository.findOne(orderId);
    }

    public OrderEntity rankAndSave(OrderEntity order, RankBean rankBean) {
        List<GoodsBean> goodsList = JSON.parseArray(order.getGoods(), GoodsBean.class);
        for(GoodsBean each: goodsList){
            if(each.getTypeId()==rankBean.getTypeId()){
                each.setDesc(rankBean.getDesc());
                each.setRank(rankBean.getRank());
                // 更新总评分
                CurrentStorageEntity storageEntity = storageRepository.findByTypeIdAndStoreId(each.getTypeId(),order.getSalerId());
                int oldRankedNum = storageEntity.getRankedNum();
                float rank       = storageEntity.getRank();
                int newRankedNum = each.getNumber();
                float newRank    = (oldRankedNum*rank + rankBean.getRank()*newRankedNum)/(oldRankedNum + newRankedNum);//平均分
                storageEntity.setRank(newRank);
                storageEntity.setRankedNum(oldRankedNum + newRankedNum);
                storageRepository.save(storageEntity);
                break;
            }
        }
        order.setGoods(JSON.toJSONString(goodsList));
        return orderRepository.save(order);
    }


    public OrderEntity update(OrderEntity order) {
        return orderRepository.save(order);
    }

    public void updateVolume(OrderEntity order) {
        List<GoodsBean> goodsList = JSON.parseArray(order.getGoods(), GoodsBean.class);
        List<CurrentStorageEntity> data = new ArrayList<>();
        for(GoodsBean each: goodsList){
            CurrentStorageEntity storageEntity = storageRepository.findByTypeIdAndStoreId(each.getTypeId(), order.getSalerId());
            storageEntity.setVolume(storageEntity.getVolume()+each.getNumber());
            storageEntity.setNum(storageEntity.getNum()-each.getNumber());
            data.add(storageEntity);
        }
        storageRepository.save(data);
    }
}
