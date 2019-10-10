package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

public class MsgTypesActivity extends BaseListActivity {


    private String[] types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        types = new String[]{
                getString(R.string.sms_type_1),
                getString(R.string.sms_type_2),
                getString(R.string.sms_type_3),
                getString(R.string.sms_type_4)
        };

        setListData(types);

        setIndicatorType(INDICATOR_TYPE_CYCLE);

        int smsType = SharedPrefsUtils.getIntegerPreference(this,
                Constants.SMS_TYPE, 3);

        listView.setSelection(smsType);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        SharedPrefsUtils.setIntegerPreference(this, Constants.SMS_TYPE, position);
        Intent intent = new Intent();
        intent.putExtra(ITEM_EXTRA, types[position]);
        setResult(RESULT_OK, intent);
        finish();
    }
}
