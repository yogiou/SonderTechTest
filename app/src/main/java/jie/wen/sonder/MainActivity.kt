package jie.wen.sonder

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jie.wen.sonder.model.dto.PassengerDataDTO
import jie.wen.sonder.other.Constants.Companion.NO_INTERNET_MESSAGE
import jie.wen.sonder.repository.db.AirlineDataDatabase
import jie.wen.sonder.ui.fragment.AirlineDetailsFragment
import jie.wen.sonder.ui.fragment.PassengerListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val PASSENGER_DETAILS = "passenger_details"
    }

    private lateinit var connectivityManager: ConnectivityManager

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar?.show()

        if (savedInstanceState == null) {
            goToPassengerListFragment()
        }

        // listen to network change
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                // take action when network connection is gained
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                // take action when network connection lost
                showNoInternetConnectionError()
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    private fun goToPassengerListFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, PassengerListFragment.newInstance())
            .commitNow()
    }

    private fun showNoInternetConnectionError() {
        Snackbar.make(window.decorView, NO_INTERNET_MESSAGE, Snackbar.LENGTH_SHORT).show()
    }

    fun goToPassengerDetailsFragment(passengerDataDTO: PassengerDataDTO) {
        supportFragmentManager.beginTransaction().apply {
            val fragment = AirlineDetailsFragment.newInstance()
            val fragBundle = Bundle().apply {
                putSerializable(PASSENGER_DETAILS, passengerDataDTO)
            }
            fragment.arguments = fragBundle
            replace(R.id.container, fragment)
            commit()
            addToBackStack(fragment.tag)
        }
    }

    override fun onBackPressed() {
        when  {
            supportFragmentManager.backStackEntryCount > 0 -> supportFragmentManager.popBackStack()
            else -> super.onBackPressed()
        }
    }

    override fun onDestroy() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        super.onDestroy()
    }

    // check if network is available
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

    fun showActionBarBackButton(show: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(show)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    // TODO: will use if need to store in local DB
    private fun getAirlineDataDB() : RoomDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AirlineDataDatabase::class.java, "airlinedata"
        ).build()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }
}