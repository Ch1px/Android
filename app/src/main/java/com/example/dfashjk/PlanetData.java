package com.example.dfashjk;

import android.os.Parcel;
import android.os.Parcelable;

public class PlanetData implements Parcelable {
    private Integer id;
    private String title;
    private String galaxy;
    private String distance;
    private String gravity;
    private String overview;

    public PlanetData(Integer id, String title, String galaxy, String distance, String gravity, String overview) {
        this.id = id;
        this.title = title;
        this.galaxy = galaxy;
        this.distance = distance;
        this.gravity = gravity;
        this.overview = overview;
    }

    private PlanetData(Parcel in) {
        id = in.readInt();
        title = in.readString();
        galaxy = in.readString();
        distance = in.readString();
        gravity = in.readString();
        overview = in.readString();
    }

    public static final Creator<PlanetData> CREATOR = new Creator<PlanetData>() {
        @Override
        public PlanetData createFromParcel(Parcel in) {
            return new PlanetData(in);
        }

        @Override
        public PlanetData[] newArray(int size) {
            return new PlanetData[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGalaxy() {
        return galaxy;
    }

    public void setGalaxy(String galaxy) {
        this.galaxy = galaxy;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getGravity() {
        return gravity;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(galaxy);
        dest.writeString(distance);
        dest.writeString(gravity);
        dest.writeString(overview);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

