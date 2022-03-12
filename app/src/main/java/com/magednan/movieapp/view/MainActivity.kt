package com.magednan.movieapp.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.magednan.movieapp.R
import com.magednan.movieapp.databinding.ActivityMainBinding
import com.magednan.movieapp.utils.InternetListener
import com.magednan.movieapp.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), InternetListener.ConnectivityReceiverListener {
   //vars
    private var message=""
    private lateinit var navHostFragment:NavHostFragment
    private val viewModel by viewModels<ViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflating the data binding
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        setSupportActionBar(binding.toolBar)

        // inflating the navigation
      navHostFragment = (supportFragmentManager
          .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment!!.navController)

        //registerReceiver
        registerReceiver(InternetListener() , IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

   //setup receiver
    override fun onNetworkConnectionChanger(isConnected: Boolean) {
        shoNetworkMessage(isConnected)
    }
    //showing Network status Message
    private fun shoNetworkMessage(isConnected: Boolean) {
        if (isConnected){
            message="You are Online "
            val snackBar = Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            snackBar.show()
        }else{
            message="You are Offline "
            val snackBar = Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            snackBar.show()
        }
    }

    override fun onResume() {
        super.onResume()
        InternetListener.connectivityReceiverListener = this
    }

    //inflating search bar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
               val menuInflater = menuInflater
        menuInflater.inflate(R.menu.op_menu, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
               if (s.length >=2){
                val bundle=Bundle()
                bundle.putString("search",s)
                navHostFragment?.findNavController()?.navigate(R.id.searchFragment,bundle)}
                else{
                   Toast.makeText(this@MainActivity, "Please Enter a valid name", Toast.LENGTH_LONG).show()

               }
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === R.id.show_all) {
        lifecycleScope.launch{
         viewModel.deleteHiddenItem()}
            return true
        } else return super.onOptionsItemSelected(item)
        return super.onOptionsItemSelected(item)
    }
}