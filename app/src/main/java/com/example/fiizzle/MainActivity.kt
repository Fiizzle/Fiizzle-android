package com.example.fiizzle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fiizzle.screens.AllFragment
import com.example.fiizzle.screens.BookFragment
import com.example.fiizzle.screens.LoginFragment
import com.example.fiizzle.screens.SignInFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        supportFragmentManager.beginTransaction().replace(R.id.main_layout, BookFragment())
//            .commitAllowingStateLoss()

        setContentView(R.layout.activity_main)
    }
}