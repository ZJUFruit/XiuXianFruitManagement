package com.example.fruit.salerapplication.testhttpapi.service.ihttp;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.example.fruit.salerapplication.testhttpapi.bean.StoreBean;

/**
 * Created by 51499 on 2017/7/10 0010.
 */
public interface FruitManagerService {

    @POST("fruitmanager/user/register")
    @FormUrlEncoded
    Call<ResponseBody> register(@Field("json") String json);


    @POST("fruitmanager/user/check/email")
    @FormUrlEncoded
    Call<ResponseBody> verifyEmail(@Field("email") String email);

    @POST("fruitmanager/user/login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("role") String role,
                             @Field("username")String username,
                             @Field("password") String password,
                             @Field("mac_id") String mac_id);


    @POST("fruitmanager/user/modify/password")
    @FormUrlEncoded
    Call<ResponseBody> modifyPassword(@Header("token_id") String token,
                                      @Field("opassword")String opassword,
                                      @Field("npassword") String npassword);


    @GET("fruitmanager/user/logout")
    Call<ResponseBody> logout(@Header("token_id") String token);

    @GET("fruitmanager/user/info")
    Call<ResponseBody> getUserInfo(@Header("token_id") String token);

    @POST("fruitmanager/user/findback/password")
    @FormUrlEncoded
    Call<ResponseBody> findBackPassword(@Field("email")String email, @Field("username")String username);

    @POST("fruitmanager/account/bind")
    @FormUrlEncoded
    Call<ResponseBody> bindBankAccount(@Header("token_id") String token,
                                 @Field("cardId")  String cardId,
                                 @Field("cardPwd") String cardPwd);

    @GET("fruitmanager/account/get")
    Call<ResponseBody> getBankAccountInfo(@Header("token_id") String token);

    @POST("fruitmanager/store/bind")
    @FormUrlEncoded
    Call<ResponseBody> bindSalerStore(@Header("token_id") String token,
                                @Field("json") String json);

    @GET("/fruitmanager/store/get")
    Call<ResponseBody> getStoreInfo(@Header("token_id") String token);

    @GET("fruitmanager/store/list")
    Call<ResponseBody> getAllStores();

    @GET("fruitmanager/store/list/{typeId}")
    Call<ResponseBody> getStoresByType(@Path("typeId")String typeId);

    @POST("fruitmanager/store/reserves/add")
    @FormUrlEncoded
    Call<ResponseBody> addStoreReverses(@Header("token_id") String token,
                                  @Field("json") String json);


    @GET("fruitmanager/store/reserves/get")
    Call<ResponseBody> getStoreReverses(@Header("token_id") String token);

    @POST("fruitmanager/store/price/update")
    @FormUrlEncoded
    Call<ResponseBody> modifyStoreGoodsPrice(@Header("token_id") String token,
                                       @Field("typeId") String typeId,
                                       @Field("nprice") String nprice);

    @POST("fruitmanager/order/create")
    @FormUrlEncoded
    Call<ResponseBody> createOrder(@Header("token_id") String token,
                             @Field("json") String json);

    @GET("fruitmanager/order/list")
    Call<ResponseBody> getOrders(@Header("token_id") String token);

    @GET("fruitmanager/order/accept/{orderId}")
    Call<ResponseBody> acceptOrder(@Header("token_id") String token,
                             @Path("orderId") String orderId);

    @GET("fruitmanager/order/packup/{orderId}")
    Call<ResponseBody> packupOrder(@Header("token_id") String token,
                             @Path("orderId") String orderId);

    @GET("fruitmanager/order/receive/{orderId}")
    Call<ResponseBody> receiveOrder(@Header("token_id") String token,
                             @Path("orderId") String orderId);

    @POST("fruitmanager/order/rank")
    @FormUrlEncoded
    Call<ResponseBody> rankOrder(@Header("token_id") String token,
                             @Field("json") String json);
}
