package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmailView;
    private EditText mAddressView, mMobileView, mCodeView, mFlatView,mFloorView;
    private View mProgressView;
    private View mLoginFormView;
    public static long id,newCodes,code;
    public static boolean checkedMySheet;
    public static int counter;
    Resources res;
    long size;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        size = 0;
        Firebase.setAndroidContext(this);
        final Firebase myFirebase = new Firebase("https://romance-pharmacy.firebaseio.com/");
        res = getResources();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedPreference", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        boolean isRegistered = sharedPref.getBoolean("IsRegistered", false);
        setContentView(R.layout.activity_login);
        if (isRegistered) {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(mainIntent);
            LoginActivity.this.finish();
        } else {
            counter = 0;
            Query queryRef = myFirebase.child("IDS");
            queryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    size = Long.parseLong(snapshot.getValue().toString());

                }

                @Override
                public void onCancelled(FirebaseError error) {
                    Toast.makeText(LoginActivity.this,"بالرجاء التأكد من التوصيل بالانترنت",Toast.LENGTH_LONG).show();
                }
            });
            Query queryRef1 = myFirebase.child("Codes");
            queryRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    newCodes = Long.parseLong(snapshot.getValue().toString());

                }

                @Override
                public void onCancelled(FirebaseError error) {
                    Toast.makeText(LoginActivity.this,"بالرجاء التأكد من التوصيل بالانترنت",Toast.LENGTH_LONG).show();
                }
            });
            mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
            mAddressView = (EditText) findViewById(R.id.address);
            mMobileView = (EditText) findViewById(R.id.mobile);
            mCodeView = (EditText) findViewById(R.id.code);
            mFloorView = (EditText) findViewById(R.id.floorNo);
            mFlatView = (EditText) findViewById(R.id.flatNo);
            final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (isConnected(LoginActivity.this)) {
                        if (mEmailView.getText().toString().isEmpty()) {
                            Toast.makeText(LoginActivity.this, "بالرجاء كتابة الاسم", Toast.LENGTH_LONG).show();
                        } else if (mAddressView.getText().toString().isEmpty()) {
                            Toast.makeText(LoginActivity.this, "بالرجاء كتابة العنوان", Toast.LENGTH_LONG).show();
                        } else if(mFloorView.getText().toString().isEmpty()|| !TextUtils.isDigitsOnly(mFloorView.getText().toString())){
                            Toast.makeText(LoginActivity.this, "بالرجاء كتابة رقم الدور صحيحاٍ", Toast.LENGTH_LONG).show();
                        }else if (mFlatView.getText().toString().isEmpty()|| !TextUtils.isDigitsOnly(mFlatView.getText().toString())) {
                            Toast.makeText(LoginActivity.this, "بالرجاء كتابة رقم الشقة صحيحا", Toast.LENGTH_LONG).show();
                        } else if (mMobileView.getText().toString().isEmpty()) {
                            Toast.makeText(LoginActivity.this, "بالرجاء كتابة رقم المحمول", Toast.LENGTH_LONG).show();
                        } else if(!checkBox.isChecked()&&((mCodeView.getText().toString().isEmpty()||!TextUtils.isDigitsOnly(mCodeView.getText().toString())))) {
                                    Toast.makeText(LoginActivity.this, "بالرجاء كتابة الكود صحيحا", Toast.LENGTH_LONG).show();

                        } else if (!TextUtils.isDigitsOnly(mMobileView.getText().toString()) || (mMobileView.getText().toString().length() < 11)) {
                            Toast.makeText(LoginActivity.this, "بالرجاء كتابة رقم المحمول", Toast.LENGTH_LONG).show();
                        } else {
                                id = size + 1;
                                code = newCodes+1;
                                editor.putString("Name", mEmailView.getText().toString());
                                editor.putString("Address", mAddressView.getText().toString());
                                editor.putString("Mobile", mMobileView.getText().toString());
                                editor.putString("Code", mCodeView.getText().toString());
                                editor.putString("Floor", mFloorView.getText().toString());
                                editor.putString("Flat", mFlatView.getText().toString());
                                editor.putBoolean("IsRegistered", true);
                                editor.putString("ID", id + "");
                                myFirebase.child("IDS").setValue(id + "");
                                myFirebase.child("SignIn").child(id + "").child("Name").setValue(mEmailView.getText().toString());
                                myFirebase.child("SignIn").child(id + "").child("Address").setValue(mAddressView.getText().toString());
                                myFirebase.child("SignIn").child(id + "").child("Mobile").setValue(mMobileView.getText().toString());
                                myFirebase.child("SignIn").child(id+"").child("Flat").setValue(mFlatView.getText().toString());
                                myFirebase.child("SignIn").child(id+"").child("Floor").setValue(mFloorView.getText().toString());
                                if(checkBox.isChecked()==false) {
                                    myFirebase.child("SignIn").child(id + "").child("Code").setValue(mCodeView.getText().toString());
                                    editor.putString("Code", mCodeView.getText().toString());
                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                                    builder1.setMessage(" قد تم التسجيل بنجاح الكود هو : "+mCodeView.getText())
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // FIRE ZE MISSILES!
                                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                                    LoginActivity.this.startActivity(mainIntent);
                                                    LoginActivity.this.finish();
                                                }
                                            });

                                    // Create the AlertDialog object and return it
                                    builder1.show();
                                }else{
                                    myFirebase.child("Codes").setValue(code+"");
                                    myFirebase.child("SignIn").child(id + "").child("Code").setValue(code+"");
                                    myFirebase.child("New_Customers").child(id + "").child("Name").setValue(mEmailView.getText().toString());
                                    myFirebase.child("New_Customers").child(id + "").child("Address").setValue(mAddressView.getText().toString());
                                    myFirebase.child("New_Customers").child(id + "").child("Mobile").setValue(mMobileView.getText().toString());
                                    myFirebase.child("New_Customers").child(id+"").child("Flat").setValue(mFlatView.getText().toString());
                                    myFirebase.child("New_Customers").child(id+"").child("Floor").setValue(mFloorView.getText().toString());
                                    myFirebase.child("New_Customers").child(id + "").child("Code").setValue(code+"");
                                    editor.putString("Code", code+"");
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage(" قد تم التسجيل بنجاح الكود هو : "+code)
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                                    LoginActivity.this.startActivity(mainIntent);
                                                    LoginActivity.this.finish();
                                                }
                                            });

                                    // Create the AlertDialog object and return it
                                     builder.show();
                                }
                                editor.commit();
                                }



                    } else {
                        Toast.makeText(LoginActivity.this, "بالرجاء التأكد من إتصال الانترنت", Toast.LENGTH_LONG).show();
                    }
                }
            });

            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);

        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                final HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                //urlc.connect();
               // return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
            return true;
        }
        return false;
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://romance.pharmacy.andrew_marcos.pharmacyrepo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://romance.pharmacy.andrew_marcos.pharmacyrepo/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

