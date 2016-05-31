package romance.pharmacy.andrew_marcos.pharmacyrepo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Medical_Data_Deliveries extends AppCompatActivity {
    public static ArrayList<String> images;
    public static ArrayList<String> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_delivery);
        images = new ArrayList<String>();
        messages = new ArrayList<String>();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Medical_Data_Deliveries.this,MedicalList.class);
                startActivity(in);
            }
        });
        ListView orderListView = (ListView) findViewById(R.id.listView);

    }
}
