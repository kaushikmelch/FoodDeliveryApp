package com.example.auth;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

class MainLocation {

    private static final String TAG = "Geocoding";


    public static double[] getAddressFromLocation(final String locationAddress,
                                                  final Context context)
    {


        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        double[] coordinates = new double[2];


        try {
            List
                    addressList = geocoder.getFromLocationName(locationAddress, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = (Address) addressList.get(0);


                coordinates[0] = address.getLatitude();
                coordinates[1] = address.getLongitude();

            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to connect to Geocoder", e);
        }
        finally {
            return coordinates;
        }

    }

};

