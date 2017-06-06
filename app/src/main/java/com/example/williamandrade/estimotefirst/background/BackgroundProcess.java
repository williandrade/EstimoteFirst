package com.example.williamandrade.estimotefirst.background;

import android.Manifest;
import android.app.Application;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.example.williamandrade.estimotefirst.action.BeaconActionGeneric;
import com.example.williamandrade.estimotefirst.action.NotificationAction;
import com.example.williamandrade.estimotefirst.session.EstimoteSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by williamandrade on 30/05/17.
 */
public class BackgroundProcess extends Application {

    private static final String TAG = "BackgroundProcess";

    private BeaconManager beaconManager;
    private EstimoteSession estimoteSession;

    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());

        BeaconRegion allBeaconsRegion = new BeaconRegion("Beacons with default Estimote UUID", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

        List<BeaconActionGeneric> actions = new ArrayList<>();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        actions.add(new NotificationAction(this, mNotificationManager));


        //REGION LISTNER
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> list) {
                Log.d(TAG, "Entered at the Region =D");

                //YOU HAVE ALL THE BEACONS ALREADY
//                list.forEach(each -> {
//                    Log.d(TAG, "Each MONITORING_LISTNER XD -> UK: " + each.getUniqueKey() + " PUUID: " + each.getProximityUUID());
//                });

                beaconManager.startRanging(allBeaconsRegion);

            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                Log.d(TAG, "Out of the Region =(");

                beaconManager.stopRanging(allBeaconsRegion);
            }
        });

        //RANGIN LISTNER
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> list) {
                list.forEach(each -> {
                    if (actions != null) {
                        actions.forEach(action -> {
                            Log.d(TAG, "Doing action " + action.getName());

                            action.doAction(each);
                        });
                    }
                });
            }
        });

        beaconManager.connect(() -> {
            // Ready to start scanning!
            Log.d(TAG, "Start monitoring for the region seted");
            beaconManager.startMonitoring(allBeaconsRegion);
            // beaconManager.startRanging(defaultUUIDRegion);
        });
    }

//    @Override
//    protected void onHandleIntent(Intent workIntent) {
//        // Gets data from the incoming Intent
//        String dataString = workIntent.getDataString();
//
//        Log.d("AQUI", " Starting Session");
//        estimoteSession = new EstimoteSession(mContext);
//
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        estimoteSession.addAction(new NotificationAction(this, mNotificationManager));
//
//        Log.d("AQUI", " Starting Look");
//        estimoteSession.startLookForBeacon();
//    }
}