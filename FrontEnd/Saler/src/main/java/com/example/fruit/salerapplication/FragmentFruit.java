package com.example.fruit.salerapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fruit on 2017/7/9.
 */

public class FragmentFruit extends Fragment {
    ListView listView;
    ArrayList<HashMap<String, Object>> listItems = new ArrayList<HashMap<String, Object>>();
    FloatingActionButton btnAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fruit, null);

        initView(view);
        initData();
        bandView();

        return view;
    }

    public void initView (View view) {
        listView = (ListView)view.findViewById(R.id.fragment_fruit_listview);
        btnAdd = (FloatingActionButton)view.findViewById(R.id.fragment_fruit_add_btn);
    }

    public void initData () {


        for (int i = 0; i < 2; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("img", R.drawable.watermelon);
            map.put("name", "大西瓜");
            map.put("price", String.format("%.2f", 10.0));
            map.put("reserve", String.format("%d", 100));
            listItems.add(map);
        }
    }

    public void bandView () {
        final FruitAdapter fruitAdapter = new FruitAdapter(getActivity(), listItems);
        listView.setAdapter(fruitAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(getActivity(), FruitManagement.class);
                intent.putExtra("source", "modify");
                intent.putExtra("type", listItems.get(position).get("name").toString());
                intent.putExtra("price", listItems.get(position).get("price").toString());
                getActivity().startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getActivity(), FruitManagement.class);
                intent.putExtra("source", "add");
                getActivity().startActivity(intent);
            }
        });
    }

    /*
    private void showDeleteDialog () {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
        normalDialog.setTitle("修改水果");
        normalDialog.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        normalDialog.setNeutralButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        normalDialog.show();
    }
    */

    private class FruitAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<HashMap<String, Object>> listData;

        public FruitAdapter (Context context,
                             ArrayList<HashMap<String, Object>> listData) {
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
                convertView = inflater.inflate(R.layout.fragment_fruit_listitem, null);

            //(ImageView)convertView.findViewById(R.id.fragment_fruit_listitem_img).set
            ((TextView) convertView.findViewById(R.id.fragment_fruit_listitem_name)).setText(listData.get(position).get("name").toString());
            ((TextView) convertView.findViewById(R.id.fragment_fruit_listitem_price)).setText("价格：" + listData.get(position).get("price").toString());
            ((TextView) convertView.findViewById(R.id.fragment_fruit_listitem_reserve)).setText("库存：" + listData.get(position).get("reserve").toString());

            return convertView;
        }

        public void add(HashMap<String, Object> map) {
            if (listData == null)
                listData = new ArrayList<HashMap<String, Object>>();
            listData.add(map);
            notifyDataSetChanged();
        }

        public void delete (int position) {
            if (getItem(position) != null)
                listData.remove(position);
            notifyDataSetChanged();
        }
    }
}
