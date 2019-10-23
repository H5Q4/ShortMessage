package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

public class VoiceMsgOptionActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.voice_msg_box));

        String number = SharedPrefsUtils.getStringPreference(this, Constants.SMS_VOICE_MAIL_NUMBER);

        if (TextUtils.isEmpty(number)) {
            number = getString(R.string.none);
        }

        addListItem(getString(R.string.voice_msg_box_number), number);
        addListItem(getString(R.string.call_voice_msg_box));

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        Intent intent = new Intent();

        switch (position) {
            case 0:
                intent.setClass(this, EditVoiceMailNumberActivity.class);
                break;
            case 1:
                // TODO dial
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
