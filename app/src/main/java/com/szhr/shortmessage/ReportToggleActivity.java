package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

public class ReportToggleActivity extends BaseListActivity {

    private String[] menus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menus = new String[]{
                getString(R.string.open),
                getString(R.string.close)
        };

        setListData(menus);

        setIndicatorType(INDICATOR_TYPE_CYCLE);

        boolean toggle = SharedPrefsUtils.getBooleanPreference(this,
                Constants.SMS_DELIVERY_REPORT_MODE, false);

        int selection = toggle ? 0 : 1;
        listView.setSelection(selection);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        SharedPrefsUtils.setBooleanPreference(this,
                Constants.SMS_DELIVERY_REPORT_MODE, position == 0);
        Intent intent = new Intent();
        intent.putExtra(ITEM_EXTRA, menus[position]);
        setResult(RESULT_OK, intent);
        finish();
    }
}
