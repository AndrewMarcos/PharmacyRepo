package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;

import romance.pharmacy.andrew_marcos.pharmacyrepo.FormsSubmit.HttpRequest;

public class Medical_Data_Deliveries extends AppCompatActivity {
    public static ArrayList<String> images;
    public static ArrayList<String> messages;
    DBHelper dbHelper;
    ListView orderListView;
    String Name,Address,Phone,MobileNo,Code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_delivery);
        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("SharedPreference", Activity.MODE_PRIVATE);
        Name=sharedPref.getString("Name","");
        Address=sharedPref.getString("Address","");
        MobileNo=sharedPref.getString("Mobile","");
        Phone=sharedPref.getString("Phone","");
        Code=sharedPref.getString("Code","");
        Name=sharedPref.getString("Name","");
        images = new ArrayList<String>();
        messages = new ArrayList<String>();
        dbHelper = new DBHelper(this);
        Button sendButton =(Button) findViewById(R.id.button2);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postData();
                for(int i=1;i<=messages.size();i++){
                    dbHelper.deleteOrder(i);
                }
                messages.clear();
                images.clear();
                Toast.makeText(getBaseContext(),"قد تم إرسال طلبك",Toast.LENGTH_LONG);
                Cursor cursor;
                cursor = dbHelper.getOrder();
                try {
                    if (cursor.moveToFirst()) {
                        do {
                            images.add(cursor.getString(1));
                            messages.add(cursor.getString(2));
                        } while (cursor.moveToNext());
                    }
                } catch (Exception e) {

                }
               // Delivery_Adapter delivery_adapter = new Delivery_Adapter(images,messages,this);
                //orderListView.setAdapter(delivery_adapter);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Medical_Data_Deliveries.this,MedicalList.class);
                startActivity(in);
            }
        });
        orderListView = (ListView) findViewById(R.id.listView);
        Cursor cursor;
        cursor = dbHelper.getOrder();
        try {
            if (cursor.moveToFirst()) {
                do {
                    images.add(cursor.getString(1));
                    messages.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

        }
        Delivery_Adapter delivery_adapter = new Delivery_Adapter(images,messages,this);
        orderListView.setAdapter(delivery_adapter);
    }

    public void postData() {
        String fullUrl = getResources().getString(R.string.Delivery_Forms);
        Log.v("hi",fullUrl);
        HttpRequest mReq = new HttpRequest();
        Log.v("hello",messages.get(0));
        String data = "entry.1820322615=" + URLEncoder.encode(Name)+"&"+ "entry.446360774=" + URLEncoder.encode(Address)+"&"+
                "entry.724114047=" + URLEncoder.encode(Phone)+"&"+ "entry.79347183=" + URLEncoder.encode(MobileNo)+"&"+
                "entry.726027365=" + URLEncoder.encode(Code);/*+"&" + "entry.589023351="+URLEncoder.encode(images.get(0))+"&"
        +"entry.1504915603="+URLEncoder.encode(messages.get(0));
         + "entry.1876034914="+URLEncoder.encode(images.get(1))+"&"
                +"entry.449768214="+URLEncoder.encode(messages.get(1))+ "entry.787641434="+URLEncoder.encode(images.get(2))+"&"
                +"entry.44068653="+URLEncoder.encode(messages.get(2))+ "entry.1537704762="+URLEncoder.encode(images.get(3))+"&"
                +"entry.1756500272="+URLEncoder.encode(messages.get(3));*/

        String response = mReq.sendPost(fullUrl, data);
    }

    @Override
    public void onResume(){
        super.onResume();
        Cursor cursor;
        cursor = dbHelper.getOrder();
        images.clear();
        messages.clear();
        try {
            if (cursor.moveToFirst()) {
                do {
                    images.add(cursor.getString(1));
                    messages.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {

        }
        Delivery_Adapter delivery_adapter = new Delivery_Adapter(images,messages,this);
        orderListView.setAdapter(delivery_adapter);
    }
}
