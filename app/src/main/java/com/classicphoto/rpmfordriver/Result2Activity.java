package com.classicphoto.rpmfordriver;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Result2Activity extends Activity {

    TextView text2, titleR;
    LinearLayout img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        text2 = (TextView) findViewById(R.id.text2);
        titleR = (TextView) findViewById(R.id.titleR);
        img_back = (LinearLayout) findViewById(R.id.img_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
