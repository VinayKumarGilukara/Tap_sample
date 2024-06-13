package com.example.tap_sample.network

import com.example.tap_sample.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface Component {
    fun inject(mainActivity: MainActivity)
}