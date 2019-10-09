package com.szhr.shortmessage;

import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

public class SmsValidityPeriodActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] periods = {
                getString(R.string.sms_period_1),
                getString(R.string.sms_period_2),
                getString(R.string.sms_period_3),
                getString(R.string.sms_period_4),
                getString(R.string.sms_period_5),
                getString(R.string.sms_period_6),
                getString(R.string.sms_period_7)
        };

        setListData(periods);

        setIndicatorType(INDICATOR_TYPE_CYCLE);

        int smsPeriod = SharedPrefsUtils.getIntegerPreference(this,
                Constants.SMS_VALIDITY_PERIOD, 6);
        listView.setSelection(smsPeriod);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        SharedPrefsUtils.setIntegerPreference(this, Constants.SMS_VALIDITY_PERIOD, position);
        finish();
    }
}
