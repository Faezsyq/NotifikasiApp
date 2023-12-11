package com.example.notifikasiapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    public static final int notifikasi = 1;
    public String chID = "ch_id";
    public String chName = "notif";
    Button btnNotifikasi;
    EditText etJudul, etPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotifikasi = findViewById(R.id.btnNotifikasi);
        etJudul = findViewById(R.id.etJudul);
        etPesan = findViewById(R.id.etPesan);

        btnNotifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                tampilNotifikasi(etJudul.getText().toString(), etPesan.getText().toString(),i);
            }
        });
    }

    private void tampilNotifikasi( String Pesan,String Judul, Intent i) {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, notifikasi, i, PendingIntent.FLAG_MUTABLE);

        NotificationManager notificationManager = (NotificationManager) MainActivity.this.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, chID)
                .setSmallIcon(R.drawable.ic_notif)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_notif))
                .setContentTitle(Judul)
                .setContentText(Pesan)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(chID, chName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(chName);
            builder.setChannelId(chID);
            if (notificationManager !=null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();

        if (notificationManager !=null){
            notificationManager.notify(notifikasi, notification);
        }
    }
}