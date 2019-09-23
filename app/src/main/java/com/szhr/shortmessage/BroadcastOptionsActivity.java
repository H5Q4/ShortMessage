package com.szhr.shortmessage;

import android.os.Bundle;

import com.szhr.shortmessage.base.BaseListActivity;

public class BroadcastOptionsActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.sms_broadcasting));

        String[] menus = {
                getString(R.string.receive_broadcast),
                getString(R.string.channel),
                getString(R.string.language),
                getString(R.string.read)
        };

        setListData(menus);

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }
}
