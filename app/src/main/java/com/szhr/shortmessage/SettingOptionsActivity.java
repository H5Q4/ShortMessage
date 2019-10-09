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
                getString(R.string.sms_period_6),
                getString(R.string.sms_period_7)
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        items.clear();

        int smsType = SharedPrefsUtils.getIntegerPreference(this,
                Constants.SMS_TYPE, 3);
        int smsPeriod = SharedPrefsUtils.getIntegerPreference(this,
                Constants.SMS_VALIDITY_PERIOD, 6);

        // TODO 获取短信中心号码: SmsManager.getSmsCenter()
        addListItem(getString(R.string.smsc), "+8613387556809");
        addListItem(getString(R.string.sms_type), types[smsType]);
        addListItem(getString(R.string.validity_period), periods[smsPeriod]);

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, EditSmscActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, MsgTypesActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, SmsValidityPeriodActivity.class));
                break;
        }
    }
}
