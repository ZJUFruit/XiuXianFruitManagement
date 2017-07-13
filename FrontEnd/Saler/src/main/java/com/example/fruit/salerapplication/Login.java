package com.example.fruit.salerapplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fruit.salerapplication.bean.SystemSettingBean;
import com.example.fruit.salerapplication.commontool.FruitDB;
import com.example.fruit.salerapplication.commontool.MacInfo;
import com.example.fruit.salerapplication.testhttpapi.bean.FruitTypeBean;
import com.example.fruit.salerapplication.testhttpapi.service.MyHttpService;
import com.example.fruit.salerapplication.testhttpapi.service.constants.RequestType;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Login extends AppCompatActivity {
    private Button btnLogin, btnForgetPassword, btnRegister;
    private MyHttpService myHttpService;
    FruitDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initView();
        bindView();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = null;
            switch (msg.what) {
//                case RequestType.GET_FRUIT_TYPES:
//                    ArrayList<FruitTypeBean> types = (ArrayList<FruitTypeBean>) msg.getData().get("types");
//                    result.setText("" + types);
//                    break;
//                case RequestType.USER_REGISTER:
//                    result.setText(""+msg.getData().getBoolean("success"));
//                    break;
//                case RequestType.USER_MODIFY_PASSWORD:
//                    result.setText(""+msg.getData().getBoolean("success"));
//                    break;
                case RequestType.USER_LOGIN:
                    if (msg.getData().getBoolean("success")) {
                        intent = new Intent(Login.this, MainActivity.class);
                        String token = msg.getData().getString("token");
                        db.insertSetting("token", token);
                        Login.this.startActivity(intent);
                        Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                        Login.this.finish();
                    }
                    else {
                        Toast.makeText(Login.this, msg.getData().getString("message"), Toast.LENGTH_SHORT).show();
                    }

                    break;
//                case RequestType.USER_LOGOUT:
//                    result.setText(""+msg.getData().getBoolean("success"));
//                    break;
//                case RequestType.GET_STORES_BYTYPE:
//                    ArrayList<StoreBean> stores = (ArrayList<StoreBean>) msg.getData().getSerializable("stores");
//                    result.setText("size="+stores.size());
//                    break;
                default:
                    Toast.makeText(Login.this, "未知请求类型", Toast.LENGTH_SHORT).show();
                    break;
                //TODO: other type of requst
            }
            super.handleMessage(msg);
        }
    };

    public void initView() {
        findViewById(R.id.btn_header_left).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_header_right).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.text_title)).setText("修鲜水果管家");
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnForgetPassword = (Button) findViewById(R.id.btn_forget_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    public void bindView() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((TextView) findViewById(R.id.input_username)).getText().toString();
                String password = ((TextView) findViewById(R.id.input_password)).getText().toString();
                String mac_id = MacInfo.getMachineHardwareAddress();

                myHttpService.login("saler", username, password, mac_id);
            }
        });
        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                Login.this.startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                Login.this.startActivity(intent);
            }
        });
    }

    private void init(){
        db = new FruitDB(getApplicationContext());
        if(checkForSetting()){
            Intent intent = new Intent(Login.this, MainActivity.class);
            Login.this.startActivity(intent);
            Login.this.finish();
        }
        initHttpConnect();
    }

    private void initHttpConnect() {
        Map<String, String> serverConfigMap = loadServerConfig("server.properties");
        if (serverConfigMap != null) {
            myHttpService = new MyHttpService(handler, serverConfigMap);
        }
    }

    private Map<String, String> loadServerConfig(String filename) {
        AssetManager am = getAssets();
        Properties props = new Properties();
        try {
            InputStream inputStream = am.open(filename);
            props.load(inputStream);
            Map<String, String> result = new HashMap<>();
            for (Object key : props.keySet()) {
                result.put((String) key, (String) props.get(key));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkForSetting(){
        List<SystemSettingBean> settings = db.querySetting();
        for(SystemSettingBean setting : settings){
            if (setting.getKey().equals("token"))
                return true;
        }
        return false;
    }
}
