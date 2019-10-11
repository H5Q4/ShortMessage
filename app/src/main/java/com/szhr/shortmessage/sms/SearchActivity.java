package com.szhr.shortmessage.sms;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.szhr.shortmessage.R;
import com.szhr.shortmessage.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    private static final int REQUEST_CODE_NUMBER = 321;
    private EditText nameEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle(getString(R.string.find));

        nameEt = findViewById(R.id.nameEt);
    }

    @Override
    protected void onClickBottomLeft() {
        String name = nameEt.getText().toString().trim();

        Intent intent = new Intent(SearchActivity.this, ContactsActivity.class);
        intent.putExtra(ContactsActivity.KEY_QUERY_PARAM, name);
        startActivityForResult(intent, REQUEST_CODE_NUMBER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_NUMBER && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
