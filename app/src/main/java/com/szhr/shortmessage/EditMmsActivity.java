package com.szhr.shortmessage;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.EditText;

import com.szhr.shortmessage.base.BaseActivity;

public class EditMmsActivity extends BaseActivity {

    private EditText contentEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mms);

        setTitle(getString(R.string.read_sms));

        contentEt = findViewById(R.id.contentEt);
    }

    @Override
    protected void onClickBottomLeft() {
        String content = contentEt.getText().toString();

        if (TextUtils.isEmpty(content)) {
            toast(getString(R.string.empty_sms));
            return;
        }

    }


}
