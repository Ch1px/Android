package com.example.dfashjk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView latestApodImageView;
    TextView latestApodExplanation;
    CardView newsCard;
    CardView gravityCard;
    CardView factCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);
        latestApodImageView = findViewById(R.id.latest_apod_image);
        latestApodExplanation = findViewById(R.id.latest_apod_explanation);
        newsCard = findViewById(R.id.newsCard);
        gravityCard = findViewById(R.id.gravityCard);
        factCard = findViewById(R.id.factCard);
        newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });
        gravityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GravityDemoActivity.class);
                startActivity(intent);
            }
        });
        factCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlanetActivity.class);
                startActivity(intent);
            }
        });
        latestApodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PictureActivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_planets:
                        startActivity(new Intent(MainActivity.this, PlanetActivity.class));
                        return true;
                    case R.id.nav_solar:
                        startActivity(new Intent(MainActivity.this, NewsActivity.class));
                        return true;
                    case R.id.nav_picture:
                        startActivity(new Intent(MainActivity.this, PictureActivity.class));
                        return true;
                    case R.id.nav_gravity:
                        startActivity(new Intent(MainActivity.this, GravityDemoActivity.class));
                        return true;
                    default:
                        return false;
                }
            }
        });
        if (isNetworkConnected()) {
            fetchLatestSpaceNews();
            fetchApod();
        } else {
            Toast.makeText(this, "You need to be connected to the internet to access this content.", Toast.LENGTH_LONG).show();
        }
    }
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    private void fetchApod() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String imageUrl = jsonObject.getString("url");
                    String explanation = jsonObject.getString("explanation");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(MainActivity.this).load(imageUrl).into(latestApodImageView);
                            latestApodExplanation.setText(explanation);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void fetchLatestSpaceNews() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.spaceflightnewsapi.net/v3/articles")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();

                    NewsItem latestNews = parseJsonForLatest(jsonData);

                    runOnUiThread(() -> {
                        TextView newsTitleView = findViewById(R.id.latest_news_title);
                        TextView newsSummaryView = findViewById(R.id.latest_news_summary);

                        newsTitleView.setText(latestNews.getTitle());
                        newsSummaryView.setText(latestNews.getSummary());
                    });
                }
            }
        });
    }
    private NewsItem parseJsonForLatest(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);

            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String title = jsonObject.getString("title");
            String summary = jsonObject.getString("summary");

            return new NewsItem(title, summary);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}