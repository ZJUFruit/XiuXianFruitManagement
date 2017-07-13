package com.example.fruit.salerapplication.testhttpapi.service.ihttp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import com.example.fruit.salerapplication.testhttpapi.bean.FruitTypeBean;

/**
 * Created by 51499 on 2017/7/10 0010.
 */
public interface FruitCenterService {

    @GET("fruitcenter/types")
    Call<List<FruitTypeBean>> getFruitTypes();

}
