package romance.pharmacy.andrew_marcos.pharmacyrepo.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import romance.pharmacy.andrew_marcos.pharmacyrepo.MainActivity;
import romance.pharmacy.andrew_marcos.pharmacyrepo.News;
import romance.pharmacy.andrew_marcos.pharmacyrepo.R;

/**
 * Created by MorcosS on 6/26/16.
 */
public class myAppNotificationService extends IntentService {
    View rootView;
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
    protected void onHandleIntent(Intent intent) {
        if (!MainActivity.onOpen) {
            Firebase.setAndroidContext(this);
            myNotificationFirebase = new Firebase("https://romance-pharmacy.firebaseio.com/News");
            myNotificationFirebase.addChildEventListener(new ChildEventListener() {
                // Retrieve new posts as they are added to the database
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                    NotificationManager mNM;
                    mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    // Set the icon, scrolling text and timestamp
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getBaseContext())
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle("News")
                                    .setContentText("Guess What a new offer is on the way!");
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
                    mBuilder.setContentIntent(resultPendingIntent);
                    int mNotificationId = 001;
                    // Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
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

        } else {
            MainActivity.onOpen = false;
        }
    }
}
