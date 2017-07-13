package com.example.fruit.salerapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fruit on 2017/7/12.
 */

public class Order extends AppCompatActivity {
    Button btnReturn;
    ListView listView;
    ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();
        initData();
        bindView();
    }

    public void initView () {
        btnReturn = (Button) findViewById(R.id.btn_header_left);
        findViewById(R.id.btn_header_right).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.text_title)).setText("订单详情");
        listView = (ListView) findViewById(R.id.fruit_management_list);
    }

    public void initData () {
        for (int i = 0; i < 2; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", "大西瓜");
            map.put("price", String.format("%.2f", 10.0));
            listItems.add(map);
        }
    }

    public void bindView () {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order.this.finish();
            }
        });

        final OrderAdapter orderAdapter = new OrderAdapter(this, listItems);
        listView.setAdapter(orderAdapter);
    }

    private class OrderAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<HashMap<String, String>> listData;

        public OrderAdapter (Context context,
                             ArrayList<HashMap<String, String>> listData) {
            this.inflater = LayoutInflater.from(context);
            this.listData = listData;
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(R.layout.basic_listitem, null);

            ((TextView) convertView.findViewById(R.id.basic_listitem_left)).setText(listData.get(position).get("name"));
            ((TextView) convertView.findViewById(R.id.basic_listitem_right)).setText("￥" + listData.get(position).get("price"));

            return convertView;
        }
    }
}
