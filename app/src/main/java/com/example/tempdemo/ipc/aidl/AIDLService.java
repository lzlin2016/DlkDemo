package com.example.tempdemo.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.tempdemo.IMyAidlInterface;

public class AIDLService extends Service {
    IMyAidlInterface.Stub mStub = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean,
                               float aFloat, double aDouble, String aString) throws RemoteException {
        }

        @Override
        public String getName(String nickName) throws RemoteException {
            return "aidl " + nickName;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mStub;
    }
}
