package com.example.fruit.salerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by fruit on 2017/7/11.
 */

public class SetUserAccount extends AppCompatActivity {
    Button btnReturn, btnChangePassword, btnSave, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        initView();
        bindView();
    }

    public void initView () {
        btnReturn = (Button) findViewById(R.id.btn_header_left);
        btnLogout = (Button) findViewById(R.id.setting_account_management_logout);
        btnChangePassword = (Button) findViewById(R.id.setting_account_management_change_password);
        findViewById(R.id.btn_header_right).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.text_title)).setText("账户管理");
    }

    public void bindView () {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetUserAccount.this.finish();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetUserAccount.this, ChangePassword.class);
                SetUserAccount.this.startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
