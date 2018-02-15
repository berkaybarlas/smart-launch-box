package com.elec339.berkay.ultimatelunchbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DailyRecords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_records);
        defineButtons();
    }


    final Context context = this;

    private void defineButtons() {
       findViewById(R.id.button8).setOnClickListener(buttons);

    }
       private View.OnClickListener buttons = new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                switch (arg0.getId()) {
                    case R.id.button8:
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        break;

                }
            }

        };

}
