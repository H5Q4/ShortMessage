package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

public class BroadcastToggleActivity extends BaseListActivity {

    private String[] options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.receive_broadcast));

        options = new String[]{
                getString(R.string.open),
                getString(R.string.close)
        };

        boolean toggle = SharedPrefsUtils.getBooleanPreference(this,
                Constants.SMS_BROADCAST_MODE, false);

        setListData(options);

        setIndicatorType(INDICATOR_TYPE_CYCLE);

        listView.setSelection(toggle ? 0 : 1);

    }

    @Override
    protected void onClickListItem(View view, int position) {
        SharedPrefsUtils.setBooleanPreference(this,
                Constants.SMS_BROADCAST_MODE, position == 0);
        Intent intent = new Intent();
        intent.putExtra(ITEM_EXTRA, options[position]);
        setResult(RESULT_OK, intent);
        finish();
    }
}
