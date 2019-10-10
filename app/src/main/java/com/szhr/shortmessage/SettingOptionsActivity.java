package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

public class SettingOptionsActivity extends BaseListActivity {

    private String[] types;
    private String[] periods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.sms_settings));

        types = new String[]{
                getString(R.string.sms_type_1),
                getString(R.string.sms_type_2),
                getString(R.string.sms_type_3),
                getString(R.string.sms_type_4)
        };

        periods = new String[]{
                getString(R.string.sms_period_1),
                getString(R.string.sms_period_2),
                getString(R.string.sms_period_3),
                getString(R.string.sms_period_4),
                getString(R.string.sms_period_5),
                getString(R.string.sms_period_6)
        };

        int smsType = SharedPrefsUtils.getIntegerPreference(this,
                Constants.SMS_TYPE, 4);
        int smsPeriod = SharedPrefsUtils.getIntegerPreference(this,
                Constants.SMS_VALIDITY_PERIOD, 6);

        // TODO 获取短信中心号码: SmsManager.getSmsCenter()
        addListItem(getString(R.string.smsc), "+8613387556809");
        addListItem(getString(R.string.sms_type), types[smsType - 1]);
        addListItem(getString(R.string.validity_period), periods[smsPeriod - 1]);

        setIndicatorType(INDICATOR_TYPE_INDEX);

    }


    @Override
    protected void onClickListItem(View view, int position) {
        Intent intent = new Intent();

        switch (position) {
            case 0:
                intent.setClass(this, EditSmscActivity.class);
                break;
            case 1:
                intent.setClass(this, MsgTypesActivity.class);
                break;
            case 2:
                intent.setClass(this, SmsValidityPeriodActivity.class);
                break;
        }

        startActivityForResult(intent, position);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String extra = data.getStringExtra(ITEM_EXTRA);
            changeExtra(requestCode, extra);
        }
    }
}
