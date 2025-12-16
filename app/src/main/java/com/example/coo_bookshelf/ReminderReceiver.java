package com.example.coo_bookshelf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderReceiver extends BroadcastReceiver {

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {

        // Make sure notification permission is granted
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            return;  // Exit if permission is missing
        }

        // Build the notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "default")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)    // Notification icon
                        .setContentTitle("Bookshelf Reminder")              // Title text
                        .setContentText("See what’s next on your reading list!") // Message text
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);  // Priority level

        try {
            // Get notification manager and show notification
            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            manager.notify(1, builder.build());
        } catch (SecurityException e) {
            // Safety catch in case permission fails unexpectedly
        }
    }
}
