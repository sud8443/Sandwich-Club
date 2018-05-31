package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            // Converting the String to a JSON object
            JSONObject sandwichJsonObj = new JSONObject(json);

            // Getting the nested JSON object for "name"
            JSONObject name = sandwichJsonObj.getJSONObject("name");

            // Setting the "mainName"
            sandwich.setMainName(name.getString("mainName"));

            // Getting the JSONArray for the other names
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> otherNamesList = new ArrayList<>();
            for (int i = 0; i<alsoKnownAs.length(); i++){
                otherNamesList.add(alsoKnownAs.getString(i));
            }
            // Setting the other names to "alsoKnowAs"
            sandwich.setAlsoKnownAs(otherNamesList);

            // Setting the place of origin
            sandwich.setPlaceOfOrigin(sandwichJsonObj.getString("placeOfOrigin"));

            // Setting the description
            sandwich.setDescription(sandwichJsonObj.getString("description"));

            // Setting the image url
            sandwich.setImage(sandwichJsonObj.getString("image"));

            // Setting the ingredients
            JSONArray ingredientsArray = sandwichJsonObj.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++){
                ingredientsList.add(ingredientsArray.getString(i));
            }
            sandwich.setIngredients(ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
