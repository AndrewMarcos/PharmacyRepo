package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

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
        Name=sharedPref.getString("Name","NoName");
        Address=sharedPref.getString("Address","");
        MobileNo=sharedPref.getString("Mobile","");
        Phone=sharedPref.getString("Phone","");
        Code=sharedPref.getString("Code","");
        id=sharedPref.getString("ID","0");
        images = new ArrayList<String>();
        messages = new ArrayList<String>();
        dbHelper = new DBHelper(this);
        Button sendButton =(Button) findViewById(R.id.button2);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true) {
                    if (messages.size() != 0 && images.size() != 0) {
                        messages.clear();
                        images.clear();
                        // notification
                        new DownloadFilesTask().execute("test");



                        Toast.makeText(getBaseContext(), "قد تم إرسال طلبك", Toast.LENGTH_LONG).show();
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
                        Cursor c = dbHelper.getOrder();
                        try {
                            if (c.moveToFirst()) {
                                do {
                                    int id = c.getInt(0);
                                    dbHelper.deleteOrder(id);

                                } while (c.moveToNext());
                            }
                        } catch (Exception e) {

                        }

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                postData();
                            }
                        });
                        t.start();
                        finish();
                    } else {
                        Toast.makeText(Medical_Data_Deliveries.this, "بالرجاء إضافة طلب جديد", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Medical_Data_Deliveries.this,"بالرجاء التأكد من إتصال الانترنت",Toast.LENGTH_LONG).show();

                }
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
        final Cursor cursor;
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
        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final CharSequence[] items = {
                        "تعديل", "حذف"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Medical_Data_Deliveries.this);
                builder.setTitle("اختر الأمر المناسب....");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("تعديل")) {
                            Intent editIntent = new Intent(Medical_Data_Deliveries.this, MedicalList.class);
                            Cursor c = dbHelper.getOrder();
                            c.move(i);
                            int id = c.getInt(0);
                            editIntent.putExtra("image", c.getString(1));
                            editIntent.putExtra("message", c.getString(2));
                            editIntent.putExtra("edit", true);
                            editIntent.putExtra("ID", id);
                            startActivity(editIntent);

                        } else if (items[item].equals("حذف")) {
                            Cursor c = dbHelper.getOrder();
                            c.move(i);
                            int id = c.getInt(0);
                            dbHelper.deleteOrder(id);
                            messages.clear();
                            images.clear();
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
                            delivery_adapter = new Delivery_Adapter(images, messages, Medical_Data_Deliveries.this);
                            orderListView.setAdapter(delivery_adapter);
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });
    }




    public void postData() {

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+2"));
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String dayLongName = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());

        new DownloadFilesTask().execute("aa");

        MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("PersonID").setValue(id);
        MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("Time").setValue(c.getTime());
        MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("Sender").setValue(Name);
        MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("senderCode").setValue(Code);
        MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("Seen").setValue("0");

        for(int i=0;i<delivery_adapter.getCount();i++) {
            MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("Images").child(i+"").setValue(images.get(i).toString());
            MainActivity.myFirebaseRef.child("Delivery").child((deliveryNo+1)+"").child("Messages").child(i+"").setValue(messages.get(i).toString());
        }
        MainActivity.myFirebaseRef.child("DeliveriesNo").setValue(deliveryNo+1);
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

    private class DownloadFilesTask extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... urls) {
            int count = urls.length;
            long totalSize = 0;

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"to\": \"/topics/admin\",\"data\":" +
                    " {\"message\":\"This is a Firebase test Cloud Messaging Topic Message!\"," +
                    "\"text\":\"" + urls[0] + "\"," +
                    "\"title\": \"صيدلية\"}}");
            Request request = new Request.Builder()
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("authorization", "key=AAAATZMXjSE:APA91bE5tdGwQFMAHGl5bIZudRAb0cAtrXdMsKTOL_LGjlkq1uCWFY6Tn-jj4QhX_2gioXMjQ3njcCOXKazz8Zsqx5mzO7NIiezie4UK9pINs-btNesWg7qg4M0Z1V0H9HEGNfkrENyN")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "c5b46db6-dd4e-4a39-f6bc-5d2f8bf42bca")
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }
}
