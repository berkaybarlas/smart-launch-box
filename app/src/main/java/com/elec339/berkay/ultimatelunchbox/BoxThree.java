package com.elec339.eralp.ultimatelunchbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BoxThree extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_three);
        defineButtons();
    }


    final Context context = this;

    private void defineButtons() {
        findViewById(R.id.button7).setOnClickListener(buttons);
        findViewById(R.id.button6).setOnClickListener(buttons);
}
        private View.OnClickListener buttons = new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                switch (arg0.getId()) {
                    case R.id.button7:
                    Intent intent = new Intent(context, DailyRecords.class);
                    startActivity(intent);

                    case R.id.button6:
                        Intent intent1 = new Intent(context, BoxTwo.class);
                        startActivity(intent1);
                        break;

                }
            }
        };
}
