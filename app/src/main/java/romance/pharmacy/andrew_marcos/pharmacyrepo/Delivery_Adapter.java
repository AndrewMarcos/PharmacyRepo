package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by MorcosS on 5/31/16.
 */
public class Delivery_Adapter extends BaseAdapter {
    ArrayList<String> images;
    ArrayList<String> messages;
    Activity activity;
    LayoutInflater inflater;

    public Delivery_Adapter( ArrayList<String> images,ArrayList<String> messages, Activity activity) {
        inflater = activity.getLayoutInflater();
        this.images = images;
        this.activity = activity;
        this.messages=messages;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.delivery_listview_item,null);
        ImageView image = (ImageView) view.findViewById(R.id.imageView2);
        TextView textView = (TextView) view.findViewById(R.id.textView3);
        TextView textView1 = (TextView) view.findViewById(R.id.textView4);
        try {
            byte[] decodedString = Base64.decode(images.get(i), Base64.DEFAULT);
            Bitmap base64Bitmap = BitmapFactory.decodeByteArray(decodedString, 0,
                    decodedString.length);
            image.setImageBitmap(base64Bitmap);
        }catch(Exception e){
            Picasso.with(activity).load("https://cdn.shopify.com/s/files/1/0714/0137/t/21/assets/noimage.jpg?942797024349010768").into(image);

        }
        textView.setText(textView.getText().toString()+" "+(i+1));
        textView1.setText(messages.get(i));
        return view;
    }
}
