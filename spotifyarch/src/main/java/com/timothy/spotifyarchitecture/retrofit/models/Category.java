package com.timothy.spotifyarchitecture.retrofit.models;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
@Entity(primaryKeys = {"id"}, tableName = "spotify_category")
public class Category {
  public String href;
  public List<Image> icons;
  public String id;
  public String name;

  public Category() {
  }

}
