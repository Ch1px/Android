package com.example.dfashjk;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {
    private final List<PlanetData> planet;

    public Adapter(List<PlanetData> planet) {
        this.planet = planet;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final Integer[] dummyImage = {null};
        holder.itemView.setOnClickListener(v -> {
            synchronized (dummyImage) {

            Intent intent = new Intent(holder.itemView.getContext(), PlanetDetailActivity.class);
            intent.putExtra("planet", planet.get(position));
            intent.putExtra("planetImage", dummyImage[0]);
            holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.title.setText(planet.get(position).getTitle());
        holder.galaxy.setText(planet.get(position).getGalaxy());
        holder.distance.setText(planet.get(position).getDistance() + " m km");
        holder.gravity.setText(planet.get(position).getGravity() + " m/ss");

        switch (planet.get(position).getTitle().toLowerCase()) {
            case "mars":
                dummyImage[0] =  R.drawable.mars_img;
                break;
            case "neptune":
                dummyImage[0] =  R.drawable.neptune_img;
                break;
            case "earth":
                dummyImage[0] =  R.drawable.earth_img;
                break;
            case "venus":
                dummyImage[0] =  R.drawable.venus_img;
                break;
            case "jupiter":
                dummyImage[0] =  R.drawable.jupiter_img;
                break;
            case "saturn":
                dummyImage[0] =  R.drawable.saturn_img;
                break;
            case "uranus":
                dummyImage[0] =  R.drawable.uranus_img;
                break;
            case "mercury":
                dummyImage[0] =  R.drawable.mercury_img;
                break;
            case "sun":
                dummyImage[0] =  R.drawable.sun_img;
                break;
        }
        holder.planetimg.setImageResource(dummyImage[0]);

    }

    @Override
    public int getItemCount() {
        return planet.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView title, galaxy, distance, gravity;
        ImageView planetimg;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            galaxy = itemView.findViewById(R.id.galaxy);
            distance = itemView.findViewById(R.id.distance);
            gravity = itemView.findViewById(R.id.gravity);
            planetimg = itemView.findViewById(R.id.planet_img);
        }
    }
}