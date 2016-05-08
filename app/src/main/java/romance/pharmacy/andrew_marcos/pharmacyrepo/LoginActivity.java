package romance.pharmacy.andrew_marcos.pharmacyrepo;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.URLEncoder;
import romance.pharmacy.andrew_marcos.pharmacyrepo.FormsSubmit.HttpRequest;
import romance.pharmacy.andrew_marcos.pharmacyrepo.Requests.UsersIds;

public class LoginActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmailView;
    private EditText mAddressView,mMobileView,mCodeView,mPhoneView;
    private View mProgressView;
    private View mLoginFormView;
    public static int id;
    public static boolean checkedMySheet;
    public static int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("SharedPreference",Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        boolean isRegistered = sharedPref.getBoolean("IsRegistered",false);
        setContentView(R.layout.activity_login);
        if(isRegistered) {
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(mainIntent);
            LoginActivity.this.finish();
        }else {
            checkedMySheet = false;
            String url = "https://spreadsheets.google.com/feeds/list/1vCINRHNp8yrdvjJkn_KA5c_WnhoRVeczySwNA7yhRh0/3/public/values?alt=json";
            final UsersIds usersIds = new UsersIds(url, getBaseContext());
            counter = 0;
            mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
            mAddressView = (EditText) findViewById(R.id.address);
            mMobileView = (EditText) findViewById(R.id.mobile);
            mCodeView = (EditText) findViewById(R.id.code);
            mPhoneView = (EditText) findViewById(R.id.phone);
            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (mEmailView.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please Write your name", Toast.LENGTH_LONG).show();
                    } else if (mAddressView.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please add your address", Toast.LENGTH_LONG).show();
                    } else if (mMobileView.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please enter your mobile number", Toast.LENGTH_LONG).show();
                    } else if (mCodeView.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please add your Code", Toast.LENGTH_LONG).show();
                    } else if (mPhoneView.getText().toString().isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please add your Phone no.", Toast.LENGTH_LONG).show();
                    } else if (!TextUtils.isDigitsOnly(mMobileView.getText().toString()) || (mMobileView.getText().toString().length() < 11)) {
                        Toast.makeText(LoginActivity.this, "Please enter a valid mobile no.", Toast.LENGTH_LONG).show();
                    } else if (!TextUtils.isDigitsOnly(mPhoneView.getText().toString()) || (mMobileView.getText().toString().length() <= 7)) {
                        Toast.makeText(LoginActivity.this, "Please enter a valid phone no.", Toast.LENGTH_LONG).show();
                    } else {
                        if (checkedMySheet) {
                            id = usersIds.getId();
                            editor.putString("Name", mEmailView.getText().toString());
                            editor.putString("Address", mAddressView.getText().toString());
                            editor.putString("Mobile", mMobileView.getText().toString());
                            editor.putString("Code", mCodeView.getText().toString());
                            editor.putString("Phone", mPhoneView.getText().toString());
                            editor.putBoolean("IsRegistered", true);
                            editor.commit();
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    postData();
                                }
                            });
                            t.start();
                        }
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(mainIntent);
                        LoginActivity.this.finish();

                    }

                }
            });


            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);

        }
    }

    public void postData() {

        String fullUrl = "https://docs.google.com/forms/d/1m1dhRzcPNagVxO28uH-7uebB8sZ1gC1TyNx-iFVM498/formResponse";
        HttpRequest mReq = new HttpRequest();
        String name,address,telephone,mobile,code;
        int myId;
        name = mEmailView.getText().toString();
        address =mAddressView.getText().toString();
        telephone = mPhoneView.getText().toString();
        mobile = mMobileView.getText().toString();
        String mobileNo = ""+mobile.charAt(0)+mobile.charAt(1)+mobile.charAt(2)+mobile.charAt(3)+" "+mobile.charAt(4)+mobile.charAt(5)+mobile.charAt(6)
        +" "+mobile.charAt(7)+mobile.charAt(8)+mobile.charAt(9)+mobile.charAt(10);
        code = mCodeView.getText().toString();
        myId=this.id+1;
        String data = "entry.902841453=" + URLEncoder.encode(name)+"&"+ "entry.1743281797=" + URLEncoder.encode(address)+"&"+
                "entry.1507940231=" + URLEncoder.encode(telephone)+"&"+ "entry.1447172494=" + URLEncoder.encode(mobileNo)+"&"+
                "entry.433899888=" + URLEncoder.encode(code)+"&" + "entry.551037592=" + URLEncoder.encode(myId+"");
        String response = mReq.sendPost(fullUrl, data);
    }


}

