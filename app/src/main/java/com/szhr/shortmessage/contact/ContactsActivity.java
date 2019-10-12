package com.szhr.shortmessage.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.InputPhoneNoActivity;
import com.szhr.shortmessage.R;
import com.szhr.shortmessage.base.BaseListActivity;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsActivity extends BaseListActivity {

    private static final String TAG = ContactsActivity.class.getSimpleName();
    public static final String KEY_QUERY_PARAM = "query_param";

    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String searchString = getIntent().getStringExtra(KEY_QUERY_PARAM);
        setIndicatorType(INDICATOR_TYPE_CUSTOM);

        getAsyncDialog().runAsync(new Runnable() {
            @Override
            public void run() {
                contacts = ContactOperations.queryPhoneContacts(getContentResolver(), searchString);
                contacts.addAll(ContactOperations.querySimContacts(getContentResolver(), searchString));
                Collections.sort(contacts, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return o1.getDisplayName().compareTo(o2.getDisplayName());
                    }
                });
            }
        }, new Runnable() {
            @Override
            public void run() {
                handleContacts(contacts);
            }
        }, R.string.please_wait);
    }


    private void handleContacts(List<Contact> contacts) {
        items.clear();
        this.contacts = contacts;

        for (Contact contact : contacts) {
            Map<String, String> item = new HashMap<>();
            item.put(ITEM_NAME, contact.getDisplayName());
            item.put(ITEM_EXTRA, contact.getPhoneNumber());
            items.add(item);
        }

        notifyDataSetChanged();
    }

    @Override
    protected int getItemIndicatorDrawable(int i) {
        return contacts.get(i).isFromSim() ? R.drawable.ic_sim_card : R.drawable.ic_phone;
    }

    @Override
    protected void onClickListItem(View view, int position) {
        Intent intent = new Intent();
        intent.putExtra(InputPhoneNoActivity.KEY_NUMBER, contacts.get(position).getPhoneNumber());
        setResult(RESULT_OK, intent);
        finish();
    }

}


