package com.sample.permission

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.module.permissions.isLocationEnabled

class LocationBroadcastReceiver (private val context: Context) : BroadcastReceiver() {
    private val locationStateChange = MutableLiveData<Boolean>()

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        if (action == LocationManager.PROVIDERS_CHANGED_ACTION) {
            val state = context.isLocationEnabled()
            locationStateChange.postValue(state)
        }
    }

    fun subscribeState() = locationStateChange
    /**
     * Listen to subscribeState() to receive updates
     */
    fun register() {
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        val receiverFlags = ContextCompat.RECEIVER_NOT_EXPORTED
        ContextCompat.registerReceiver(context, this, filter, receiverFlags)
    }

    fun unregister() {
        context.unregisterReceiver(this)
    }

}