package com.example.fruit.salerapplication.testhttpapi.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.fruit.salerapplication.testhttpapi.bean.FruitTypeBean;
import com.example.fruit.salerapplication.testhttpapi.bean.GoodsBean;
import com.example.fruit.salerapplication.testhttpapi.bean.OrderBean;
import com.example.fruit.salerapplication.testhttpapi.bean.StoreReverseBean;
import com.example.fruit.salerapplication.testhttpapi.bean.StoreBean;
import com.example.fruit.salerapplication.testhttpapi.bean.UserBean;
import com.example.fruit.salerapplication.testhttpapi.service.constants.RequestType;
import com.example.fruit.salerapplication.testhttpapi.service.ihttp.FruitCenterService;
import com.example.fruit.salerapplication.testhttpapi.service.ihttp.FruitManagerService;

/**
 * Created by 51499 on 2017/7/10 0010.
 */
public class MyHttpService {
    public String fruit_center_baseUrl;
    public String fruit_manager_baseUrl;

    private Retrofit retrofit_center;
    private FruitCenterService fruitCenterService;
    private Retrofit retrofit_manager;
    private FruitManagerService fruitManagerService;

    private Handler mHandler;

    public MyHttpService(Handler mHandler, Map<String, String> serverConfig) {
        this.mHandler = mHandler;

        fruit_center_baseUrl = "http://"+serverConfig.get("server_ip_center")+":"+serverConfig.get("server_port_center")+"/";
        fruit_manager_baseUrl = "http://"+serverConfig.get("server_ip_manager")+":"+serverConfig.get("server_port_manager")+"/";

        retrofit_center = new Retrofit.Builder()
                .baseUrl(fruit_center_baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fruitCenterService = retrofit_center.create(FruitCenterService.class);

        retrofit_manager = new Retrofit.Builder()
                .baseUrl(fruit_manager_baseUrl)
                .build();
        fruitManagerService = retrofit_manager.create(FruitManagerService.class);

    }

    /**
     * 注册新用户
     * */
    public void register(String username, String password,
                         String phone, String email,
                         String realname, String role,String code){
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);
            json.put("phone", phone);
            json.put("email",email);
            json.put("realname", realname);
            json.put("role", role);//buyer or saler
            json.put("code", code);
            Call<ResponseBody> registerCall = fruitManagerService.register(json.toString());
            registerCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody responseBody = response.body();
                    try {
                        String responseStr = responseBody.string();
                        JSONObject jsonObject = new JSONObject(responseStr);
                        Bundle bundle = new Bundle();
                        boolean success = jsonObject.getBoolean("success");
                        bundle.putBoolean("success", success);
                        if(!success){
                            bundle.putString("message", jsonObject.getString("message"));
                            bundle.putBoolean("username", jsonObject.getBoolean("username"));
                            bundle.putBoolean("phone", jsonObject.getBoolean("phone"));
                            bundle.putBoolean("email", jsonObject.getBoolean("email"));
                            bundle.putBoolean("code", jsonObject.getBoolean("code"));
                        }
                        sendMessageToUI(RequestType.USER_REGISTER, bundle);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("Register fail");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户注册时验证邮箱
     * */
    public void verifyEmail(String email){
        Call<ResponseBody> verifyCall = fruitManagerService.verifyEmail(email);
        verifyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    ResponseBody responseBody = response.body();
                    try {
                        String jsonStr = responseBody.string();
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("success",jsonObject.getBoolean("success"));
                        bundle.putString("message",jsonObject.getString("message"));
                        sendMessageToUI(RequestType.USER_REGISTER_VERIFY, bundle);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 获取当前登陆用户的账号信息
     * */
    public void getUserInfo(String token){
        Call<ResponseBody> getUserInfoCall = fruitManagerService.getUserInfo(token);
        getUserInfoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Bundle bundle = new Bundle();
                        UserBean userInfo = parseUserBeanFromJson(jsonObject.getJSONObject("data"));
                        if(userInfo!=null){
                            bundle.putSerializable("userInfo", userInfo);
                            bundle.putBoolean("success", true);
                        }else{
                            bundle.putBoolean("success", false);
                        }
                        sendMessageToUI(RequestType.USER_GET_INFO, bundle);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 用户登陆，注意指定role = [ buyer | saler ]
     * mac_id 为本机唯一的MAC地址
     * */
    public void login(String role,String username,String password,String mac_id){
        Call<ResponseBody> loginCall = fruitManagerService.login(role,username,password,mac_id);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(responseBody.string());
                    Bundle bundle = new Bundle();
                    boolean success = jsonObject.getBoolean("success");
                    bundle.putBoolean("success",success);
                    if(success) {
                        bundle.putString("token", jsonObject.getString("token"));
                    }else{
                        bundle.putString("message", jsonObject.getString("message"));
                    }
                    sendMessageToUI(RequestType.USER_LOGIN, bundle);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("login fail");
            }
        });
    }

    /**
     * 修改密码，附上登陆时获取的token
     * */
    public void modifyPassword(String token, String oldpassword ,String npassword){
        Call<ResponseBody> modifyPwdCall = fruitManagerService.modifyPassword(token,oldpassword, npassword);
        modifyPwdCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                JSONObject jsonObject = null;
                try{
                    jsonObject = new JSONObject(responseBody.string());
                    Bundle bundle = new Bundle();
                    boolean success = jsonObject.getBoolean("success");
                    bundle.putBoolean("success", success);
                    if(!success) {
                        bundle.putString("message", jsonObject.getString("message"));
                    }
                    sendMessageToUI(RequestType.USER_MODIFY_PASSWORD, bundle);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("modify password fail");
            }
        });
    }

    /**
     * 绑定银行卡
     * */
    public void bindBankAccount(String token, String cardID,String cardPwd){
        Call<ResponseBody> bindAccountCall = fruitManagerService.bindBankAccount(token,cardID,cardPwd);
        bindAccountCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    ResponseBody responseBody = response.body();
                    JSONObject jsonObject = null;
                    try{
                        jsonObject = new JSONObject(responseBody.string());
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("success", jsonObject.getBoolean("success"));

                        sendMessageToUI(RequestType.USER_BIND_BANKCARD, bundle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    //TODO:
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("bind bank card fail");
            }
        });
    }
    /**
     * 获取绑定的银行卡信息,若未绑定则success为false
     * */
    public void getBankAccountInfo(String token){
        Call<ResponseBody> getAccountCall = fruitManagerService.getBankAccountInfo(token);
        getAccountCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    ResponseBody responseBody = response.body();
                    JSONObject jsonObject = null;
                    try{
                        jsonObject = new JSONObject(responseBody.string());
                        Bundle bundle = new Bundle();
                        boolean success = jsonObject.getBoolean("success");
                        bundle.putBoolean("success", success);
                        if(success) {
                            bundle.putString("cardId", jsonObject.getString("cardId"));
                            bundle.putDouble("balance", jsonObject.getDouble("balance"));
                        }else{
                            bundle.putString("message", jsonObject.getString("message"));
                        }
                        sendMessageToUI(RequestType.USER_GET_BANKCARDINFO, bundle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 登出
     * */
    public void logout(String token){
        Call<ResponseBody> logoutCall = fruitManagerService.logout(token);
        logoutCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                if(code==200){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", true);
                    sendMessageToUI(RequestType.USER_LOGOUT, bundle);
                }else{
                    //TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("logout fail");
            }
        });
    }

    /**
     * 找回密码，给用户名username的邮箱email发送邮件（会验证是否时绑定的邮箱）
     * */
    public void findBackPassword(String email, String username){
        Call<ResponseBody> findBackPasswordCall = fruitManagerService.findBackPassword(email, username);
        findBackPasswordCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                JSONObject jsonObject = null;
                try{
                    jsonObject = new JSONObject(responseBody.string());
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", jsonObject.getBoolean("success"));
                    bundle.putString("message", jsonObject.getString("message"));

                    sendMessageToUI(RequestType.USER_FINDBACK_PASSWORD, bundle);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("find back password fail");
            }
        });
    }

    /**
     * 商家绑定商店
     * */
    public void bindSalerStore(String token, String address,String storename,
                               String chargeman,String phone,String desc){
        JSONObject json = new JSONObject();
        try{
            json.put("address", address);
            json.put("storename", storename);
            json.put("chargeman", chargeman);
            json.put("phone", phone);
            json.put("desc", desc);
            Call<ResponseBody> bindStoreCall = fruitManagerService.bindSalerStore(token, json.toString());
            bindStoreCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    int code = response.code();
                    if(code==200){
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("success", true);
                        sendMessageToUI(RequestType.SALER_BIND_STORE, bundle);
                    }else{
                        //TODO
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("bind store fail");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取绑定的商店信息
     * */
    public void getStoreInfo(String token){
        Call<ResponseBody> getStoreInfoCall = fruitManagerService.getStoreInfo(token);
        getStoreInfoCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    ResponseBody responseBody = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        Bundle bundle = new Bundle();
                        boolean success = jsonObject.getBoolean("success");
                        bundle.putBoolean("success", success);
                        if(success){
                            StoreBean store = parseSingleStoreBean(jsonObject.getJSONObject("data"));
                            bundle.putSerializable("store", store);
                        }else{
                            bundle.putString("message", jsonObject.getString("message"));
                        }
                        sendMessageToUI(RequestType.SALER_GET_STOREINFO, bundle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 买家用户获取所有的商定列表
     * */
    public void getAllStores(){
        Call<ResponseBody> getAllStoresCall = fruitManagerService.getAllStores();
        getAllStoresCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                try {
                    ArrayList<StoreBean> stores = (ArrayList<StoreBean>) parseStoreBeanFromJson(responseBody.string());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("stores", stores);
                    sendMessageToUI(RequestType.GET_ALL_STORES, bundle);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("get all store fail");
            }
        });
    }

    /**
     * 买家用户 获取有某种水果储备的商店列表
     * */
    public void getStoresByType(String typeId){
        Call<ResponseBody> getStorebyTypeCall = fruitManagerService.getStoresByType(typeId);
        getStorebyTypeCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody responseBody = response.body();
                try {
                    ArrayList<StoreBean> stores = (ArrayList<StoreBean>) parseStoreBeanFromJson(responseBody.string());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("stores", stores);
                    sendMessageToUI(RequestType.GET_STORES_BYTYPE, bundle);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("get store by type fail");
            }
        });
    }

    /**
     * 商家在登陆状态下
     * 获取商店的库存
     * */
    public void getStoreReverses(String token){
        Call<ResponseBody> getStoreReversesCall = fruitManagerService.getStoreReverses(token);
        getStoreReversesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200) {
                    ResponseBody responseBody = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        JSONArray data = jsonObject.getJSONArray("data");
                        ArrayList<StoreReverseBean> reverses = parseStoreReverseBeanFromJson(data);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("reverses", reverses);
                        sendMessageToUI(RequestType.SALER_STORE_GET_REVERSES, bundle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //TODO
            }
        });
    }

    /**
     * 商家增加库存，通过扫描NFC芯片，最后生成一个json
     * */
    public void addStoreReverses(String token, String json){//TODO:偷懒啦哈哈哈,自己生成json传过来
        Call<ResponseBody> addStoreReversesCall = fruitManagerService.addStoreReverses(token,json);
        addStoreReversesCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                if(code == 200){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", true);
                    sendMessageToUI(RequestType.SALER_STORE_ADD_REVERSES, bundle);
                }else{
                    //TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 商家修改某水果的价格
     * */
    public void modifyStoreGoodsPrice(String token, String typeId, String nprice){
        Call<ResponseBody> modifyGoodsPriceCall = fruitManagerService.modifyStoreGoodsPrice(token,typeId,nprice);
        modifyGoodsPriceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                if(code == 200){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", true);
                    sendMessageToUI(RequestType.SALER_STORE_MODIFY_PRICE, bundle);
                }else{
                    //TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 买家 创建订单
     * */
    public void createOrder(String token, String json){//TODO:json自己生成
        Call<ResponseBody> createOrderCall = fruitManagerService.createOrder(token,json);
        createOrderCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    ResponseBody responseBody = response.body();
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        JSONObject data = jsonObject.getJSONObject("data");
                        OrderBean order = parseOrderBeanFromJsonObject(data);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order", order);
                        sendMessageToUI(RequestType.BUYER_CREATE_ORDER, bundle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 买家或者卖家获取订单
     * */
    public void getOrders(String token){
        Call<ResponseBody> getOrdersCall = fruitManagerService.getOrders(token);
        getOrdersCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    ResponseBody responseBody = response.body();
                    try{
                        ArrayList<OrderBean> orders = new ArrayList<OrderBean>();
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        JSONArray datas = jsonObject.getJSONArray("data");
                        for(int i=0; i<datas.length(); i++){
                            JSONObject temp = datas.getJSONObject(i);
                            OrderBean order = parseOrderBeanFromJsonObject(temp);
                            orders.add(order);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orders",orders);
                        sendMessageToUI(RequestType.USER_GET_ORDERS, bundle);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 卖家接受订单
     * */
    public void acceptOrder(String token, String orderId){
        Call<ResponseBody> acceptOrderCall = fruitManagerService.acceptOrder(token,orderId);
        acceptOrderCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", true);
                    sendMessageToUI(RequestType.SALER_ORDERS_ACCEPT, bundle);
                }else{
                    //TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 卖家打包出货订单
     * */
    public void packupOrder(String token, String orderId){
        Call<ResponseBody> packupOrderCall = fruitManagerService.packupOrder(token, orderId);
        packupOrderCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", true);
                    sendMessageToUI(RequestType.SALER_ORDERS_PACKUP, bundle);
                }else{
                    //TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 买家收到订单
     * */
    public void receiveOrder(String token, String orderId){
        Call<ResponseBody> receiveOrderCall = fruitManagerService.receiveOrder(token, orderId);
        receiveOrderCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==200){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", true);
                    sendMessageToUI(RequestType.BUYER_ORDERS_RECEIVE, bundle);
                }else{
                    //TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 买家评价订单
     * */
    public void rankOrder(String token, String orderId,String typeId, String desc, float rank){
        JSONObject json = new JSONObject();
        try {
            json.put("orderId", orderId);
            json.put("typeId", typeId);
            json.put("rank", rank);
            json.put("desc",desc);
            Call<ResponseBody> rankOrderCall = fruitManagerService.rankOrder(token, json.toString());
            rankOrderCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code()==200){
                        ResponseBody responseBody = response.body();
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            JSONObject data = jsonObject.getJSONObject("data");
                            OrderBean order = parseOrderBeanFromJsonObject(data);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("order", order);
                            sendMessageToUI(RequestType.BUYER_ORDERS_RANK, bundle);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        //TODO
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取水果种类
     * */
    public void getFruitTypes(){
        Call<List<FruitTypeBean>> fruitTypes = fruitCenterService.getFruitTypes();
        fruitTypes.enqueue(new Callback<List<FruitTypeBean>>() {
            @Override
            public void onResponse(Call<List<FruitTypeBean>> call, Response<List<FruitTypeBean>> response) {
                ArrayList<FruitTypeBean> result = (ArrayList<FruitTypeBean>) response.body();
                Bundle bundle = new Bundle();
                bundle.putSerializable("types", result);

                sendMessageToUI(RequestType.GET_FRUIT_TYPES, bundle);
            }

            @Override
            public void onFailure(Call<List<FruitTypeBean>> call, Throwable t) {
                System.out.println("connection fail");
            }
        });
    }

    private OrderBean parseOrderBeanFromJsonObject(JSONObject object){
        try {
            long orderId = object.getLong("orderId");
            long salerId = object.getLong("salerId");
            long buyerId = object.getLong("buyerId");
            float price  = (float)object.getDouble("price");
            String address = object.getString("address");
            String status  = object.getString("status");
            Date odate = new Date(object.getLong("odate"));
            Date date = new Date(object.getLong("date"));
            String goodsStr = object.getString("goods");
            String buyerName = object.getString("buyerName");
            String storeName = object.getString("storeName");
            ArrayList<GoodsBean> goods = parseGoodsBeanFromJson(goodsStr);

            OrderBean bean = new OrderBean(orderId,salerId,storeName,buyerId,buyerName,price,goods,address,odate,status,date);
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private ArrayList<StoreReverseBean> parseStoreReverseBeanFromJson(JSONArray array){
        try{
            ArrayList<StoreReverseBean> reverses = new ArrayList<>();
            JSONArray jsonArray = array;
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject temp = jsonArray.getJSONObject(i);
                long csid = temp.getLong("csid");
                long storeId = temp.getLong("storeId");
                long typeId  = temp.getLong("typeId");
                int num = temp.getInt("num");
                Float price = (float)temp.getDouble("price");
                int volume = temp.getInt("volume");
                float rank = (float)temp.getDouble("rank");
                int rankedNum = temp.getInt("rankedNum");
                boolean isvalid = temp.getBoolean("isvalid");
                StoreReverseBean bean = new StoreReverseBean(csid,storeId,typeId,num,price,volume,rank,rankedNum,isvalid);
                reverses.add(bean);
            }
            return reverses;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<GoodsBean> parseGoodsBeanFromJson(String json){
        try {
            ArrayList<GoodsBean> goods = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length();i++){
                JSONObject temp = jsonArray.getJSONObject(i);
                long typeId = temp.getLong("typeId");
                String typeName = temp.getString("typeName");
                int number = temp.getInt("number");
                float price = (float) temp.getDouble("price");
                String desc = null;
                float rank = -1;
                if(temp.has("desc")){
                    desc = temp.getString("desc");
                }
                if(temp.has("rank")){
                    rank = (float) temp.getDouble("rank");
                }
                GoodsBean good = new GoodsBean(typeId,typeName,number,desc,price,rank);
                goods.add(good);
            }
            return goods;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<StoreBean> parseStoreBeanFromJson(String json){
        List<StoreBean> stores = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject tempObject = jsonArray.getJSONObject(i);
                StoreBean bean = parseSingleStoreBean(tempObject);
                stores.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stores;
    }

    private UserBean parseUserBeanFromJson(JSONObject tempObject){
        try{
            long uid = tempObject.getLong("uid");
            String username = tempObject.getString("username");
            String password = tempObject.getString("password");
            String phone = tempObject.getString("phone");
            String email = tempObject.getString("email");
            String realname = tempObject.getString("realname");
            String role = tempObject.getString("role");
            UserBean user = new UserBean(uid, username, password, phone, email,realname, role);
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private StoreBean parseSingleStoreBean(JSONObject tempObject){
        try {
            long storeId = tempObject.getLong("storeId");
            long uid = tempObject.getLong("uid");
            String address = tempObject.getString("address");
            String storename = tempObject.getString("storename");
            String chargeman = tempObject.getString("chargeman");
            String phone = tempObject.getString("phone");
            String description = tempObject.getString("description");
            String desc = tempObject.getString("desc");
            StoreBean bean = new StoreBean(storeId,uid,address,storename,chargeman,
                    phone,description,desc);
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void sendMessageToUI(int requestType, Bundle bundle){
        Message message = mHandler.obtainMessage(requestType);
        message.setData(bundle);
        mHandler.sendMessage(message);
    }
}
