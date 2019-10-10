package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

public class SettingsActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.sms_settings));

        boolean toggleReport = SharedPrefsUtils.getBooleanPreference(this,
                Constants.SMS_DELIVERY_REPORT_MODE, false);
        String reportExtra = toggleReport ? getString(R.string.open) : getString(R.string.close);

        int storageLocation = SharedPrefsUtils.getIntegerPreference(this,
                Constants.SMS_STORE_CARD, 1);
        String locationExtra = storageLocation == 1 ? getString(R.string.phone_first) :
                getString(R.string.sim_first);

        addListItem(getString(R.string.setting_option));
        addListItem(getString(R.string.status_report), reportExtra);
        addListItem(getString(R.string.storage_location), locationExtra);
        addListItem(getString(R.string.storage_status));

        setIndicatorType(INDICATOR_TYPE_CYCLE);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, SettingOptionsActivity.class));
                break;
            case 1:
                startActivityForResult(new Intent(this, ReportToggleActivity.class), position);
                break;
            case 2:
                startActivityForResult(new Intent(this, StorageLocationActivity.class), position);
                break;
            case 3:
                startActivity(new Intent(this, StorageStateActivity.class));
                break;
            default:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String extra = data.getStringExtra(ITEM_EXTRA);
            changeExtra(requestCode, extra);
        }
    }
}
