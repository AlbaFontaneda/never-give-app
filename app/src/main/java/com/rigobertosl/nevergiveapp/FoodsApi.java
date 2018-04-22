package com.rigobertosl.nevergiveapp;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class FoodsApi extends AsyncTask<View, Void, ArrayList<FoodKcal>> {

    final String FOODS_KEY = "LTplIb5O1cbc2giBWN3Kf0N3ZuLgsIsgHsLAptbZ";
    final String FOOD_NO = "01229";

    final String TYPE_REQUEST = "s";
    final String FORMAT_REQUEST = "json";

    @Override
    protected ArrayList<FoodKcal> doInBackground(View... views) {

        ArrayList<FoodKcal>temp = makeCall("https://api.nal.usda.gov/ndb/V2/reports?ndbno="
                + FOOD_NO + "&type=" + TYPE_REQUEST + "&format=" + FORMAT_REQUEST + "&api_key=" + FOODS_KEY);

        return temp;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private ArrayList<FoodKcal> makeCall(String stringURL) {
        URL url = null;
        InputStream is = null;
        JSONObject jsonObject;
        ArrayList<FoodKcal> temp = new ArrayList<FoodKcal>();

        try {
            url = new URL(stringURL);
        } catch (Exception ex) {
            System.out.println("Malformed URL");
        }

        try {
            if (url != null) {
                is = url.openStream();
            }
        } catch (IOException ioe) {
            System.out.println("IOException");
        }
        if(is != null) {
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject jsonObj = new JSONObject(jsonText);

                JSONArray foods = jsonObj.getJSONArray("foods");
                for (int i = 0; i < foods.length(); i++) {
                    JSONObject food = foods.getJSONObject(i);

                    JSONObject nutrients = food.getJSONObject("nutrients");
                    int x = 0;
                    for (int j = 0; j < nutrients.length(); i++) {
                        //JSONObject c = nutrients.getJSONObject(i);
                        //if(c.getString("nutrient_id").equals(208)){
                           // FoodKcal poi = new FoodKcal(c.getString("unit"), c.getString("value"));
                            //temp.add(poi);
                        //}
                    }
                }

            } catch (Exception e) {
                System.out.println("Exception");
                return new ArrayList<FoodKcal>();
            }
        }

        return  temp;
    }

    protected void onPostExecute(ArrayList<FoodKcal> result) {
        // Aquí se actualiza el interfaz de usuario
        List<String> listTitle = new ArrayList<String>();

        for (int i = 0; i < result.size(); i++) {
            // make a list of the venus that are loaded in the list.
            // show the name, the category and the city
            listTitle.add(i, "tipo: " +result.get(i).getName() + "\nkcal: " + result.get(i).getKcal());
        }

        // set the results to the list
        // and show them in the xml
    }
}
