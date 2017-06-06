package com.timothy.spotifyarchitecture.retrofit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#cursor-based-paging-object">Cursor-based paging object model</a>
 *
 * @param <T> expected object that is paged
 */
public class CursorPager<T> {
    public String href;
    public List<T> items;
    public int limit;
    public String next;
    public Cursor cursors;
    public int total;

    public CursorPager() {
    }
}
