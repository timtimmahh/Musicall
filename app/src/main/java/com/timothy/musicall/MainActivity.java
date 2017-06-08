package com.timothy.musicall;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.timothy.spotifyarchitecture.entities.SpotifyUser;
import com.timothy.spotifyarchitecture.remote.Resource;
import com.timothy.spotifyarchitecture.viewmodels.SpotifyViewModel;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LifecycleRegistryOwner {
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Inject
    SpotifyViewModel defaultViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(Auth.SPOTIFY_CLIENT_ID, AuthenticationResponse.Type.CODE, Auth.REDIRECT_URI);
            builder.setScopes(new String[]{"playlist-read-private", "playlist-read-collaborative", "playlist-modify-public", "playlist-modify-private", "streaming",
                    "user-follow-modify", "user-follow-read", "user-library-read", "user-library-modify", "user-read-private", "user-read-birthdate",
                    "user-read-email", "user-top-read"});
            AuthenticationRequest request = builder.build();
            AuthenticationClient.openLoginActivity(MainActivity.this, Auth.SPOTIFY_REQUEST_CODE, request);
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ((App)getApplication()).getServiceComponent().inject(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Auth.SPOTIFY_REQUEST_CODE) {
            final AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, data);
            if (response.getType().equals(AuthenticationResponse.Type.CODE))
                defaultViewModel.loadAuthToken(response.getCode()).observe(this, tokenResource -> {
                    if (tokenResource != null) {
                        defaultViewModel.loadMe().observe(MainActivity.this, meObserver);
                        Log.d("TokenReturned", new Gson().toJson(tokenResource.data));
                    } else {
                        Log.d("TokenError", "Null Token");
                    }
                });
        }
    }

    Observer<Resource<SpotifyUser>> meObserver = spotifyUserResource -> {
        if (spotifyUserResource != null) {
            Log.d("OnLoadMe", spotifyUserResource.data != null ? spotifyUserResource.data.display_name : "Error with data.");
        } else {
            Log.d("OnLoadMe", "Error loading me.");
        }
    };

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
