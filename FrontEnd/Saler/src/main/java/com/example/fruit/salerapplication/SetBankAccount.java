package com.example.fruit.salerapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by fruit on 2017/7/11.
 */

public class SetBankAccount extends AppCompatActivity {
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);

        initView();
        bindView();
    }

    public void initView () {
        btnReturn = (Button) findViewById(R.id.btn_header_left);
        findViewById(R.id.btn_header_right).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.text_title)).setText("绑定银行卡");
    }

    public void bindView () {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetBankAccount.this.finish();
            }
        });
    }
}
