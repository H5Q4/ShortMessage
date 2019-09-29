package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;

public class SendOptionsActivity extends BaseListActivity {

    public static final String SEND_OPTION = "send_option";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.write_sms));

        String[] menus = {
                getString(R.string.send_only),
                getString(R.string.save_only),
                getString(R.string.send_and_save)
        };

        setListData(menus);

        setIndicatorType(INDICATOR_TYPE_INDEX);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        String msgBody = getIntent().getStringExtra(EditMmsActivity.MSG_CONTENT);
        Intent intent = new Intent(this, InputPhoneNoActivity.class);
        intent.putExtra(EditMmsActivity.MSG_CONTENT, msgBody);
        intent.putExtra(SEND_OPTION, position);

        startActivity(intent);

        finish();
    }
}
