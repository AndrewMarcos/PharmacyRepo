package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import romance.pharmacy.andrew_marcos.pharmacyrepo.Adapters.NEWS_Adapter;
import romance.pharmacy.andrew_marcos.pharmacyrepo.Data.data_news;

public class News extends AppCompatActivity {

    Resources res;
    ArrayList<data_news> DataArray;
    NEWS_Adapter news_adapter;
    ListView listView_news;
    ProgressBar progressBar;
    Query queryRef;
    DataSnapshot myChild;
    Firebase myFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Firebase.setAndroidContext(this);
        myFirebase= new Firebase(getString(R.string.MyFirebase_Database));

        listView_news = (ListView) findViewById(R.id.listView_NEWS);
        progressBar=(ProgressBar)findViewById(R.id.progressBar_NEWS);



    }

    @Override
    protected void onStart() {
        super.onStart();
        final SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("SharedPreference", Activity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();
        DataArray = new ArrayList<>();

        Query queryRef = myFirebase.child("News");
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot snapshot) {
                DataArray.clear();
                long size = snapshot.getChildrenCount();
                Iterable<DataSnapshot> myChildren =  snapshot.getChildren();
                while (myChildren.iterator().hasNext()) {
                    int i =0;
                    myChild = myChildren.iterator().next();
                    try {

                        DataArray.add(0,new data_news(myChild.child("Picture").getValue().toString(),
                                myChild.child("Text").getValue().toString(),
                                        myChild.getKey().toString()));

                    }catch (Exception e){
                    }
                    i++;
                }
                news_adapter = new NEWS_Adapter(DataArray, News.this);
                listView_news.setAdapter(news_adapter);
                listView_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent go_Details = new Intent(News.this, News_details.class);
                        go_Details.putExtra("pic", snapshot.child(DataArray.get(position).getNews_id()).child("Picture").getValue().toString());
                        go_Details.putExtra("text", snapshot.child(DataArray.get(position).getNews_id()).child("Text").getValue().toString());
                        startActivity(go_Details);
                    }
                });

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(FirebaseError error) {

            }

        });
        Query queryRef2 = myFirebase.child("NewsNo");
        queryRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editor.putString("NewsID",dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
