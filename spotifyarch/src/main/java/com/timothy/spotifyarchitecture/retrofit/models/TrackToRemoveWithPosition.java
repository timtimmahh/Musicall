package com.timothy.spotifyarchitecture.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TrackToRemoveWithPosition {
    public String uri;
    public List<Integer> positions;

    public TrackToRemoveWithPosition() {
    }
}
