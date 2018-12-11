package com.syrovama.aidltask;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.syrovama.datamanager.IMyAidlInterface;

public class MainActivity extends FragmentActivity
        implements SendDataFragment.Callback, GetDataFragment.Callback {

    private static final String TAG = "MainActivity" ;
    private static final String PACKAGE = "com.syrovama.datamanager";
    private static final String SERVICE_NAME = ".DataService";
    private IMyAidlInterface mService;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IMyAidlInterface.Stub.asInterface(service);
            Log.d(TAG, "service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            Log.d(TAG, "service disconnected");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SendDataFragment sendFragment = (SendDataFragment)fragmentManager.findFragmentById(R.id.fragmentSendData);
        if (sendFragment == null) {
            sendFragment = new SendDataFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentSendData, sendFragment)
                    .commit();
        }
        GetDataFragment getFragment = (GetDataFragment)fragmentManager.findFragmentById(R.id.fragmentGetData);
        if (getFragment == null) {
            getFragment = new GetDataFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentGetData, getFragment)
                    .commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent i = new Intent();
        i.setComponent(new ComponentName(PACKAGE, PACKAGE+SERVICE_NAME));
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        if (mConnection != null) {
            unbindService(mConnection);
        }
        super.onPause();

    }

    @Override
    public void onSendRequested(String text) {
        try {
            mService.setData(text);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving data, try again later", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public String onTextRequested() {
        String text = null;
        try {
            text = mService.getData();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error getting data, try again later", Toast.LENGTH_LONG)
                    .show();
        }
        return text;
    }
}
