package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;

public class SettingsActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.sms_settings));

        String[] menus = {
                getString(R.string.setting_option),
                getString(R.string.status_report),
                getString(R.string.storage_location),
                getString(R.string.storage_status)
        };

        setListData(menus);

        setIndicatorType(INDICATOR_TYPE_CYCLE);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, SettingOptionsActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, StorageStateActivity.class));
                break;
            default:

                break;
        }
    }
}
