package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;

public class MainActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.voice_msg_box));

        String[] menus = {
                getString(R.string.read_sms),
                getString(R.string.write_sms),
                getString(R.string.voice_msg_box),
                getString(R.string.sms_tpl),
                getString(R.string.sms_settings),
                getString(R.string.sms_broadcasting)
        };

        setListData(menus);

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, MsgBoxesActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, EditMmsActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, VoiceMsgOptionActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, BroadcastOptionsActivity.class));
                break;
            default:
                break;
        }
    }
}
