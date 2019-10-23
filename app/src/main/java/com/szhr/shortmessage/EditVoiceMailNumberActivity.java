package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.szhr.shortmessage.base.BaseActivity;
import com.szhr.shortmessage.util.Constants;
import com.szhr.shortmessage.util.SharedPrefsUtils;

import static com.szhr.shortmessage.base.BaseListActivity.ITEM_EXTRA;

public class EditVoiceMailNumberActivity extends BaseActivity {

    private EditText numberEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_voice_mail_number);
        numberEt = (EditText) findViewById(R.id.numberEt);

        String number = SharedPrefsUtils.getStringPreference(this, Constants.SMS_VOICE_MAIL_NUMBER);

        if (!TextUtils.isEmpty(number)) {
            numberEt.setText(number);
            numberEt.setSelection(number.length());
        }
    }

    @Override
    protected void onClickBottomLeft() {
        final String number = numberEt.getText().toString().trim();
        if (TextUtils.isEmpty(number) || !TextUtils.isDigitsOnly(number)) {
            toast(getString(R.string.invalid_input));
            return;
        }

        SharedPrefsUtils.setStringPreference(this, Constants.SMS_VOICE_MAIL_NUMBER, number);

        Intent data = new Intent();
        data.putExtra(ITEM_EXTRA, number);
        setResult(RESULT_OK, data);
        finish();
    }
}
