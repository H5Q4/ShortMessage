package com.szhr.shortmessage;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.szhr.shortmessage.base.BaseActivity;
import com.szhr.shortmessage.model.Sms;

public class InputPhoneNoActivity extends BaseActivity {

    private EditText numberEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_phone_no);

        centerTv.setText(R.string.find);

        numberEt = findViewById(R.id.numberEt);
    }

    @Override
    protected void onClickBottomLeft() {
        String number = numberEt.getText().toString();

        if (TextUtils.isEmpty(number) || !TextUtils.isDigitsOnly(number)) {
            toast(getString(R.string.invalid_input));
            return;
        }

        String body = getIntent().getStringExtra(EditSmsActivity.MSG_CONTENT);
        int type = getIntent().getIntExtra(SendOptionsActivity.SEND_OPTION, 0);
        switch (type) {
            case 0:
                SmsOperations.sendMessage(this, number,body, false);
                break;
            case 1:
                Sms sms = new Sms();
                sms.content = body;
                sms.receiver = number;
                SmsOperations.saveDraft(this, sms);
                break;
            case 2:
                SmsOperations.sendMessage(this, number, body, true);
                break;
        }

        finish();

    }
}
