package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MorcosS on 5/31/16.
 */
public class MainMenuAdapter extends BaseAdapter {
    String [] list;
    LayoutInflater inflater;
    Activity activity;
    String [] images;


    public MainMenuAdapter(String[] list, Activity activity) {
        inflater = activity.getLayoutInflater();
        this.list = list;
        this.activity = activity;
        images= activity.getResources().getStringArray(R.array.menu_pics);
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int i) {
        return list[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.mainmenu_gridview,null);
        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        TextView textView = (TextView) view.findViewById(R.id.textView);
            Picasso.with(activity).load(images[i]).into(image);
        textView.setText(list[i]);
        return view;

    }
}
