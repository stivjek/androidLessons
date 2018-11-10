package ru.startandroid.sqllite;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    private static MainActivity mainActivity;
    private static WeatherController weatherController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnHTTP = findViewById(R.id.button);
        Button btnDB = findViewById(R.id.button2);

        View.OnClickListener oclBtnHTTP = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpSend();
            }
        };
        View.OnClickListener oclBtnDB = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        };

        btnHTTP.setOnClickListener(oclBtnHTTP);
        btnDB.setOnClickListener(oclBtnDB);

        text = findViewById(R.id.textView);
        text.setMovementMethod(new ScrollingMovementMethod());

        mainActivity =  this;
        weatherController = new WeatherController(this);
        startService(new Intent(getBaseContext(), WeatherService.class));
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }

    public static WeatherController getController()
    {
        return weatherController;
    }

    protected void httpSend()
    {
        weatherController.getWeather();
    }

    protected void getData()
    {
        text.setText(weatherController.getDb());
    }


}