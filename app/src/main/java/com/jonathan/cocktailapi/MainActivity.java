package com.jonathan.cocktailapi;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jonathan.cocktailapi.adapter.DrinkAdapter;
import com.jonathan.cocktailapi.databinding.ActivityMainBinding;
import com.jonathan.cocktailapi.entity.Drink;
import com.jonathan.cocktailapi.entity.DrinkResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityMainBinding binding;
    private DrinkAdapter drinkAdapter;

    private ArrayAdapter<Drink> categoryArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //  Set LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        binding.rvData.setLayoutManager(manager);

        //  Set Adapter
        binding.rvData.setAdapter(getDrinkAdapter());

        //  Load Category Data
        loadCategoryData();

        //  Load Drink Data
//        loadDrinkData();

        //  SR Layout
        binding.srLayout.setOnRefreshListener(() -> {
            binding.srLayout.setRefreshing(false);
        });

        //  Spin Listener
        binding.spinCategory.setOnItemSelectedListener(this);


    }

    public DrinkAdapter getDrinkAdapter() {
        if (drinkAdapter == null) {
            drinkAdapter = new DrinkAdapter();
        }
        return drinkAdapter;
    }

    public ArrayAdapter<Drink> getCategoryArrayAdapter(ArrayList<Drink> categories) {
        if (categoryArrayAdapter == null) {
            categoryArrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, categories);
        }
        return categoryArrayAdapter;
    }

    private void loadCategoryData() {
        try {
            InputStream inputStream = getAssets().open("drink_categoryv2.json");
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
            Gson gson = new Gson();
            Drink[] drinks = gson.fromJson(reader, Drink[].class);
            ArrayList<Drink> listDrinks = new ArrayList<>(Arrays.asList(drinks));

            // Set Spinner ArrayAdapter
            binding.spinCategory.setAdapter(getCategoryArrayAdapter(listDrinks));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  https://www.thecocktaildb.com/api/json/v1/1/filter.php?c=Cocktail
    private void loadDrinkData(String categoryName) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Uri uri = Uri.parse("https://www.thecocktaildb.com/api/json/v1/1/filter.php").buildUpon()
                .appendQueryParameter("c", categoryName)
                .build();
        StringRequest request = new StringRequest(Request.Method.GET, uri.toString(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                Gson gson = new Gson();

                DrinkResponse drinkResponse = gson.fromJson(object.toString(), DrinkResponse.class);

                // Drink List
                ArrayList<Drink> listDrink = new ArrayList<>();

                for (int i = 0; i < drinkResponse.getDrinks().length; i++) {
                    Drink dr = drinkResponse.getDrinks()[i];

                    Drink d = new Drink();
                    d.setName(dr.getName());
                    d.setThumbnail(dr.getThumbnail());
                    d.setId(dr.getId());

                    listDrink.add(d);
                }
                drinkAdapter.changeData(listDrink);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        loadDrinkData(item);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
