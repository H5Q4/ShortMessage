package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

public class BroadcastOptionsActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.sms_broadcasting));

        boolean receiveBc = SharedPrefsUtils.getBooleanPreference(this,
                Constants.SMS_BROADCAST_MODE, false);

        addListItem(getString(R.string.receive_broadcast), receiveBc ? getString(R.string.open) :
                getString(R.string.close));
        addListItem(getString(R.string.channel));
        addListItem(getString(R.string.language), getString(R.string.all_langs));
        addListItem(getString(R.string.read));

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        switch (position) {
            case 0:
                startActivityForResult(new Intent(this,
                        BroadcastToggleActivity.class), position);
                break;
            case 1:
                break;
            case 2:
                startActivity(new Intent(this, LanguageSelectionActivity.class));
                break;
            case 3:
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
