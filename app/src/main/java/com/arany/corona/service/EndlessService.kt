package com.arany.corona.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.os.SystemClock
import android.widget.Toast
import com.arany.corona.*
import com.arany.corona.ModelExtensions.isVaccinationCenterAvailableForAge45
import com.arany.corona.api.ApiService
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.karn.notify.Notify
import kotlinx.coroutines.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class EndlessService : Service(), CoroutineScope by CoroutineScope(Dispatchers.Main) {
    @Inject lateinit var apiService: ApiService
    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action
            when (action) {
                Actions.START.name -> startService()
                Actions.STOP.name -> stopService()
                else -> Timber.e("This should never happen. No action in the received intent")
            }
        } else {
            Timber.e(
                "with a null intent. It has been probably restarted by the system."
            )
        }
        // by returning this we make sure the service is restarted if the system kills the service
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Timber.e("The service has been created".toUpperCase())
        val notification = createNotification()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("The service has been destroyed".toUpperCase())
        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        val restartServiceIntent = Intent(applicationContext, EndlessService::class.java).also {
            it.setPackage(packageName)
        }
        val restartServicePendingIntent: PendingIntent = PendingIntent.getService(this, 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT)
        applicationContext.getSystemService(Context.ALARM_SERVICE)
        val alarmService: AlarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent)
    }
    
    private fun startService() {
        if (isServiceStarted) return
        Timber.e("Starting the foreground service task")
        Toast.makeText(this, "Service starting its task", Toast.LENGTH_SHORT).show()
        isServiceStarted = true
        setServiceState(this, ServiceState.STARTED)

        // we need this lock so our service gets not affected by Doze Mode
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "EndlessService::lock").apply {
                    acquire()
                }
            }
        //LocalBroadcastManager.getInstance(requireContext()).registerReceiver(activeAddressBroadcastReceiver, IntentFilter(Constants.ACTIVE_LOCATION_BROADCAST))
        // we're starting a loop in a coroutine
        CoroutineScope(coroutineContext).launch {
            while (isServiceStarted) {
                val vaccinationCenters = withContext(Dispatchers.IO) {
                    try {
                        getVaccinationCenters()
                    }catch (e: Exception){
                        e.printStackTrace()
                        arrayListOf<VaccinationCenterAbstract.VaccinationCenter>()
                    }
                }
                if(vaccinationCenters.isVaccinationCenterAvailableForAge45()){
                    Notify.with(applicationContext)
                        .content {
                            title = "Vaccination centers available"
                            text = "Vaccination centers available for 45+"
                        }
                        .meta {
                            timeout = 10000
                            cancelOnClick = true
                        }
                        .alerting("randomAlertingKey") {
                            sound = Uri.parse("android.resource://" + applicationContext.packageName + "/" + R.raw.alarm)
                            vibrationPattern = arrayListOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                        }.show()
                }
                delay(15000)
            }
            Timber.e("End of the loop for the service")
        }
    }

    private fun stopService() {
        Timber.e("Stopping the foreground service")
        Toast.makeText(this, "Service stopping", Toast.LENGTH_SHORT).show()
        try {
            wakeLock?.let {
                if (it.isHeld) {
                    it.release()
                }
            }
            stopForeground(true)
            stopSelf()
        } catch (e: Exception) {
            Timber.e("Service stopped without being started: ${e.message}")
        }
        isServiceStarted = false
        setServiceState(this, ServiceState.STOPPED)
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "ENDLESS SERVICE CHANNEL"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                notificationChannelId,
                "Endless Service notifications channel",
                NotificationManager.IMPORTANCE_HIGH
            ).let {
                it.description = "Endless Service channel"
                it.enableLights(true)
                it.lightColor = Color.RED
                it.enableVibration(true)
                it.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                it
            }
            notificationManager.createNotificationChannel(channel)
        }

        val pendingIntent: PendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }

        val builder: Notification.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) Notification.Builder(
            this,
            notificationChannelId
        ) else Notification.Builder(this)

        return builder
            .setContentTitle("Vaccinator")
            .setContentText("Checking for 45+ vaccination centers...")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setTicker("Ticker text")
            .setPriority(Notification.PRIORITY_HIGH) // for under android 26 compatibility
            .build()
    }

    private suspend fun getVaccinationCenters(): ArrayList<VaccinationCenterAbstract.VaccinationCenter>{
        val vaccinationCenters = ArrayList<VaccinationCenterAbstract.VaccinationCenter>()
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().time)
        val vaccinationCenterAbstractResponse = apiService.getVaccinationCenters(PreferenceHelper.getDistrictId(), date)
        var vaccinationCenterAbstract: VaccinationCenterAbstract?= null
        if(vaccinationCenterAbstractResponse.isSuccessful){
            vaccinationCenterAbstract = vaccinationCenterAbstractResponse.body()
        }
        vaccinationCenters.addAll(vaccinationCenterAbstract?.vaccinationCenters?: arrayListOf())
        return vaccinationCenters
    }

}
