package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class SignUp extends AppCompatActivity {

    Button buttonSignUp;
    EditText editTextName, editTextMobile, editTextHome, editTextAddress;
    static long signInNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        editTextName = (EditText) findViewById(R.id.editTextNameSignUp);
        editTextMobile = (EditText) findViewById(R.id.editTextMobileSignUp);
        editTextHome = (EditText) findViewById(R.id.editTextHomeSignUp);
        editTextAddress = (EditText) findViewById(R.id.editTextAddressSignUp);


        Query queryRef = MainActivity.myFirebaseRef.child("SignInNo");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                signInNo = Long.parseLong(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError error) {

            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addStr, mobStr, naStr, phoStr;
                addStr = null;
                mobStr = null;
                naStr = null;


                if (editTextName.getText().toString().length() == 0)
                    editTextName.setError("the name is required");
                else
                    naStr = editTextName.getText().toString();

                if (editTextMobile.getText().toString().length() == 0)
                    editTextMobile.setError("Mobile Number is required");
                else if (editTextMobile.getText().toString().length() != 11)
                    editTextMobile.setError("Please enter a valid Mobile Number");
                else
                    mobStr = editTextMobile.getText().toString();

                if (editTextAddress.getText().toString().length() == 0)
                    editTextAddress.setError("Address is required");
                else
                    addStr = editTextAddress.getText().toString();

                if (editTextHome.getText().toString().length() == 0)
                    phoStr = "No Home Phone";
                else
                    phoStr = editTextHome.getText().toString();

                if (!addStr.equals(null) && !mobStr.equals(null) && !naStr.equals(null)) {
                    MainActivity.myFirebaseRef.child("SignIn").child((signInNo + 1) + "").child("Address").setValue(addStr);
                    MainActivity.myFirebaseRef.child("SignIn").child((signInNo + 1) + "").child("Mobile").setValue(mobStr);
                    MainActivity.myFirebaseRef.child("SignIn").child((signInNo + 1) + "").child("Name").setValue(naStr);
                    MainActivity.myFirebaseRef.child("SignIn").child((signInNo + 1) + "").child("Phone").setValue(phoStr);
                    final String finalNaStr = naStr;
                    final Long x=signInNo;
                    MainActivity.myFirebaseRef.child("SignInNo").setValue(signInNo + 1, new Firebase.CompletionListener() {

                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MY_PREFS_NAME), MODE_PRIVATE).edit();
                            editor.putBoolean("SignedUp", true);
                            editor.putString("name", finalNaStr);
                            editor.putLong("ID",x);
                            editor.commit();
                            SignUp.super.onBackPressed();
                        }
                    });
                }


            }
        });


    }
}
