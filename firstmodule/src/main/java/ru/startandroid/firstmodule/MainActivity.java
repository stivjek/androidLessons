package ru.startandroid.firstmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHTTP = findViewById(R.id.button);

        View.OnClickListener oclBtnHTTP = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpSend();
            }
        };

        btnHTTP.setOnClickListener(oclBtnHTTP);
    }

    protected void httpSend()
    {
        final TextView text = findViewById(R.id.textView);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.43.60/android.json.php";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    text.setText("Response => Название: "+response.getString("name")+
                            ". Значение: "+response.getString("value")+
                            ". Код:"+response.getString("code"));
                } catch (JSONException e) {
                    text.setText(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                text.setText(error.getMessage());
            }
        });
        queue.add(jsObjRequest);
    }
}
