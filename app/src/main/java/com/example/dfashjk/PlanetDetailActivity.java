package com.example.dfashjk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class PlanetDetailActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_detail);

        PlanetData obj = getIntent().getParcelableExtra("planet");
        Integer planetImg = getIntent().getIntExtra("planetImage", -1);
        SetData(obj, planetImg);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);


        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(PlanetDetailActivity.this, MainActivity.class));
                        return true;
                    case R.id.nav_planets:
                        startActivity(new Intent(PlanetDetailActivity.this, PlanetActivity.class));
                        return true;
                    case R.id.nav_solar:
                        startActivity(new Intent(PlanetDetailActivity.this, SolarActivity.class));
                        return true;
                    case R.id.nav_contact:
                        startActivity(new Intent(PlanetDetailActivity.this, ContactActivity.class));
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }else{
            super.onBackPressed();
        }
    }

    private void SetData(PlanetData obj, Integer planetImg) {
        TextView titleInfo = findViewById(R.id.title_info);
        titleInfo.setText(obj.getTitle());

        TextView distanceInfo = findViewById(R.id.distance_info);
        distanceInfo.setText(obj.getDistance() + "m km");

        TextView gravityInfo = findViewById(R.id.gravity_info);
        gravityInfo.setText(obj.getGravity() + " m/ss");

        TextView overviewInfo = findViewById(R.id.overview_info);
        overviewInfo.setText(obj.getOverview());

        TextView galaxyInfo = findViewById(R.id.galaxy_info);
        galaxyInfo.setText(obj.getGalaxy());

        ImageView planetImgInfo = findViewById(R.id.planet_img_info);
        planetImgInfo.setImageResource(planetImg);
    }

}