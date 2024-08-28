package com.oms.fashionhub

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.common.net.InternetDomainName
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val channelId = "notification_channel"
const val channelName= "com.oms.fashionhub"
class MyFirebaseMessagingService : FirebaseMessagingService() {

    //generate the notification
    //attach the notification created  with the  custom layout
    //show the notification

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if(remoteMessage.getNotification() != null){
            generatenotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }
    }


    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(tittle: String, message: String):RemoteViews{

        val remoteView = RemoteViews("com.oms.fashionhub",R.layout.notification)
        remoteView.setTextViewText(R.id.title,tittle)
        remoteView.setTextViewText(R.id.message,message)
        remoteView.setImageViewResource(R.id.notilogo,R.drawable.vastrahublogo)

        return remoteView

    }


    fun generatenotification(tittle:String, message: String){

        val intent= Intent(this, SignupPage::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)


        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
        )


        //channelid and channelname

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.vastrahublogo)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(tittle,message))

        val notificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0,builder.build())
    }


}