package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.szhr.shortmessage.base.BaseActivity;
import com.szhr.shortmessage.model.Sms;

public class ReadSmsActivity extends BaseActivity {

    public static final String KEY_SMS = "key_sms";

    private TextView contentTv;
    private TextView numberTv;
    private Sms sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sms);

        setTitle(getString(R.string.app_name));
        leftTv.setText(getString(R.string.options));

        contentTv = findViewById(R.id.contentTv);
        numberTv = findViewById(R.id.numberTv);

        sms = (Sms) getIntent().getSerializableExtra(KEY_SMS);
        contentTv.setText(sms.content);
        numberTv.setText(sms.address);
    }

    @Override
    protected void onClickBottomLeft() {
        Intent intent = new Intent(this, ReadSmsOptionsActivity.class);
        intent.putExtra(ReadSmsActivity.KEY_SMS, sms);
        startActivity(intent);
    }
}
