package com.tugcearas.sweety.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.tugcearas.sweety.R
import com.tugcearas.sweety.databinding.ActivityMainBinding
import com.tugcearas.sweety.util.extensions.gone
import com.tugcearas.sweety.util.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavView,navController)

        navController.addOnDestinationChangedListener{_,destination,_ ->
            when(destination.id){
                R.id.splashScreen -> {
                    binding.bottomNavView.gone()
                    window.statusBarColor = ContextCompat.getColor(this,R.color.splash_status_bar_color)
                }
                R.id.signinScreen,
                R.id.signupScreen,
                R.id.successOrderScreen -> {
                    binding.bottomNavView.gone()
                    window.statusBarColor = ContextCompat.getColor(this,R.color.platinum)
                }
                R.id.paymentScreen,
                R.id.detailScreen -> {
                    binding.bottomNavView.gone()
                    window.statusBarColor = ContextCompat.getColor(this,R.color.dark_pink)
                }
                else -> {
                    binding.bottomNavView.visible()
                    window.statusBarColor = ContextCompat.getColor(this,R.color.dark_pink)
                }
            }
        }
    }
}