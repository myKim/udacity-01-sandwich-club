package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        String mainName = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;

        JSONObject sandwichJson = new JSONObject(json);

        JSONObject nameJson = sandwichJson.getJSONObject("name");

        mainName = nameJson.getString("mainName");
        JSONArray alsoKnownAsArray = nameJson.getJSONArray("alsoKnownAs");

        alsoKnownAs = new ArrayList<String>();
        for(int i=0; i<alsoKnownAsArray.length(); i++) {
            alsoKnownAs.add(alsoKnownAsArray.getString(i));
        }

        placeOfOrigin = sandwichJson.getString("placeOfOrigin");
        description = sandwichJson.getString("description");
        image = sandwichJson.getString("image");

        JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
        ingredients = new ArrayList<String>();
        for(int i=0; i<ingredientsArray.length(); i++) {
            ingredients.add(ingredientsArray.getString(i));
        }

        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return sandwich;
    }
}
