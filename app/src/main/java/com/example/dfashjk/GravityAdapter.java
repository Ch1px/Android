package com.example.dfashjk;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GravityAdapter extends RecyclerView.Adapter<GravityAdapter.PlanetViewHolder> {

    private final String[] planetNames;
    private int selectedPosition;

    public GravityAdapter(String[] planetNames, OnPlanetClickListener listener) {
        this.planetNames = planetNames;
        this.listener = listener;

        for (int i = 0; i < planetNames.length; i++) {
            if (planetNames[i].equals("Earth")) {
                selectedPosition = i;
                break;
            }
        }
    }

    @NonNull
    @Override
    public PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.planet_item, parent, false);
        return new PlanetViewHolder(view);
    }

    public interface OnPlanetClickListener {
        void onPlanetClick(String planetName);
    }

    private OnPlanetClickListener listener;

    @Override
    public void onBindViewHolder(@NonNull PlanetViewHolder holder, int position) {
        String planetName = planetNames[position];
        holder.planetName.setText(planetName);
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            listener.onPlanetClick(planetName);
        });
        if (selectedPosition == position) {
            holder.planetName.setTextColor(Color.parseColor("#00ffff"));
        } else {
            holder.planetName.setTextColor(Color.parseColor("#909090"));
        }
    }

    @Override
    public int getItemCount() {
        return planetNames.length;
    }

    static class PlanetViewHolder extends RecyclerView.ViewHolder {

        TextView planetName;

        PlanetViewHolder(@NonNull View itemView) {
            super(itemView);
            planetName = itemView.findViewById(R.id.planet_name);
        }
    }
}
