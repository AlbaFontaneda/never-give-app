package com.rigobertosl.nevergiveapp.food.activity;

import android.os.AsyncTask;
import android.view.View;

import com.rigobertosl.nevergiveapp.food.activity.FoodResumeActivity;
import com.rigobertosl.nevergiveapp.objects.FoodKcal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FoodsApi extends AsyncTask<View, Void, ArrayList<FoodKcal>> {

    final String FOODS_KEY = "LTplIb5O1cbc2giBWN3Kf0N3ZuLgsIsgHsLAptbZ";
    final String FOOD_NO = "?ndbno=45001659&ndbno=45049771&ndbno=01083&ndbno=45048329&ndbno=45034090" +
            "&ndbno=45002256&ndbno=45001849&ndbno=45013375&ndbno=45056459&ndbno=45003803&ndbno=45002292&ndbno=45038434";

    final String TYPE_REQUEST = "s";
    final String FORMAT_REQUEST = "json";

    @Override
    protected ArrayList<FoodKcal> doInBackground(View... views) {
        ArrayList<FoodKcal>temp =  makeCall("https://api.nal.usda.gov/ndb/V2/reports"
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
                    JSONObject jObj = foods.getJSONObject(i);
                    JSONObject food = jObj.getJSONObject("food");
                    JSONArray nutrients = food.getJSONArray("nutrients");

                    for (int j = 0; j < nutrients.length(); j++) {
                        JSONObject c = nutrients.getJSONObject(j);
                        if(c.getString("nutrient_id").equals("208")){
                            String kcal = c.getString("value");
                            String unidades = c.getString("unit");
                            FoodKcal poi = new FoodKcal(unidades,kcal);
                            temp.add(poi);
                        }
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
        List<Integer> listKcal = new ArrayList<Integer>();

        for (int i = 0; i < result.size(); i++) {
            listKcal.add(i, Integer.valueOf(result.get(i).getKcal()));
        }

        FoodResumeActivity.listKcal = listKcal;

    }
}
