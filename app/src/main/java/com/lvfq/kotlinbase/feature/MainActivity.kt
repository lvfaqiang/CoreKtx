package com.lvfq.kotlinbase.feature

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import cn.basic.core.base.BasicActivity
import cn.basic.core.common.ILoading
import com.lvfq.kotlinbase.R
import com.lvfq.kotlinbase.databinding.ActivityMainBinding
import com.lvfq.kotlinbase.views.LoadingView


class MainActivity : BasicActivity<ActivityMainBinding>() {

    override fun bindingView(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {

//        val navView: BottomNavigationView = findViewById(R.id.nav_view)

//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
////        setupActionBarWithNavController(navController, appBarConfiguration)
//        binding.navView.setupWithNavController(navController)
    }

    override val loadingView: ILoading
        get() = LoadingView.get(this)

//    override val binding: ActivityMainBinding
//        get() = ActivityMainBinding.inflate(layoutInflater)
//

}
