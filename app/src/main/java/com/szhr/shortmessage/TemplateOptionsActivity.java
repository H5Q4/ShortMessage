package com.szhr.shortmessage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;

public class TemplateOptionsActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] options = {
                getString(R.string.add),
                getString(R.string.edit),
                getString(R.string.send),
                getString(R.string.delete),
                getString(R.string.delete_all)
        };

        setListData(options);

        setIndicatorType(INDICATOR_TYPE_CYCLE);
    }

    @Override
    protected void onClickListItem(View view, int position) {
        String templateId = getIntent().getStringExtra(TemplateActivity.KEY_TEMPLATE_ID);
        String templateBody = getIntent().getStringExtra(TemplateActivity.KEY_TEMPLATE_BODY);

        Intent intent = new Intent();

        switch (position) {
            case 0:
                intent.putExtra(EditTemplateActivity.KEY_FOR_UPDATE, false);
                intent.setClass(this, EditTemplateActivity.class);
                break;
            case 1:
                intent.putExtra(EditTemplateActivity.KEY_FOR_UPDATE, true);
                intent.putExtra(TemplateActivity.KEY_TEMPLATE_ID, templateId);
                intent.putExtra(TemplateActivity.KEY_TEMPLATE_BODY, templateBody);
                intent.setClass(this, EditTemplateActivity.class);
                break;
            case 2:
                intent.setClass(this, InputPhoneNoActivity.class);
                intent.putExtra(EditSmsActivity.MSG_CONTENT, templateBody);
                break;
            case 3:
                intent.putExtra(TemplateActivity.KEY_TEMPLATE_ID, templateId);
                intent.setClass(this, DeleteTplActivity.class);
                break;
            case 4:
                intent.setClass(this, DeleteTplActivity.class);
                intent.putExtra(DeleteTplActivity.KEY_FOR_ALL, true);
                break;
                default:break;

        }

        startActivity(intent);
    }
}
