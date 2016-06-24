package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class Medical_Data_Deliveries extends AppCompatActivity {
    public static ArrayList<String> images;
    public static ArrayList<String> messages;
    DBHelper dbHelper;
    ListView orderListView;
    String Name,Address,Phone,MobileNo,Code,id;
    String data;
    Firebase myFirebaseRef;
    Delivery_Adapter delivery_adapter;
    long deliveryNo =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_delivery);
        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("SharedPreference", Activity.MODE_PRIVATE);
        Query queryRef = MainActivity.myFirebaseRef.child("DeliveriesNo");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                deliveryNo = Long.parseLong(snapshot.getValue().toString());


            }
            @Override public void onCancelled(FirebaseError error) {

            }
        });
        Name=sharedPref.getString("Name","");
        Address=sharedPref.getString("Address","");
        MobileNo=sharedPref.getString("Mobile","");
        Phone=sharedPref.getString("Phone","");
        Code=sharedPref.getString("Code","");
        id=sharedPref.getString("ID","");
        images = new ArrayList<String>();
        messages = new ArrayList<String>();
        dbHelper = new DBHelper(this);
        Button sendButton =(Button) findViewById(R.id.button2);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        postData();
                    }
                });
                t.start();
                for(int i=1;i<=messages.size();i++){
                    dbHelper.deleteOrder(i);
                }
                messages.clear();
                images.clear();
                Toast.makeText(Medical_Data_Deliveries.this,"قد تم إرسال طلبك",Toast.LENGTH_LONG);
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
                finish();

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
        delivery_adapter = new Delivery_Adapter(images,messages,this);
        orderListView.setAdapter(delivery_adapter);
    }

    public void postData() {
      /*  String fullUrl = getResources().getString(R.string.Delivery_Forms);
        HttpRequest mReq1 = new HttpRequest();
        data="";
        switch(messages.size()) {
            case 1:
                data = "entry.1820322615=" + URLEncoder.encode(Name) + "&" + "entry.446360774=" + URLEncoder.encode(Address) + "&" +
                        "entry.724114047=" + URLEncoder.encode(Phone) + "&" + "entry.79347183=" + URLEncoder.encode(MobileNo) + "&" +
                        "entry.726027365=" + URLEncoder.encode(Code) + "&" + "entry.589023351=" + URLEncoder.encode(images.get(0)) + "&"
                        + "entry.1504915603=" + URLEncoder.encode(messages.get(0)) + "&" + "entry.1876034914=" + URLEncoder.encode("") + "&"
                        + "entry.449768214=" + URLEncoder.encode("") + "&" + "entry.787641434=" + URLEncoder.encode("") + "&"
                        + "entry.44068653=" + URLEncoder.encode("") + "&" + "entry.1537704762=" + URLEncoder.encode("") + "&"
                        + "entry.1756500272=" + URLEncoder.encode("");break;
            case 2:
                data = "entry.1820322615=" + URLEncoder.encode(Name) + "&" + "entry.446360774=" + URLEncoder.encode(Address) + "&" +
                        "entry.724114047=" + URLEncoder.encode(Phone) + "&" + "entry.79347183=" + URLEncoder.encode(MobileNo) + "&" +
                        "entry.726027365=" + URLEncoder.encode(Code) + "&" + "entry.589023351=" + URLEncoder.encode(images.get(0)) + "&"
                        + "entry.1504915603=" + URLEncoder.encode(messages.get(0)) + "&" + "entry.1876034914=" + URLEncoder.encode(images.get(1)) + "&"
                        + "entry.449768214=" + URLEncoder.encode(messages.get(1)) + "&" + "entry.787641434=" + URLEncoder.encode("") + "&"
                        + "entry.44068653=" + URLEncoder.encode("") + "&" + "entry.1537704762=" + URLEncoder.encode("") + "&"
                        + "entry.1756500272=" + URLEncoder.encode("");break;

            case 3:
                data = "entry.1820322615=" + URLEncoder.encode(Name) + "&" + "entry.446360774=" + URLEncoder.encode(Address) + "&" +
                        "entry.724114047=" + URLEncoder.encode(Phone) + "&" + "entry.79347183=" + URLEncoder.encode(MobileNo) + "&" +
                        "entry.726027365=" + URLEncoder.encode(Code) + "&" + "entry.589023351=" + URLEncoder.encode(images.get(0)) + "&"
                        + "entry.1504915603=" + URLEncoder.encode(messages.get(0)) + "&" + "entry.1876034914=" + URLEncoder.encode(images.get(1)) + "&"
                        + "entry.449768214=" + URLEncoder.encode(messages.get(1)) + "&" + "entry.787641434=" + URLEncoder.encode(images.get(2)) + "&"
                        + "entry.44068653=" + URLEncoder.encode(messages.get(2)) + "&" + "entry.1537704762=" + URLEncoder.encode("") + "&"
                        + "entry.1756500272=" + URLEncoder.encode("");break;
            case 4:
             data = "entry.1820322615=" + URLEncoder.encode(Name) + "&" + "entry.446360774=" + URLEncoder.encode(Address) + "&" +
                    "entry.724114047=" + URLEncoder.encode(Phone) + "&" + "entry.79347183=" + URLEncoder.encode(MobileNo) + "&" +
                    "entry.726027365=" + URLEncoder.encode(Code) + "&" + "entry.589023351=" + URLEncoder.encode(images.get(0)) + "&"
                    + "entry.1504915603=" + URLEncoder.encode(messages.get(0)) + "&" + "entry.1876034914=" + URLEncoder.encode(images.get(1)) + "&"
                    + "entry.449768214=" + URLEncoder.encode(messages.get(1)) + "&" + "entry.787641434=" + URLEncoder.encode(images.get(2)) + "&"
                    + "entry.44068653=" + URLEncoder.encode(messages.get(2)) + "&" + "entry.1537704762=" + URLEncoder.encode(images.get(3)) + "&"
                    + "entry.1756500272=" + URLEncoder.encode(messages.get(3));break;
        }
        String response1 = mReq1.sendPost(fullUrl, data);*/
        MainActivity.myFirebaseRef.child("DeliveriesNo").setValue(deliveryNo+1);
        MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("PersonID").setValue(id);
        for(int i=0;i<delivery_adapter.getCount();i++) {
            MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("Images").child(i+"").setValue(images.get(i).toString());
            MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("Messages").child(i+"").setValue(messages.get(i).toString());
        }

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
