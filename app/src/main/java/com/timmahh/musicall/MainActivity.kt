package com.timmahh.musicall

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.BindingAdapter
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.timmahh.spotifyweb.log
import com.timmahh.spotifyweb.retrofit.models.Image
import com.timmahh.spotifyweb.toString
import com.timmahh.spotifyweb.viewmodels.SpotifyUserViewModel
import javax.inject.Inject

@BindingAdapter("app:imageUrls")
fun loadImage(view: ImageView, imageUrls: List<Image>) {
	GlideApp.with(view).load(imageUrls[imageUrls.lastIndex].url).diskCacheStrategy(DiskCacheStrategy
			.ALL).centerCrop().fallback(R.mipmap
			.ic_launcher_round).placeholder(R.mipmap.ic_launcher_round).into(view)
}

@GlideModule
class MainGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, LifecycleRegistryOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    @Inject
    lateinit var spotifyUserViewModel: SpotifyUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).serviceComponent.inject(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
	
	    val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }
	
	override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
	
	    if (id == R.id.log_in) {
		    spotifyUserViewModel.loadAuthLogin(this)
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
	    if (requestCode == Auth.SPOTIFY_REQUEST_CODE)
		    spotifyUserViewModel.authLoginResponse(resultCode, data, this).observe(this, Observer {
			    log("Observing token response: ${toString(it)}")
		    })
    }
	
	/**
	 * Handle onNewIntent() to inform the fragment manager that the
	 * state is not saved.  If you are handling new intents and may be
	 * making changes to the fragment state, you want to be sure to call
	 * through to the super-class here first.  Otherwise, if your state
	 * is saved but the activity is not stopped, you could get an
	 * onNewIntent() call which happens before onResume() and trying to
	 * perform fragment operations at that point will throw IllegalStateException
	 * because the fragment manager thinks the state is still saved.
	 */
	override fun onNewIntent(intent: Intent?) {
		super.onNewIntent(intent)
		log(toString(intent))
		/*if (requestCode == Auth.SPOTIFY_REQUEST_CODE)
			spotifyUserViewModel.authLoginResponse(resultCode, data, this).observe(this, Observer {
				log("Observing token response: ${toString(it)}")
			})*/
		
	}
	
	override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}
