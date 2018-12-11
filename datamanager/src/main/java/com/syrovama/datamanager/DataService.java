package com.syrovama.datamanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.RemoteException;

public class DataService extends Service {
    public static final String FILENAME = "data";
    public static final String PREF_TEXT = "text";

    @Override
    public IBinder onBind(Intent intent) {
        return new IMyAidlInterface.Stub() {
            @Override
            public String getData() throws RemoteException {
                SharedPreferences data = getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
                return data.getString(PREF_TEXT,"");
            }

            @Override
            public void setData(String text) throws RemoteException {
                SharedPreferences data = getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
                data.edit().putString(PREF_TEXT, text).commit();
            }
        };
    }

}
