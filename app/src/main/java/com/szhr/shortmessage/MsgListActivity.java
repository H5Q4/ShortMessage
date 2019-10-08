package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.model.Sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgListActivity extends BaseListActivity {
    public static final String KEY_TYPE = "type";

    public static final int INBOX = 1;
    public static final int OUTBOX = 2;
    public static final int TEMPLATE = 3;
    private List<Sms> smsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int type = getIntent().getIntExtra(KEY_TYPE, 1);

        switch (type) {
            case INBOX:
                setTitle(getString(R.string.inbox));
                break;
            case OUTBOX:
                setTitle(getString(R.string.sent_box));
                List<Sms> sms = SmsOperations.getSms(this, SmsOperations.Folder.OUTBOX);
                handleSms(sms);
                break;
            case TEMPLATE:
                break;
        }

        setIndicatorType(INDICATOR_TYPE_CUSTOM);

    }

    private void handleSms(List<Sms> smsList) {
        items.clear();
        this.smsList = smsList;

        for (Sms sms : smsList) {
            Map<String, String> item = new HashMap<>();
            item.put(ITEM_NAME, sms.sender);
            items.add(item);
        }

        notifyDataSetChanged();
    }

    @Override
    protected int getItemIndicatorDrawable(int i) {
        return this.smsList.get(i).type == 3 ? R.drawable.ic_sms_not_sent : R.drawable.ic_sms_sent;
    }

    @Override
    protected void onClickListItem(View view, int position) {
        Intent intent = new Intent(this, ReadSmsActivity.class);
        intent.putExtra(ReadSmsActivity.KEY_SMS, smsList.get(position));
        startActivity(intent);
    }
}
