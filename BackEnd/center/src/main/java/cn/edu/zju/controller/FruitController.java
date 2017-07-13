package cn.edu.zju.controller;

import cn.edu.zju.database.entity.Fruit;
import cn.edu.zju.database.entity.FruitType;
import cn.edu.zju.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 51499 on 2017/7/6 0006.
 */
@Controller
@RequestMapping("/fruitcenter")
public class FruitController {
    @Autowired
    private FruitService service;

    @RequestMapping("/types")
    @ResponseBody
    public List<FruitType> getAllFruitTypes(){
        Map<String, Object> result = new HashMap<>();
        List<FruitType> fruitTypes = service.findAllTypes();
        return fruitTypes;
    }

    @RequestMapping("/update/{fid}")
    @ResponseBody
    public Map<String, Object> updateFruitInfo(@PathVariable String fid,
                                               String type, String value){
        Map<String, Object> result = new HashMap<>();
        long fruitId = Long.valueOf(fid);

        Fruit fruit = service.findFruit(fruitId);
        if(fruit!=null){
            if(type.equalsIgnoreCase("salerId")){
                fruit.setSalerId(Long.valueOf(value));
            }else if(type.equalsIgnoreCase("orderId")){
                fruit.setOrderId(Long.valueOf(value));
            }
            result.put("success", true);
        }else{
            result.put("success", false);
        }

        return result;
    }
}
