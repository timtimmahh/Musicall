package com.timothy.spotifyarchitecture.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by tim on 4/14/17.
 */
@Entity(tableName = "token", primaryKeys = {"access_token"}, indices = {@Index(value = {"id"}, unique = true)})
public class Token {
    public String access_token;
    public String token_type;
    public String scope;
    public long expires_in;
    public String refresh_token;
    public String id;

    public Token() {
    }

    public static Token createToken(Token token) {
        token.expires_in = System.currentTimeMillis() + (token.expires_in * 1000);
        return token;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
