package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MedicalList extends AppCompatActivity {
    int CAMERA_PIC_REQUEST = 2;
    ImageButton imageButton;
    ImageView imageView;
    String imageInBase64;
    DBHelper dbHelper;
    int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_list);
        final boolean isEdit = getIntent().getBooleanExtra("edit",false);
        imageInBase64="No Image";
        dbHelper = new DBHelper(this);
        final EditText message = (EditText) findViewById(R.id.editText);
        Button saveButton = (Button) findViewById(R.id.button);
        Cursor cursor = dbHelper.getOrder();
        imageButton = (ImageButton) findViewById(R.id.imageButton);
         imageView= (ImageView) findViewById(R.id.set_image_);
       // imageButton.setImageResource(R.drawable.document_add);
        final int id = getIntent().getIntExtra("ID",0);
        try {
           length = cursor.getCount();
            Log.v("delete",length+"");

        }catch (Exception e){
           length =0;
        }
        if(isEdit) {

            message.setText(getIntent().getStringExtra("message"));
            imageInBase64 = getIntent().getStringExtra("image");
            byte[] decodedString = Base64.decode(imageInBase64, Base64.DEFAULT);
            Bitmap base64Bitmap = BitmapFactory.decodeByteArray(decodedString, 0,
                    decodedString.length);
            if(!imageInBase64.equals("No Image")) {
                imageView.setImageBitmap(base64Bitmap);
            }
          }
            saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isEdit) {
                    if (dbHelper.addOrder(length + 1, imageInBase64, message.getText().toString()) == true) {
                        Toast.makeText(getBaseContext(), "تم حفظ الطلب", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "لم يتم حفظ الطلب", Toast.LENGTH_LONG).show();
                    }
                }else {
                        dbHelper.deleteOrder(id);
                        if (dbHelper.addOrder(id, imageInBase64, message.getText().toString()) == true) {
                            Toast.makeText(getBaseContext(), "تم حفظ الطلب", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "لم يتم حفظ الطلب", Toast.LENGTH_LONG).show();
                        }
                }
                finish();
            }
        });
       imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {
                        "الكاميرا", "الاستديو"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MedicalList.this);
                builder.setTitle(" اختر صورة من ...");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("الكاميرا")) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
                        } else if (items[item].equals("الاستديو")) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i,  1);
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK && data != null) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
            byte[] byteArr = byteArray.toByteArray();
            imageInBase64 = Base64.encodeToString(byteArr, Base64.DEFAULT);
            imageView.setImageBitmap(image);
        }else if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap image1 = BitmapFactory.decodeStream(imageStream);
            Bitmap myimage = scaleDownBitmap(image1,100,this);
            ByteArrayOutputStream byteArray1 = new ByteArrayOutputStream();
            myimage.compress(Bitmap.CompressFormat.JPEG, 100, byteArray1);
            byte[] byteArr1 = byteArray1.toByteArray();
            imageInBase64 = Base64.encodeToString(byteArr1, Base64.DEFAULT);
            imageView.setImageBitmap(myimage);

        }else{
            finish();
        }
    }


    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

}
