package com.timmahh.spotifyweb.presenters

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.timmahh.spotifyweb.viewmodels.SpotifyUserViewModel
import kotlinx.android.synthetic.main.activity_spotify.*
import kotlinx.android.synthetic.main.app_bar_spotify.*
import javax.inject.Inject

/**
 * Entity and Pojo used to create a many to many relationship since Room doesn't support it.
 */
class SpotifyDrawerLayout : Fragment(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var spotifyUserViewModel: SpotifyUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*spotifyUserViewModel.spotifyUser.observe(this, Observer {
            debug("observing spotify user data")
        })*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                activity, drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.activity_spotify, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                com.timmahh.spotifyweb.R.id.action_settings -> true
                else -> false
            }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.log_in -> {
                spotifyUserViewModel.loadAuthLogin(activity)
            }
            R.id.nav_songs -> {

            }
            R.id.nav_artists -> {

            }
            R.id.nav_albums -> {

            }
            R.id.nav_playlists -> {

            }
            R.id.nav_settings -> {

            }
            R.id.nav_about -> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun onBackPressed(): Boolean {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
            return true
        }
        return false
    }


    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(com.timmahh.spotifyweb.R.menu.spotify, menu)
    }

}