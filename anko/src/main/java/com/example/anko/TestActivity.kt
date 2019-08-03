package com.example.anko

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class TestActivity : AppCompatActivity() {

    companion object {
        fun start(ctx: Context) {
            ctx.startActivity(Intent(ctx, TestActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

}