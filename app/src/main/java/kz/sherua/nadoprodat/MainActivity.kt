package kz.sherua.nadoprodat

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kz.sherua.nadoprodat.fragment.SalesFragment
import androidx.appcompat.app.ActionBarDrawerToggle as ActionBarDrawerToggle1

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onBackPressed() {

        if (supportFragmentManager!!.backStackEntryCount > 0) {
            Log.d("here", "here")
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val navController = findNavController(R.id.nav_host_fragment)
        lvSells.setOnClickListener {
            navController.navigate(R.id.salesFragment)
        }
        lvStorage.setOnClickListener {
            navController.navigate(R.id.storageFragment)
        }
        lvBasket.setOnClickListener {
            navController.navigate(R.id.nav_home)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)


        val actionBarDrawerToggle =  object : ActionBarDrawerToggle1(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            
            override fun onDrawerStateChanged(newState: Int) {
                if (newState == DrawerLayout.STATE_SETTLING) {
                    if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_button)
                        lvNavTab.visibility = View.VISIBLE
                        lvMain.visibility = View.GONE
                    } else {
                        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_humburger)
                        lvNavTab.visibility = View.GONE
                        lvMain.visibility = View.VISIBLE
                    }
                }
                super.onDrawerStateChanged(newState)
            }

        }
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_humburger)
        searchView.setOnSearchClickListener{
            tvHeaderTab.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}