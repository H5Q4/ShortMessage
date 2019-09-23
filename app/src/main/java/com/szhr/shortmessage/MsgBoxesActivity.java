package com.szhr.shortmessage;

import android.os.Bundle;

import com.szhr.shortmessage.base.BaseListActivity;

public class MsgBoxesActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.read_sms));

        String[] menus = {
                getString(R.string.inbox),
                getString(R.string.sent_box)
        };

        setListData(menus);

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }
}
