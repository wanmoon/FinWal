package com.wanmoon.finwal.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wanmoon.finwal.R;

/**
 * Created by Wanmoon on 9/21/2017 AD.
 */

public class NotificationPage extends AppCompatActivity implements View.OnClickListener {

    public Button noti;

    public String title = "FinWal : Your saving goal";
    public String text = "Did you save money to your goal?";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_noti);

        noti = (Button) findViewById(R.id.buttonNoti);
        noti.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == noti) {
            notificationPage(v);
        }
    }

    public void notificationPage(View view){

        Notification.Builder notification = new Notification.Builder(getApplicationContext());
        notification.setSmallIcon(R.mipmap.logo);
        notification.setContentTitle(title);
        notification.setContentText(text);
        notification.setAutoCancel(true);


       // Notification notification  = new Notification.Builder(this)
            //    .setContentTitle("FinWal : Your saving goal")
//                .setContentText("Did you save money to your goal?")
          //      .setSmallIcon(R.mipmap.logo) // icon app
                //  .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .addAction(R.drawable.noti_correct, "Yes", yes_pendingIntent) // yes
//                .addAction(R.drawable.noti_error, "No", no_pendingIntent)// no
//                .build();

        // prepare intent which is triggered if the
        // notification is selected
        Intent yes_intent = new Intent(this, NotificationReceiver.class);
        yes_intent.setAction(NotificationReceiver.YES_ACTION);
        PendingIntent yes_pendingIntent = PendingIntent.getActivity(this, 0, yes_intent, 0);
        notification.addAction(R.drawable.noti_correct, "Yes", yes_pendingIntent);


        Intent no_intent = new Intent(this, NotificationReceiver.class);
        no_intent.setAction(NotificationReceiver.NO_ACTION);
        PendingIntent no_pendingIntent = PendingIntent.getActivity(this, 0, no_intent, 0);
        notification.addAction(R.drawable.noti_error, "No", no_pendingIntent);


        // use System.currentTimeMillis() to have a unique ID for the pending intent

        // build notification
        // the addAction re-use the same intent to keep the example short


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1000, notification.getNotification());

        // hide the notification after its selected
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;

    }
}
