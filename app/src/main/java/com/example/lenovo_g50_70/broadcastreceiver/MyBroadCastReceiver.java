package com.example.lenovo_g50_70.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadCastReceiver extends BroadcastReceiver {
    public MyBroadCastReceiver() {
        //自定义广播
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"received in MyBroadCastReceiver",Toast.LENGTH_SHORT).show();
        //广播拦截，后面的广播不再收到这条广播
        abortBroadcast();
    }
}
