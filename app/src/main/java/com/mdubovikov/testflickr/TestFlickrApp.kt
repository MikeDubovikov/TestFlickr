package com.mdubovikov.testflickr

import android.app.Application
import com.mdubovikov.testflickr.di.ApplicationComponent
import com.mdubovikov.testflickr.di.DaggerApplicationComponent

class TestFlickrApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}