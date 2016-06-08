package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

import romance.pharmacy.andrew_marcos.pharmacyrepo.Adapters.NEWS_Adapter;
import romance.pharmacy.andrew_marcos.pharmacyrepo.Data.data_news;
import romance.pharmacy.andrew_marcos.pharmacyrepo.jsondata.Jsondata;

public class News extends AppCompatActivity {

    Resources res;
    ArrayList<data_news> DataArray;
    NEWS_Adapter news_adapter;
    ListView listView_news;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        listView_news = (ListView) findViewById(R.id.listView_NEWS);
        progressBar=(ProgressBar)findViewById(R.id.progressBar_NEWS);

        DataArray = new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(News.this);

        String url = "https://spreadsheets.google.com/feeds/list/1vCINRHNp8yrdvjJkn_KA5c_WnhoRVeczySwNA7yhRh0/3/public/values?alt=json";
        StringRequest str = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Gson gson = new Gson();


                        final Jsondata dd = gson.fromJson(response, Jsondata.class);


                        for (int i = 0; i < dd.getFeed().getEntry().size(); i++) {

                            DataArray.add(new data_news(dd.getFeed().getEntry().get(i).getGsx$pic1().get$t(),
                                    dd.getFeed().getEntry().get(i).getGsx$pic2().get$t(),
                                    dd.getFeed().getEntry().get(i).getGsx$text().get$t()));
                        }

                        news_adapter = new NEWS_Adapter(DataArray, News.this);
                        listView_news.setAdapter(news_adapter);

                        listView_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent go_Details = new Intent(News.this, News_details.class);
                                go_Details.putExtra("pic", dd.getFeed().getEntry().get(position).getGsx$pic1().get$t() +
                                        dd.getFeed().getEntry().get(position).getGsx$pic2().get$t());
                                go_Details.putExtra("text", dd.getFeed().getEntry().get(position).getGsx$text().get$t());
                                startActivity(go_Details);
                            }
                        });

                        progressBar.setVisibility(View.GONE);
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
