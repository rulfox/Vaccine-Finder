package com.arany.corona.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import timber.log.Timber

class StartReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ) {
            when{
                getServiceState(context) == ServiceState.STARTED -> {
                    Intent(context, EndlessService::class.java).also {
                        it.action = Actions.START.name
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Timber.e("Starting the service in >=26 Mode from a BroadcastReceiver")
                            context.startForegroundService(it)
                            return
                        }
                        Timber.e("Starting the service in < 26 Mode from a BroadcastReceiver")
                        context.startService(it)
                    }
                }
                else -> {

                }
            }
        }
    }
}
