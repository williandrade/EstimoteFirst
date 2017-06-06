package com.example.williamandrade.estimotefirst.action;

import com.estimote.coresdk.recognition.packets.Beacon;

/**
 * Created by williamandrade on 29/05/17.
 */

public interface BeaconActionGeneric {

    public String getName();

    public void doAction(Beacon beacon);

}
