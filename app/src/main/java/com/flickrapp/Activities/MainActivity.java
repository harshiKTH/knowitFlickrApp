package com.flickrapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.flickrapp.Adapter.DataAdapter;
import com.flickrapp.DAO.JSONResponse;
import com.flickrapp.DAO.RequestInterface;
import com.flickrapp.Models.Items;
import com.flickrapp.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, DataAdapter.onClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Items> data;
    private DataAdapter adapter;
    private Toolbar toolbar;
    public static  final  String EXTRA_URL="extraImageUrl";
    public static  final  String EXTRA_TITLE="extraImageTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    //setting initial view and load details from API
    private void initViews() {
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    //method to call the flicker API and fill adapter details for recycle view
    private void loadJSON() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getItems()));
                adapter = new DataAdapter(getApplicationContext(),data);
                recyclerView.setAdapter(adapter);
                adapter.setOnclickListner(MainActivity.this);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //search view search bar for user input
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        return false;
    }

    //method for filtering the item list based on title content
    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        ArrayList<Items> itemsArrayList = new ArrayList<>();
        for(Items item:data){
            if(item.getTitle().toLowerCase().contains(userInput.toLowerCase())){
                itemsArrayList.add(item);
            }
        }
        adapter.updateView(itemsArrayList);
        adapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onItemClick(int position) {
        //create new intent and pass the image URL and the title
        Intent intent = new Intent(MainActivity.this, PhotoDetail.class);
        Items items = data.get(position);
        intent.putExtra(EXTRA_URL,items.getMedia().getM());
        intent.putExtra(Intent.EXTRA_TITLE,items.getTitle());
        startActivity(intent);
    }
}

