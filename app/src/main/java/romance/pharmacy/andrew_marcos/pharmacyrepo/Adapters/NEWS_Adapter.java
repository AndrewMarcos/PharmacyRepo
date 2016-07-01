package romance.pharmacy.andrew_marcos.pharmacyrepo.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import romance.pharmacy.andrew_marcos.pharmacyrepo.Data.data_news;
import romance.pharmacy.andrew_marcos.pharmacyrepo.R;

/**
 * Created by Andrew Samir on 6/7/2016.
 */
public class NEWS_Adapter extends BaseAdapter {

    ArrayList<data_news> list;
    Activity activity;
    LayoutInflater inflater;

    public NEWS_Adapter(ArrayList<data_news> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.news_listview, null);
        TextView New = (TextView) convertView.findViewById(R.id.textViewNew);
//        ImageView im = (ImageView) convertView.findViewById(R.id.imageViewNews);




        data_news dataNews = list.get(position);
        New.setText(dataNews.getText());

        data_news x=list.get(position);

        NamesAndView container=new NamesAndView();
        container.name=x;
        container.view=convertView;

        ImageLoader loaders=new ImageLoader();
        loaders.execute(container);


        //String photo= dataNews.getPic_1();
        //byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
       // im.setImageBitmap(decodedByte);


        return convertView;
    }
    private class ImageLoader extends AsyncTask<NamesAndView,Void,NamesAndView> {


        @Override
        protected NamesAndView doInBackground(NamesAndView... params) {

            NamesAndView container=params[0];
            data_news name=container.name;
            try {


                Bitmap bitmap=StringToBitMap(name.getPic_1());

                container.bitmap=bitmap;
                return container;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(NamesAndView namesAndView) {


            ImageView im= (ImageView) namesAndView.view.findViewById(R.id.imageViewNews);

            im.setImageBitmap(namesAndView.bitmap);

        }
    }

    class NamesAndView{

        public data_news name;
        public View view;
        public Bitmap bitmap;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
