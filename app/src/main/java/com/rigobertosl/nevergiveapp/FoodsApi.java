package com.rigobertosl.nevergiveapp;

import android.util.JsonReader;
import android.view.View;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

class FoodsKcal {
    private String name;
    private String kcal;

    public FoodsKcal() {
        this.name = "";
        this.kcal = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getKcal() {
        return kcal;
    }

    public String setKcal() {
        return kcal;
    }

}


public class FoodsApi {

    final String FOODS_KEY = "LTplIb5O1cbc2giBWN3Kf0N3ZuLgsIsgHsLAptbZ";
    final String FOOD_NO = "01229";

    final String TYPE_REQUEST = "s";
    final String FORMAT_REQUEST = "json";

    protected ArrayList<FoodsKcal> doInBackground(View... urls) {
        ArrayList<FoodsKcal> temp;
        //print the call in the console
        System.out.println("https://api.nal.usda.gov/ndb/V2/reports?ndbno="
                + FOOD_NO + "&type=" + TYPE_REQUEST + "&format=" + FORMAT_REQUEST + "&api_key=" + FOODS_KEY);

        // make Call to the url
        temp = makeCall("https://api.nal.usda.gov/ndb/V2/reports?ndbno="
                + FOOD_NO + "&type=" + TYPE_REQUEST + "&format=" + FORMAT_REQUEST + "&api_key=" + FOODS_KEY);

        return temp;
    }

    public static ArrayList<FoodsKcal> makeCall(String stringURL) {

        URL url = null;
        BufferedInputStream is = null;
        JsonReader jsonReader;
        ArrayList<FoodsKcal> temp = new ArrayList<FoodsKcal>();

        try {
            url = new URL(stringURL);
        } catch (Exception ex) {
            System.out.println("Malformed URL");
        }

        try {
            if (url != null) {
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                is = new BufferedInputStream(urlConnection.getInputStream());
            }
        } catch (IOException ioe) {
            System.out.println("IOException");
        }

        if (is != null) {
            try {

            } catch (Exception e) {
                System.out.println("Exception");
                return new ArrayList<FoodsKcal>();
            }
        }

        return temp;}
}
