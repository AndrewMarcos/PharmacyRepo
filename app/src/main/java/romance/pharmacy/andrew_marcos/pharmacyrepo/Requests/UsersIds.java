package romance.pharmacy.andrew_marcos.pharmacyrepo.Requests;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import romance.pharmacy.andrew_marcos.pharmacyrepo.LoginActivity;

/**
 * Created by MorcosS on 4/30/16.
 */
public class UsersIds {
   public int id ;

    public UsersIds (String url ,Context context){
        id=0;
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest s = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String st = response.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1= jsonObject.getJSONObject("feed");
                            JSONArray jsonArray=jsonObject1.getJSONArray("entry");
                            id=jsonArray.length();
                            LoginActivity.checkedMySheet=true;

                           } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("ids",error.toString());
                         }
                });

        queue.add(s);
    }

    public int getId() {

        return id;
    }

}
