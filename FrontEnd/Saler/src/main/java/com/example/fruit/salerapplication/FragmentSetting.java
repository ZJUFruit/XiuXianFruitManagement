package com.example.fruit.salerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fruit on 2017/7/9.
 */

public class FragmentSetting extends Fragment {
    private View viewStoreInfo, viewBankAccount, viewAccountManagement;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);

        initView(view);
        bindView();

        return view;
    }

    public void initView (View view) {
        viewStoreInfo = view.findViewById(R.id.setting_store_info_view);
        viewBankAccount = view.findViewById(R.id.setting_bank_account_view);
        viewAccountManagement = view.findViewById(R.id.setting_account_management_view);
    }

    public void bindView () {
        viewStoreInfo.setOnClickListener(new SettingClickListener());
        viewBankAccount.setOnClickListener(new SettingClickListener());
        viewAccountManagement.setOnClickListener(new SettingClickListener());
    }

    private class SettingClickListener implements View.OnClickListener {
        Intent intent;
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_store_info_view:
                    intent = new Intent(getActivity(), SetStoreInfo.class);
                    getActivity().startActivity(intent);
                    break;
                case R.id.setting_bank_account_view:
                    intent = new Intent(getActivity(), SetBankAccount.class);
                    getActivity().startActivity(intent);
                    break;
                case R.id.setting_account_management_view:
                    intent = new Intent(getActivity(), SetUserAccount.class);
                    getActivity().startActivity(intent);
                    break;
            }
        }
    }
}
