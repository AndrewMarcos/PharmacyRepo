package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

import romance.pharmacy.andrew_marcos.pharmacyrepo.Data.data_news;
import romance.pharmacy.andrew_marcos.pharmacyrepo.jsondata.DataInJson;

public class News extends AppCompatActivity {

    Resources res;
    ArrayList<data_news> DataArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        DataArray=new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(News.this);

        String url = res.getString(R.string.Get_NEWS);
        StringRequest str = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(News.this, response, Toast.LENGTH_LONG).show();

                        Gson gson = new Gson();

                        DataInJson dd = gson.fromJson(response, DataInJson.class);
                       for (int i = 5; i < dd.getFeed().getEntry().size(); ) {



                           DataArray.add(new data_news(dd.getFeed().getEntry().get(i).getContent().get$t(),
                                   dd.getFeed().getEntry().get(i+1).getContent().get$t(),
                                   dd.getFeed().getEntry().get(i+2).getContent().get$t()));
                           i+=5;
                       }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(News.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        queue.add(str);

    }
}
