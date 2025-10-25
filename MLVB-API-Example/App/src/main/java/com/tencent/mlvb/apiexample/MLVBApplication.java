package com.tencent.mlvb.apiexample;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.multidex.MultiDex;

import com.tencent.live2.V2TXLiveDef.V2TXLiveLogConfig;
import com.tencent.live2.V2TXLivePremier;
import com.tencent.mlvb.debug.GenerateTestUserSig;

public class MLVBApplication extends Application {

    private static MLVBApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;
        V2TXLiveLogConfig liveLogConfig = new V2TXLiveLogConfig();
        liveLogConfig.enableConsole = true;
        V2TXLivePremier.setLogConfig(liveLogConfig);
        V2TXLivePremier.setLicence(instance, GenerateTestUserSig.LICENSEURL, GenerateTestUserSig.LICENSEURLKEY);

        V2TXLivePremier.setObserver(new V2TXLivePremier.V2TXLivePremierObserver() {
            @Override
            public void onLicenceLoaded(int result, String reason) {
                Log.d("V2TXLivePremier", "onLicenceLoaded: result:" + result + ", reason:" + reason);
                if (result == 0) {
                    Toast.makeText(instance, "Licence Loaded", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(instance).setTitle("Licence Load Failed").setMessage("result:" + result + ", reason:" + reason).setPositiveButton("OK", null).create();
                    dialog.show();
                }
            }
        });
    }

}
