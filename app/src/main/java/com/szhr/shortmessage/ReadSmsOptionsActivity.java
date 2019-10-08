package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.model.Sms;

import java.io.Serializable;

public class ReadSmsOptionsActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] options = {
                getString(R.string.delete),
                getString(R.string.forward),
                getString(R.string.edit),
                getString(R.string.delete_all)
        };

        setListData(options);

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        Intent intent = new Intent();
        Sms sms = (Sms) getIntent().getSerializableExtra(ReadSmsActivity.KEY_SMS);

        switch (position) {
            case 0:
                intent.putExtra(ReadSmsActivity.KEY_SMS, sms);
                intent.setClass(this, DeleteSmsActivity.class);
                break;
            case 1:
                intent.putExtra(EditSmsActivity.MSG_CONTENT, sms.content);
                intent.setClass(this, InputPhoneNoActivity.class);
                break;
            case 2:
                intent.putExtra(EditSmsActivity.MSG_CONTENT, sms.content);
                intent.setClass(this, EditSmsActivity.class);
                break;
            case 3:
                intent.putExtra(DeleteSmsActivity.KEY_FOR_ALL, true);
                intent.setClass(this, DeleteSmsActivity.class);
                break;
        }

        startActivity(intent);

        finish();
    }
}
