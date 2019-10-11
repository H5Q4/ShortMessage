package com.szhr.shortmessage;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;

import com.szhr.shortmessage.base.BaseListActivity;

public class MainActivity extends BaseListActivity {

    private static final int PERMISSION_REQUEST_SMS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.app_name));

        askForContactPermission();

        String[] menus = {
                getString(R.string.read_sms),
                getString(R.string.write_sms),
                getString(R.string.voice_msg_box),
                getString(R.string.sms_tpl),
                getString(R.string.sms_settings),
                getString(R.string.sms_broadcasting)
        };

        setListData(menus);

        setIndicatorType(INDICATOR_TYPE_INDEX);

    }

    @Override
    protected void onClickListItem(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, MsgBoxesActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, EditSmsActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, VoiceMsgOptionActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, BroadcastOptionsActivity.class));
                break;
            default:
                break;
        }
    }

    public void askForContactPermission () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS) ||
                        shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("SMS access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm SMS access");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_SMS,
                                                    Manifest.permission.RECEIVE_SMS,
                                                    Manifest.permission.READ_CONTACTS,
                                                    Manifest.permission.SEND_SMS}
                                    , PERMISSION_REQUEST_SMS);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    requestPermissions(
                            new String[]
                                    {Manifest.permission.READ_SMS,
                                            Manifest.permission.RECEIVE_SMS,
                                            Manifest.permission.READ_CONTACTS,
                                            Manifest.permission.SEND_SMS}
                            , PERMISSION_REQUEST_SMS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }
}
