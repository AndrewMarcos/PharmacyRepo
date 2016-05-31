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
    ArrayList<String> list;
    LayoutInflater inflater;
    Activity activity;


    public MainMenuAdapter(ArrayList<String> list, Activity activity) {
        inflater = activity.getLayoutInflater();
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
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
        switch(i) {
            case 0:Picasso.with(activity).load("http://previews.123rf.com/images/vectorikart/vectorikart1506/vectorikart150600017/41171471-Modern-flat-vector-illustration-of-a-smiling-young-attractive-female-pharmacist-at-the-counter-in-a--Stock-Vector.jpg").into(image);break;
            case 1: Picasso.with(activity).load("http://www.clker.com/cliparts/7/9/5/0/11971562571757911274tulipan_Pharmaceutical_carton_1.svg.med.png").into(image);break;

        }
        textView.setText(list.get(i));
        return view;

    }
}
