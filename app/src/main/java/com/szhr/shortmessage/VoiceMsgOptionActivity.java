package com.szhr.shortmessage;

import android.os.Bundle;

import com.szhr.shortmessage.base.BaseListActivity;

public class VoiceMsgOptionActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.voice_msg_box));

        String[] menus = {
                getString(R.string.voice_msg_box_number),
                getString(R.string.call_voice_msg_box)
        };

        setListData(menus);

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }
}
