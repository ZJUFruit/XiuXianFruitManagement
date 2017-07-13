package cn.edu.zju.service;

import cn.edu.zju.bean.FruitReservesBean;
import cn.edu.zju.bean.ReservesBean;
import cn.edu.zju.database.entity.CurrentStorageEntity;
import cn.edu.zju.database.entity.StoreEntity;
import cn.edu.zju.database.repository.CurrentStorageRepository;
import cn.edu.zju.database.repository.StoreRepository;
import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CurrentStorageRepository currentStorageRepository;

    public StoreEntity findStoreByUid(long uid){
        List<StoreEntity> stores = storeRepository.findByUid(uid);
        if(stores!=null && stores.size()>0){
            return stores.get(0);
        }
        return null;
    }

    public StoreEntity createOrUpdateStore(StoreEntity entity){
        List<StoreEntity> stores = storeRepository.findByUid(entity.getUid());
        if(stores!=null && stores.size()>0){
            StoreEntity old = stores.get(0);
            old.setAddress(entity.getAddress());
            old.setChargeman(entity.getChargeman());
            old.setStorename(entity.getStorename());
            old.setDesc(entity.getDesc());
            old.setPhone(entity.getPhone());
            return storeRepository.save(old);
        }else{
            return storeRepository.save(entity);
        }
    }

    public List<StoreEntity> getAllStores(){
        return (List<StoreEntity>)storeRepository.findAll();
    }

    public List<CurrentStorageEntity> findAllStorageByStoreId(long storeId){
        return currentStorageRepository.findByStoreId(storeId);
    }

    // 商品入库，增加存量
    public void updateReserves(ReservesBean bean, long storeId) {
        List<FruitReservesBean> fruits = bean.getFruits();
        List<CurrentStorageEntity> data = new ArrayList<>();
        for(FruitReservesBean eachFruit: fruits){
            CurrentStorageEntity entity = currentStorageRepository.findByTypeIdAndStoreId(eachFruit.getTypeId(), storeId);
            if(entity!=null) {
                entity.setNum(entity.getNum() + eachFruit.getAmount());
            }else{
                entity = new CurrentStorageEntity(storeId, eachFruit.getTypeId());
                entity.setNum(eachFruit.getAmount());
            }
            data.add(entity);
        }
        currentStorageRepository.save(data);
    }

    public List<StoreEntity> getStoresByType(long typeId) {
        List<CurrentStorageEntity> storageEntities = currentStorageRepository.findByTypeId(typeId);
        List<StoreEntity> stores = new ArrayList<>();
        for(CurrentStorageEntity each: storageEntities){
            StoreEntity store = storeRepository.findOne(each.getStoreId());
            stores.add(store);
        }
        return stores;
    }

    public CurrentStorageEntity updateFruitPrice(long storeId,long typeId, float nprice) {
        CurrentStorageEntity storageEntity = currentStorageRepository.findByTypeIdAndStoreId(typeId, storeId);
        if(storageEntity==null){
            return null;
        }
        storageEntity.setPrice(nprice);
        return currentStorageRepository.save(storageEntity);
    }
}
