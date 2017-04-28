package com.example.lenovo_g50_70.broadcastreceiver;
/*
    接收系统广播，只需创建广播接收者，并注册该广播的接收事件
    发送自定义广播时，上述+Intent，为Intent设置广播行为
    发送本地广播，上述+LocalBroadcastManager
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private ImageView imageView;
    private IntentFilter networkFilter;
    private IntentFilter localFilter;
    //动态注册需要创建广播接收者实例
    private NetworkChangeReceiver networkChangeReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localFilter =new IntentFilter();
        localFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
        localReceiver =new LocalReceiver();
        //获取LocalBroadcastReceiver实例
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
        //本地广播必须动态注册，静态注册无效
        localBroadcastManager.registerReceiver(localReceiver,localFilter);
        networkFilter =new IntentFilter();
        //为广播接收者设置监听网络变化的行为
        networkFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver =new NetworkChangeReceiver();
        //动态注册广播接收者
        registerReceiver(networkChangeReceiver,networkFilter);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                //该行为和注册文件的行为一致
                intent.setAction("com.example.broadcasttest.MYBROADCAST");
                //标准广播，无序
                sendBroadcast(intent);
            }
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                //该行为和注册文件的行为一致
                intent.setAction("com.example.broadcasttest.MYBROADCAST");
                //有序广播,第二个参数是与权限相关的字符串
                sendOrderedBroadcast(intent,null);
            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                //该行为和注册文件的行为一致
                intent.setAction("com.example.broadcasttest.LOCAL_BROADCAST");
                //发送异步广播
                localBroadcastManager.sendBroadcast(intent);
                //发送同步广播
                //localBroadcastManager.sendBroadcastSync(intent);
            }
        });
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.image10);
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setImageDrawable(new CircleImageDrawable(bitmap));
    }

    class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
            //通过系统服务类查看网络状态
            ConnectivityManager connectivityManager=
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null && networkInfo.isAvailable()){
                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //动态注册的广播接收器一定都要取消注册
        localBroadcastManager.unregisterReceiver(localReceiver);
        unregisterReceiver(networkChangeReceiver);
    }
}
