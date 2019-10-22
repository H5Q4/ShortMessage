package com.szhr.shortmessage;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;
import com.szhr.shortmessage.util.TemplatesProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateActivity extends BaseListActivity {
    public static final String KEY_TEMPLATE_ID = "template_id";
    public static final String KEY_TEMPLATE_BODY = "template_body";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.sms_tpl));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAsyncDialog().runAsync(new Runnable() {
            @Override
            public void run() {
                Cursor cur = getContentResolver().query(TemplatesProvider.Template.CONTENT_URI,
                        null, null, null, null);
                if (cur != null) {
                    items.clear();
                    while(cur.moveToNext()) {
                        String body = cur.getString(cur.getColumnIndex(TemplatesProvider.Template.TEXT));
                        int id = cur.getInt(cur.getColumnIndex(TemplatesProvider.Template._ID));
                        Map<String, String> item = new HashMap<>();
                        item.put(ITEM_NAME, body);
                        item.put(KEY_TEMPLATE_ID, String.valueOf(id));
                        items.add(item);
                    }

                    cur.close();
                }
            }
        }, new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        }, R.string.please_wait);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        Intent intent = new Intent(this, TemplateOptionsActivity.class);
        intent.putExtra(KEY_TEMPLATE_ID, items.get(position).get(KEY_TEMPLATE_ID));
        intent.putExtra(KEY_TEMPLATE_BODY, items.get(position).get(ITEM_NAME));
        startActivity(intent);
    }
}
