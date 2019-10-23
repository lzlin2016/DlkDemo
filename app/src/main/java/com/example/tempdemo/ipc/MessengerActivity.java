package com.example.tempdemo.ipc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tempdemo.IMyAidlInterface;
import com.example.tempdemo.MainActivity;
import com.example.tempdemo.R;
import com.example.tempdemo.ipc.aidl.AIDLService;
import com.example.tempdemo.ipc.service.MessengerService;

/**
 * MessengerActivity.java
 * <p>
 * 类的描述: 多进程间的通信IPC
 * 创建时间: 2019/10/23 16:41
 * 修改备注: 参考致谢@ https://www.cnblogs.com/ldq2016/p/8417692.html
 */
public class MessengerActivity extends Activity {
    // IPC
    private boolean mBound;
    private Messenger mMessenger;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
            mBound = false;
        }
    };

    public void sayHello(View view) {
        if (!mBound) {
            return;
        }
        Message msg = Message.obtain(null, 0,0,0);
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void luanch(Context ctx) {
        ctx.startActivity(new Intent(ctx, MessengerActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }



    // aidl

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = new Intent(this, AIDLService.class);
        bindService(intent, mServiceConnection2, BIND_AUTO_CREATE);
    }

    public void aidl(View view) {
        if(mIMyAidlInterface != null){
            try {
                String name = mIMyAidlInterface.getName("I'm nick");
                Toast.makeText(this, "name = " + name, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private IMyAidlInterface mIMyAidlInterface;

    ServiceConnection mServiceConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
