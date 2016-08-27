package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    static Firebase myFirebaseRef;
    ImageView imageView_facebook,imageView_map,imageView_call;
    Button imageView_news,imageView_delivery;
    public static boolean onOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*Intent mServiceIntent = new Intent(this, myAppNotificationService.class);
        startService(mServiceIntent);*/
        System.out.println("Registration.onTokenRefresh TOKEN: " + FirebaseInstanceId.getInstance().getToken() );
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Firebase.setAndroidContext(this);
        final SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("SharedPreference", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        myFirebaseRef = new Firebase(getString(R.string.MyFirebase_Database));
        imageView_call=(ImageView)findViewById(R.id.imageView_call);
        imageView_delivery=(Button)findViewById(R.id.imageView_deleviry);
        imageView_facebook=(ImageView)findViewById(R.id.imageView_facebook);
        imageView_map=(ImageView)findViewById(R.id.imageView_map);
        imageView_news=(Button)findViewById(R.id.imageView_News);
        imageView_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=Mobil");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        imageView_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, Medical_Data_Deliveries.class);
                startActivity(in);
            }
        });

        imageView_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, News.class);
                startActivity(intent);
            }
        });
        imageView_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        imageView_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "0222015544"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
