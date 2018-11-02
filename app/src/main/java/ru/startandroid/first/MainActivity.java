package ru.startandroid.first;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView text = findViewById(R.id.textView);

        Button btnOk = findViewById(R.id.button3);
        Button btnCalncel = findViewById(R.id.button4);

        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("Hello World");
            }
        };

        View.OnClickListener oclBtnCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("Cancel");
            }
        };

        btnOk.setOnClickListener(oclBtnOk);
        btnCalncel.setOnClickListener(oclBtnCancel);
    }
}
