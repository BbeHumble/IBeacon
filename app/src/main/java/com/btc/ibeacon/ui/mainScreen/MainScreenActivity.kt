package com.btc.ibeacon.ui.mainScreen

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.btc.ibeacon.R
import com.btc.ibeacon.ui.beacon.BeaconFragment
import com.btc.ibeacon.ui.ble.BleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class MainScreenActivity : MvpAppCompatActivity(),
    MainScreenView,
    BottomNavigationView.OnNavigationItemSelectedListener {

    @InjectPresenter
    lateinit var presenter: MainScreenPresenter

    @ProvidePresenter
    fun providePresenter(): MainScreenPresenter {
        return MainScreenPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigationView()
        if (savedInstanceState == null) {
            presenter.loadFragment(supportFragmentManager, BleFragment())
        }
        requestPermission()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this@MainScreenActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainScreenActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this@MainScreenActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@MainScreenActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    if ((ContextCompat.checkSelfPermission(
                            this@MainScreenActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) ==
                                PackageManager.PERMISSION_GRANTED)
                    ) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                        presenter.loadFragment(supportFragmentManager, BleFragment())
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        if (navigation.selectedItemId != menuItem.itemId) {
            when (menuItem.itemId) {
                R.id.ble_menu -> {
                    presenter.loadFragment(supportFragmentManager, BleFragment())
                }
                R.id.beacons_menu -> {
                    presenter.loadFragment(supportFragmentManager, BeaconFragment())

                }
            }
        }
        return true
    }

    private fun initBottomNavigationView() {
        navigation.setOnNavigationItemSelectedListener(this)
        navigation.menu.findItem(R.id.ble_menu).isChecked = true
    }

}