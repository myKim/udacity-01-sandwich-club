package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mMainNameTextView;
    private TextView mAlsoKnownTextView;
    private TextView mIngredientsTextView;
    private TextView mOriginTextView;
    private TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
            sandwich = null;
        }

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
        mMainNameTextView = (TextView) findViewById(R.id.main_name_tv);
        mOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        mAlsoKnownTextView = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);

        mMainNameTextView.setText(sandwich.getMainName());
        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());

        mAlsoKnownTextView.setText("");
        ArrayList<String> alsoKnownArray = (ArrayList<String>) sandwich.getAlsoKnownAs();
        for(int i=0; i<alsoKnownArray.size(); i++) {
            if(i != 0) {
                mAlsoKnownTextView.append(", ");
            }
            mAlsoKnownTextView.append(alsoKnownArray.get(i));
        }

        mIngredientsTextView.setText("");
        ArrayList<String> ingredientsArray = (ArrayList<String>) sandwich.getIngredients();
        for(int i=0; i<ingredientsArray.size(); i++) {
            if(i != 0) {
                mIngredientsTextView.append(", ");
            }
            mIngredientsTextView.append(ingredientsArray.get(i));
        }
    }
}
