package com.szhr.shortmessage;

import android.os.Bundle;
import android.widget.TextView;

import com.szhr.shortmessage.base.BaseActivity;

public class StorageStateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_state);

        setTitle(getString(R.string.storage_status));
        leftTv.setText("");

        TextView simCapacityTv = findViewById(R.id.simCapacityTv);
        TextView simInboxTv = findViewById(R.id.simInboxTv);
        TextView simSentBoxTv = findViewById(R.id.simSentBoxTv);
        TextView phoneCapacityTv = findViewById(R.id.phoneCapacityTv);
        TextView phoneInboxTv = findViewById(R.id.phoneInboxTv);
        TextView phoneSentBoxTv = findViewById(R.id.phoneSentBoxTv);




    }




}
