package com.example.lenovo_g50_70.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LocalReceiver extends BroadcastReceiver {
    public LocalReceiver() {
        /**
         * 发送本地广播
         * 只可动态注册
         * 静态注册无效
         */
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
    }
}
