package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

public class StorageLocationActivity extends BaseListActivity {

    private String[] locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.storage_location));

        locations = new String[]{
          getString(R.string.phone_first),
                getString(R.string.sim_first)
        };

        setListData(locations);

        setIndicatorType(INDICATOR_TYPE_CYCLE);

        int storage = SharedPrefsUtils.getIntegerPreference(this,
                Constants.SMS_STORE_CARD, 1);

        listView.setSelection(storage - 1);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        SharedPrefsUtils.setIntegerPreference(this,
                Constants.SMS_STORE_CARD, position + 1);
        Intent intent = new Intent();
        intent.putExtra(ITEM_EXTRA, locations[position]);
        setResult(RESULT_OK, intent);
        finish();
    }


}
