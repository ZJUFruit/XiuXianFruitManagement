package cn.edu.zju.service;

import cn.edu.zju.database.entity.Fruit;
import cn.edu.zju.database.entity.FruitType;
import cn.edu.zju.database.repository.FruitRepository;
import cn.edu.zju.database.repository.FruitTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 51499 on 2017/7/6 0006.
 */
@Service
public class FruitService {
    @Autowired
    private FruitRepository fruitRepository;
    @Autowired
    private FruitTypeRepository typeRepository;

    public Fruit updateFruit(Fruit fruit){
        return fruitRepository.save(fruit);
    }

    public Fruit findFruit(long fid){
        return fruitRepository.findByUuid(fid);
    }

    public FruitType findFruitType(long tid){
        return typeRepository.findByTypeId(tid);
    }

    public List<FruitType> findAllTypes(){
        return (List<FruitType>)typeRepository.findAll();
    }

    public Fruit createFruit(Fruit fruit){
        return fruitRepository.save(fruit);
    }

    public FruitType createFruitType(FruitType type){
        return typeRepository.save(type);
    }

}
