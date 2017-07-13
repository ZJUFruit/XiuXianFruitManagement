package com.example.fruit.salerapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fruit on 2017/7/9.
 */

public class FragmentOrder extends Fragment {
    private ArrayList<HashMap<String, String>> statusList = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> listUnaccepted = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> listNotdelivered = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> listFinished = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
    ListView listView;
    private boolean fold[] = new boolean[3];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order,container,false);

        initView(view);
        initData();
        bandView();

        return view;
    }

    public void initData () {
        for (int i = 0; i < fold.length; i++)
            fold[i] = false;
        HashMap<String, String> map;

        map = new HashMap<String, String>();
        map.put("title", getActivity().getString(R.string.status_unaccepted));
        statusList.add(map);
        map = new HashMap<String, String>();
        map.put("title", getActivity().getString(R.string.status_notdelivered));
        statusList.add(map);
        map = new HashMap<String, String>();
        map.put("title", getActivity().getString(R.string.status_finished));
        statusList.add(map);

        for (int i = 0; i < 3; i++) {
            map = new HashMap<String, String>();
            map.put("oid", "订单号：123456");
            map.put("buyer", "张三");
            map.put("date", "2017/07/10 12:00");
            listUnaccepted.add(map);
        }
        for (int i = 0; i < 3; i++) {
            map = new HashMap<String, String>();
            map.put("oid", "订单号：7890");
            map.put("buyer", "张三");
            map.put("date", "2017/07/10 12:00");
            listNotdelivered.add(map);
        }
        for (int i = 0; i < 10; i++) {
            map = new HashMap<String, String>();
            map.put("oid", "订单号：2333");
            map.put("buyer", "张三");
            map.put("date", "2017/07/10 12:00");
            listFinished.add(map);
        }

        listData.add(statusList.get(0));
        listData.addAll(listUnaccepted);
        listData.add(statusList.get(1));
        listData.addAll(listNotdelivered);
        listData.add(statusList.get(2));
        listData.addAll(listFinished);
    }

    public void initView (View view) {
        listView = (ListView)view.findViewById(R.id.fragment_order_listview);
    }

    public void bandView () {
        final OrderAdapter orderAdapter = new OrderAdapter(getActivity(), listData, statusList);
        listView.setAdapter(orderAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = getIndexInStringArray(statusList, listData.get(position), "title");
                if (index == -1) {
                    Intent intent;
                    intent = new Intent(getActivity(), Order.class);
                    getActivity().startActivity(intent);
                }
                else {
                    int beginIndex;
                    switch (index) {
                        case 0:
                            beginIndex = index + 1;
                            if (fold[index]) {
                                orderAdapter.addMult(beginIndex, listUnaccepted);
                            }
                            else {
                                orderAdapter.deleteMult(beginIndex, listUnaccepted.size());
                            }
                            fold[index] = !fold[index];
                            break;
                        case 1:
                            beginIndex = index + 1 + (!fold[0] ? listUnaccepted.size() : 0);
                            if (fold[index]) {
                                orderAdapter.addMult(beginIndex, listNotdelivered);
                            }
                            else {
                                orderAdapter.deleteMult(beginIndex, listNotdelivered.size());
                            }
                            fold[index] = !fold[index];
                            break;
                        case 2:
                            beginIndex = index + 1 + (!fold[0] ? listUnaccepted.size() : 0) + (!fold[1] ? listNotdelivered.size() : 0);
                            if (fold[index]) {
                                orderAdapter.addMult(beginIndex, listFinished);
                            }
                            else {
                                orderAdapter.deleteMult(beginIndex, listFinished.size());
                            }
                            fold[index] = !fold[index];
                            break;
                    }
                }
            }
        });
    }

    private int getIndexInStringArray (ArrayList<HashMap<String, String>> list, HashMap<String, String> map, String key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get(key).equals(map.get(key))) {
                return i;
            }
        }
        return -1;
    }

    private class OrderAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<HashMap<String, String>> listHead;
        private ArrayList<HashMap<String, String>> listData;

        public OrderAdapter (Context context,
                             ArrayList<HashMap<String, String>> listData,
                             ArrayList<HashMap<String, String>> listHead) {
            this.inflater = LayoutInflater.from(context);
            this.listData = listData;
            this.listHead = listHead;
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

        public void add(int index, HashMap<String, String> map) {
        }

        public void delete (int position) {
        }

        public void addMult(int beginIndex, ArrayList<HashMap<String, String>> list) {
            if (listData == null)
                listData = new ArrayList<HashMap<String, String>>();
            listData.addAll(beginIndex, list);
            notifyDataSetChanged();
        }

        public void deleteMult(int beginIndex, int count) {
            if (listData != null && listData.get(beginIndex + count - 1) != null) {
                for (int i = 0; i < count; i++) {
                    listData.remove(beginIndex);
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (listHead.contains(listData.get(position))) {
                convertView = inflater.inflate(R.layout.fragment_order_listitem_head, null);
                ((TextView) convertView.findViewById(R.id.fragment_order_listitem_head)).setText(listData.get(position).get("title"));
            }
            else {
                convertView = inflater.inflate(R.layout.fragment_order_listitem, null);
                ((TextView) convertView.findViewById(R.id.fragment_order_listitem_oid)).setText(listData.get(position).get("oid"));
                //((TextView) convertView.findViewById(R.id.fragment_order_listitem_value)).setText(listData.get(position).get("value"));
                ((TextView) convertView.findViewById(R.id.fragment_order_listitem_buyer)).setText("买家：" + listData.get(position).get("buyer"));
                ((TextView) convertView.findViewById(R.id.fragment_order_listitem_date)).setText("时间：" + listData.get(position).get("date"));
            }

            return convertView;
        }
    }
}
