package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.firebase.client.Firebase;

import romance.pharmacy.andrew_marcos.pharmacyrepo.Services.myAppNotificationService;

public class MainActivity extends AppCompatActivity {
    static Firebase myFirebaseRef;
    public static boolean onOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onOpen=true;
        Intent mServiceIntent = new Intent(this, myAppNotificationService.class);
        startService(mServiceIntent);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://romance-pharmacy.firebaseio.com/");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String[] menuList =getResources().getStringArray(R.array.menu_items);

        GridView mainGridView = (GridView)findViewById(R.id.gridView);
        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(menuList,this);
        mainGridView.setAdapter(mainMenuAdapter);
        mainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:;break;
                    case 1:Intent in = new Intent(MainActivity.this, Medical_Data_Deliveries.class);
                        startActivity(in);break;
                    case 2 :Intent intent = new Intent(MainActivity.this, News.class);
                        startActivity(intent);break;

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
