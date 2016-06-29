package romance.pharmacy.andrew_marcos.pharmacyrepo.Services;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import romance.pharmacy.andrew_marcos.pharmacyrepo.News;
import romance.pharmacy.andrew_marcos.pharmacyrepo.R;

/**
 * Created by MorcosS on 6/26/16.
 */

public class myAppNotificationService extends IntentService {
    View rootView;
    String newsId;
    static Firebase myNotificationFirebase;

    public myAppNotificationService(String name) {
        super("Notification app Service");
    }
    public myAppNotificationService() {
        super("Notification app Service");
    }
    public void setView(View view){
        view = rootView;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(final Intent intent) {

            Firebase.setAndroidContext(this);
        final SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("SharedPreference", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        myNotificationFirebase = new Firebase(getString(R.string.MyFirebase_Database)+"News");
            myNotificationFirebase.addChildEventListener(new ChildEventListener() {
                // Retrieve new posts as they are added to the database
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                    Firebase myOwnFirebase = new Firebase(getString(R.string.MyFirebase_Database));
                    myOwnFirebase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newsId = dataSnapshot.child("NewsNo").getValue().toString();
                            Log.e("Allo",newsId+"ana elgdid");
                            if(!sharedPref.getString("NewsID","").equals(newsId)) {

                                NotificationManager mNM;
                                mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                // Set the icon, scrolling text and timestamp
                                NotificationCompat.Builder mBuilder =
                                        new NotificationCompat.Builder(getBaseContext())
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .setContentTitle("News")
                                                .setContentText("Guess What a new offer is on the way!").setAutoCancel(true);
                                Intent resultIntent = new Intent(getBaseContext(), News.class);
                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
                                stackBuilder.addParentStack(News.class);
                                // Adds the Intent that starts the Activity to the top of the stack
                                stackBuilder.addNextIntent(resultIntent);
                                PendingIntent resultPendingIntent =
                                        stackBuilder.getPendingIntent(
                                                0,
                                                PendingIntent.FLAG_UPDATE_CURRENT
                                        );
                                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                mBuilder.setSound(alarmSound);
                                mBuilder.setContentIntent(resultPendingIntent);
                                int mNotificationId = 001;
                                // Gets an instance of the NotificationManager service
                                NotificationManager mNotifyMgr =
                                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                // Builds the notification and issues it.
                                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                                editor.putString("NewsID",newsId);
                                editor.commit();

                            }

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    Log.e("Allo",sharedPref.getString("NewsID","")+" tany");
                                    }


                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });



    }
}
