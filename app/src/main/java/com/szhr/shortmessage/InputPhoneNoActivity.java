package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.szhr.shortmessage.base.BaseActivity;
import com.szhr.shortmessage.model.Sms;
import com.szhr.shortmessage.contact.SearchActivity;
import com.szhr.shortmessage.util.SmsOperations;

public class InputPhoneNoActivity extends BaseActivity {

    public static final int REQUEST_CODE_CONTACT = 123;
    public static final String KEY_NUMBER = "number";

    private EditText numberEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_phone_no);

        centerTv.setVisibility(View.VISIBLE);
        centerTv.setText(R.string.find);

        numberEt = findViewById(R.id.numberEt);

    }

    @Override
    protected void onClickDpadCenter() {
        startActivityForResult(new Intent(this, SearchActivity.class), REQUEST_CODE_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CONTACT && resultCode == RESULT_OK) {
            String number = data.getStringExtra(KEY_NUMBER);
            numberEt.setText(number);
        }
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
                sms.address = number;
                SmsOperations.saveDraft(this, sms);
                break;
            case 2:
                SmsOperations.sendMessage(this, number, body, true);
                break;
        }

        finish();

    }
}
