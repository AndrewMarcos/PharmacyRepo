package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class MedicalList extends AppCompatActivity {
    int CAMERA_PIC_REQUEST = 2;
    ImageButton imageButton;
    String imageInBase64;
    DBHelper dbHelper;
    int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_list);
        dbHelper = new DBHelper(this);
        final EditText message = (EditText) findViewById(R.id.editText);
        Button saveButton = (Button) findViewById(R.id.button);
        Cursor cursor = dbHelper.getOrder();
        try {
           length = cursor.getCount();
        }catch (Exception e){
           length =0;
        }
        Log.v("hi",length+"");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.addOrder(length+1,imageInBase64,message.getText().toString()) == true) {
                    Toast.makeText(getBaseContext(), "Order Saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Order Is not saved", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        Picasso.with(this).load("https://cdn4.iconfinder.com/data/icons/simplicio/128x128/file_add.png").into(imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
            byte[] byteArr = byteArray.toByteArray();
            imageInBase64 = Base64.encodeToString(byteArr, Base64.DEFAULT);
            imageButton.setImageBitmap(image);
        }else{
            finish();
        }
    }
}
