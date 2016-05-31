package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;

public class MedicalList extends AppCompatActivity {
    int CAMERA_PIC_REQUEST = 2;
    ImageButton imageButton;
    String imageInBase64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_list);
        final EditText message = (EditText) findViewById(R.id.editText);
        Button saveButton = (Button) findViewById(R.id.button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Medical_Data_Deliveries.images.add(imageInBase64);
                Medical_Data_Deliveries.messages.add(message.getText().toString());
                finish();
            }
        });
        imageButton = (ImageButton) findViewById(R.id.imageButton);
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
