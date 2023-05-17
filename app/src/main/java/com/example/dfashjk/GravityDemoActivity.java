package com.example.dfashjk;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

public class GravityDemoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, GravityAdapter.OnPlanetClickListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private TiltDetector tiltDetector;
    private TextView gravityTextView;
    BallView ballView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity_demo);

        FrameLayout ballContainer = findViewById(R.id.ball_container);
        ballView = new BallView(this);
        ballContainer.addView(ballView);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        tiltDetector = new TiltDetector(ballView);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);
        gravityTextView = findViewById(R.id.gravity_current);

        RecyclerView planetRecyclerView = findViewById(R.id.planet_recycler_view);
        String[] planetNames = new String[]{"Sun", "Mercury", "Venus", "Earth", "Moon", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        GravityAdapter adapter = new GravityAdapter(planetNames, this);
        planetRecyclerView.setAdapter(adapter);
        onPlanetSelected("Earth");

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }



    public float getGravityForPlanet(String planetName) {
        switch (planetName) {
            case "Sun":
                return 274f;
            case "Mercury":
                return 3.7f;
            case "Venus":
                return 8.87f;
            case "Earth":
                return 9.81f;
            case "Moon":
                return 1.622f;
            case "Mars":
                return 3.721f;
            case "Jupiter":
                return 24.79f;
            case "Saturn":
                return 10.44f;
            case "Uranus":
                return 8.69f;
            case "Neptune":
                return 11.15f;
            default:
                return 9.81f; // Default to Earth's gravity
        }
    }
    private void onPlanetSelected(String planetName) {
        switch (planetName) {
            case "Sun":
                ballView.setImageResource(R.drawable.sun_img);
                break;
            case "Mercury":
                ballView.setImageResource(R.drawable.mercury_img);
                break;
            case "Venus":
                ballView.setImageResource(R.drawable.venus_img);
                break;
            case "Earth":
                ballView.setImageResource(R.drawable.earth_img);
                break;
            case "Moon":
                ballView.setImageResource(R.drawable.moon_img);
                break;
            case "Mars":
                ballView.setImageResource(R.drawable.mars_img);
                break;
            case "Jupiter":
                ballView.setImageResource(R.drawable.jupiter_img);
                break;
            case "Saturn":
                ballView.setImageResource(R.drawable.saturn_img);
                break;
            case "Uranus":
                ballView.setImageResource(R.drawable.uranus_img);
                break;
            case "Neptune":
                ballView.setImageResource(R.drawable.neptune_img);
                break;
            default:
                ballView.setImageResource(R.drawable.earth_img);
                break;
        }
        float gravity = getGravityForPlanet(planetName);
        ballView.setGravity(gravity);
        gravityTextView.setText(gravity + " m/ss");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(tiltDetector, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(tiltDetector);
        super.onPause();
    }
    @Override
    public void onPlanetClick(String planetName) {
        onPlanetSelected(planetName);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation drawer item selection
        switch (item.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(GravityDemoActivity.this, MainActivity.class));
                return true;
            case R.id.nav_solar:
                startActivity(new Intent(GravityDemoActivity.this, NewsActivity.class));
                return true;
            case R.id.nav_picture:
                startActivity(new Intent(GravityDemoActivity.this, PictureActivity.class));
                return true;
            case R.id.nav_planets:
                startActivity(new Intent(GravityDemoActivity.this, PlanetActivity.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}