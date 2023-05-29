package com.hejercherbib.fdj.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hejercherbib.fdj.android.R
import com.hejercherbib.fdj.android.ui.teamScreen.TeamsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TeamsFragment.newInstance())
                .commitNow()
        }
    }
}
