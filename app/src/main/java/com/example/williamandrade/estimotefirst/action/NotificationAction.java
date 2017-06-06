package com.example.williamandrade.estimotefirst.action;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.estimote.coresdk.recognition.packets.Beacon;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by williamandrade on 29/05/17.
 */

public class NotificationAction implements BeaconActionGeneric {

    private Context context;
    private NotificationManager mNotificationManager;
    private Map<String, Integer> beacons = new HashMap<>();

    public NotificationAction(Context context, NotificationManager mNotificationManager) {
        this.context = context;
        this.mNotificationManager = mNotificationManager;
    }

    @Override
    public String getName() {
        return "NOTIFICATION";
    }

    @Override
    public void doAction(Beacon beacon) {
        String uniqueKey = beacon.getUniqueKey();
        Integer distance = beacons.get(uniqueKey);

        if (distance == null) {
            //FIRST TIME
            distance = beacon.getRssi();
        } else {
            Integer actualDistance = beacon.getRssi();
            if (distance < actualDistance + 10 || distance > actualDistance + 10) {
                //DO NOTHING
                return;
            }

            distance = actualDistance;
        }

        beacons.put(uniqueKey, distance);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.alert_dark_frame)
                        .setContentTitle("Found BEACON")
                        .setContentText("Hello World! " + beacon.getUniqueKey());

        //RSI is the distance
        Log.d("AQUI", "rsi: " + beacon.getRssi());

        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());

    }


}
