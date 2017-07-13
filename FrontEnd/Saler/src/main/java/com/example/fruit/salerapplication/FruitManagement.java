package com.example.fruit.salerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fruit on 2017/7/12.
 */

public class FruitManagement extends AppCompatActivity {
    private Spinner spinnerType;
    private List<String> listType = new ArrayList<String>();
    Button btnReturn, btnDelete, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_management);

        initView();
        bindView();
    }

    public void initView () {
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnReturn = (Button) findViewById(R.id.btn_header_left);
        findViewById(R.id.btn_header_right).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.text_title)).setText("水果管理");

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        if (source.equals("add")) {
            btnDelete.setVisibility(View.INVISIBLE);
        }
        else {

        }

        spinnerType = (Spinner) findViewById(R.id.fruit_management_spinner_type);

        listType.add("西瓜");
        listType.add("苹果");
        listType.add("香蕉");
        listType.add("葡萄");
        listType.add("火龙果");
        listType.add("水蜜桃");

        ArrayAdapter adapterType = new ArrayAdapter<String>(this, R.layout.fruit_type_spinneritem, listType);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerType.setAdapter(adapterType);
    }

    public void bindView () {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FruitManagement.this.finish();
            }
        });
    }
}
