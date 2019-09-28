package com.szhr.shortmessage;

import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;

public class SendOptionsActivity extends BaseListActivity {

    public static final String SMS = "sms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.write_sms));

        String[] menus = {
                getString(R.string.send_only),
                getString(R.string.save_only),
                getString(R.string.send_and_save)
        };

        setListData(menus);

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        switch (position) {
            case 0:

        }
    }
}
