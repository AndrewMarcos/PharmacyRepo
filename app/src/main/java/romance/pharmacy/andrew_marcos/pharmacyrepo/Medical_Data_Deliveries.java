package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;

import romance.pharmacy.andrew_marcos.pharmacyrepo.FormsSubmit.HttpRequest;

public class Medical_Data_Deliveries extends AppCompatActivity {
    public static ArrayList<String> images;
    public static ArrayList<String> messages;
    DBHelper dbHelper;
    ListView orderListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_delivery);
        images = new ArrayList<String>();
        messages = new ArrayList<String>();
        dbHelper = new DBHelper(this);
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
        String fullUrl = getResources().getString(R.string.Regestiration_Forms);
        HttpRequest mReq = new HttpRequest();
        String name,address,telephone,mobile,code;
        /*String data = "entry.902841453=" + URLEncoder.encode(name)+"&"+ "entry.1743281797=" + URLEncoder.encode(address)+"&"+
                "entry.1507940231=" + URLEncoder.encode(telephone)+"&"+ "entry.1447172494=" + URLEncoder.encode(mobileNo)+"&"+
                "entry.433899888=" + URLEncoder.encode(code)+"&" + "entry.551037592=" + URLEncoder.encode(myId+"");*///

        //String response = mReq.sendPost(fullUrl, data);
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
