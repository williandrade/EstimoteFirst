package com.example.williamandrade.estimotefirst;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.example.williamandrade.estimotefirst.action.NotificationAction;
import com.example.williamandrade.estimotefirst.background.BackgroundProcess;
import com.example.williamandrade.estimotefirst.session.EstimoteSession;

public class DiscoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);

//        Boolean permissions = EstimoteSession.checkPermission(this);

//        if (permissions) {
//            Intent bgIntent = new Intent(this, BackgroundProcess.class);
//            startService(bgIntent);
//        }

//        if (permissions) {
//            Log.d("AQUI", " Starting Session");
//            EstimoteSession estimoteSession = new EstimoteSession(getApplicationContext());
//
//            NotificationManager mNotificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            estimoteSession.addAction(new NotificationAction(this, mNotificationManager));
//
//            Log.d("AQUI", " Starting Look");
//            estimoteSession.startLookForBeacon("B9407F30-F5F8-466E-AFF9-25556B57FE6D", null, null);
//        }
    }
}
