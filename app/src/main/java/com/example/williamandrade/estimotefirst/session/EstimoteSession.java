package com.example.williamandrade.estimotefirst.session;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.common.requirements.DefaultRequirementsCheckerCallback;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.Region;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.example.williamandrade.estimotefirst.action.BeaconActionGeneric;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by williamandrade on 29/05/17.
 */

public class EstimoteSession {

    private static final String TAG = "ESTIMOTE_SESSION";

    private static String appId = "williandrade-me-com-s-blan-mk5";
    private static String appToken = "0e271d522baf030c17a3e81fee34f3d1";

    private Context applicationContext = null;
    private Activity activity = null;

    private BeaconManager beaconManager = null;
    private BeaconRegion allBeaconsRegion = null;

    private List<BeaconActionGeneric> actions;

    public EstimoteSession(Context applicationContext) {
        this.applicationContext = applicationContext;

        init();

        beaconManager = new BeaconManager(this.applicationContext);
    }

    public EstimoteSession(Context applicationContext, Activity activity) {
        this.applicationContext = applicationContext;
        this.activity = activity;

        Log.d(TAG, "Will check for the permissions");
        this.checkPermission(activity);

        init();

        beaconManager = new BeaconManager(this.applicationContext);
    }

    private void init() {
        Log.d(TAG, "Init parameters");
        //  To get your AppId and AppToken you need to create new application in Estimote Cloud.
        EstimoteSDK.initialize(applicationContext, appId, appToken);
        // Optional, debug logging.
        EstimoteSDK.enableDebugLogging(true);
    }


    public void startLookForBeacon(String proximityUUID, Integer major, Integer minor) {
        this.setAllBeaconsRegion(proximityUUID, major, minor);

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

    public void addAction(BeaconActionGeneric action) {
        if (actions == null) {
            actions = new ArrayList<>();
        }

        actions.add(action);
    }

    public static Boolean checkPermission(Activity activity) {
        return SystemRequirementsChecker.checkWithDefaultDialogs(activity);
    }


    private void setAllBeaconsRegion(String proximityUUID, Integer major, Integer minor) {
        allBeaconsRegion = new BeaconRegion("Beacons with default Estimote UUID", UUID.fromString(proximityUUID), major, minor);
    }

}
