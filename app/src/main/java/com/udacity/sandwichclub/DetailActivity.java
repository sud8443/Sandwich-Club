package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView placeOfOrigin, description, alsoKnownAs, ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);
        alsoKnownAs = findViewById(R.id.also_known_tv);
        ingredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Add null check's everywhere to prevent the app from crashing
        if(!TextUtils.isEmpty(sandwich.getPlaceOfOrigin()))
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());

        List<String> otherNames = sandwich.getAlsoKnownAs();
        if(otherNames != null && otherNames.size() > 0) {
            alsoKnownAs.setText(otherNames.get(0));
            for (int i = 1; i < otherNames.size(); i++) {
                alsoKnownAs.append(", " + otherNames.get(i));
            }
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList != null && ingredientsList.size() > 0) {
            ingredients.setText(ingredientsList.get(0));
            for (int i = 1; i < ingredientsList.size(); i++) {
                ingredients.append(", " + ingredientsList.get(i));
            }
        }

        if (!TextUtils.isEmpty(sandwich.getDescription()))
            description.setText(sandwich.getDescription());
    }
}
