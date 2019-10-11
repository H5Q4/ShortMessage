package com.szhr.shortmessage;

import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;

public class LanguageSelectionActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.language));

        addListItem(getString(R.string.all_langs));

        setIndicatorType(INDICATOR_TYPE_CYCLE);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        finish();
    }
}
