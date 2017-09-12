package com.timmahh.musicall

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

class MainActivity : AppCompatActivity(), LifecycleRegistryOwner, AnkoLogger {

    val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    lateinit var spotifyContainer: SpotifyDrawerLayout
    @Inject
    lateinit var spotifyUserViewModel: SpotifyUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).serviceComponent.inject(this)
        supportFragmentManager.beginTransaction().add(R.id.mainActivity, { spotifyContainer = SpotifyDrawerLayout(); spotifyContainer }.invoke(),
                "SpotifyDrawerLayout").disallowAddToBackStack().commitNow()
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        debug("onNewIntent:" + intent?.extras)
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry
}
