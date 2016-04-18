package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.app.Activity;
import android.content.Context;
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

public class LoginActivity extends AppCompatActivity {
    private AutoCompleteTextView mEmailView;
    private EditText mAddressView,mMobileView,mCodeView,mPhoneView;
    private View mProgressView;
    private View mLoginFormView;

    public static int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        counter=0;
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mAddressView = (EditText) findViewById(R.id.address);
        mMobileView = (EditText) findViewById(R.id.mobile);
        mCodeView = (EditText) findViewById(R.id.code);
        mPhoneView = (EditText) findViewById(R.id.phone);
        final SharedPreferences sharedPref =  getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sharedPref.getBoolean("IsRegistered", false)) {
                    //     Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    //       LoginActivity.this.startActivity(mainIntent);
                    //     LoginActivity.this.finish();

                } else if (mEmailView.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Write your name", Toast.LENGTH_LONG).show();
                } else if (mAddressView.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please add your address", Toast.LENGTH_LONG).show();
                } else if (mMobileView.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your mobile number", Toast.LENGTH_LONG).show();
                } else if (mCodeView.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please add your Code", Toast.LENGTH_LONG).show();
                } else if (mPhoneView.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please add your Phone no.", Toast.LENGTH_LONG).show();
                }else if(!TextUtils.isDigitsOnly(mMobileView.getText().toString())|| !(mMobileView.getText().toString().length() <11)){
                    Toast.makeText(LoginActivity.this, "Please enter a valid no.", Toast.LENGTH_LONG).show();
                }else if(!TextUtils.isDigitsOnly(mPhoneView.getText().toString())|| !(mMobileView.getText().toString().length() <7)) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid no.", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LoginActivity.this, mEmailView.getText().toString(), Toast.LENGTH_LONG).show();
                   /* editor.putString("Name", mEmailView.getText().toString());
                    editor.putString("Address", mAddressView.getText().toString());
                    editor.putString("Mobile", mMobileView.getText().toString());
                    editor.putString("Code", mCodeView.getText().toString());
                    editor.putString("Phone", mPhoneView.getText().toString());*/
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();
                    //editor.putBoolean("IsRegistered", true);
                }
            }
        });


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }



}

