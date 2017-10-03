package com.timmahh.musicall

import android.app.Application
import android.arch.lifecycle.*
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.timmahh.spotifyweb.getSimpleName
import com.timmahh.spotifyweb.log
import com.timmahh.spotifyweb.presenters.SpotifyDrawerLayout
import com.timmahh.spotifyweb.toString
import com.timmahh.spotifyweb.viewmodels.SpotifyUserViewModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import javax.inject.Inject

/*@BindingAdapter("app:imageUrls")
fun loadImage(view: ImageView, imageUrls: List<Image>) {
	GlideApp.with(view).load(imageUrls[imageUrls.lastIndex].url).diskCacheStrategy(DiskCacheStrategy
			.ALL).centerCrop().fallback(R.mipmap
			.ic_launcher_round).placeholder(R.mipmap.ic_launcher_round).into(view)
}

@GlideModule
class MainGlideModule : AppGlideModule()*/
class MainViewModel
@Inject
constructor(application: Application) : AndroidViewModel(application) {

    val navListeners: MediatorLiveData<MutableList<NavigationView.OnNavigationItemSelectedListener>> = MediatorLiveData()

    init {
        navListeners.value = ArrayList<NavigationView.OnNavigationItemSelectedListener>()
    }

}

class MainActivity : AppCompatActivity(), LifecycleRegistryOwner, AnkoLogger, NavigationView.OnNavigationItemSelectedListener {

    val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    lateinit var spotifyContainer: SpotifyDrawerLayout
    @Inject
    lateinit var spotifyUserViewModel: SpotifyUserViewModel
    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).serviceComponent.inject(this)
        supportFragmentManager.beginTransaction().add(R.id.mainActivity, { spotifyContainer = SpotifyDrawerLayout(); spotifyContainer }.invoke(),
                "SpotifyDrawerLayout").disallowAddToBackStack().commitNow()
        mainViewModel.navListeners.value!!.add(spotifyContainer)
        (application as App).serviceComponent.inject(spotifyContainer)
        spotifyUserViewModel.spotifyUser.observe(this, Observer {
            debug("observing spotify user data")
        })
    }

    override fun onBackPressed() {
        if (spotifyContainer.onBackPressed())
            super.onBackPressed()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Auth.SPOTIFY_REQUEST_CODE)
            spotifyUserViewModel
                    .authLoginResponse(resultCode, data, this)
                    .observe(this, Observer {
                        debug("Observing token response: ${toString(it)}")
                    })
    }

    fun serviceDropDown(v: View) {
        log("Service Drop Down", v.getSimpleName())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        debug("onNewIntent:" + intent?.extras)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        for (listener in mainViewModel.navListeners.value!!) {
            listener.onNavigationItemSelected(item)
        }
        return true
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry
}
