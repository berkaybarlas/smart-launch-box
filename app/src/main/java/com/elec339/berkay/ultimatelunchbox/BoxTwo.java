package com.elec339.berkay.ultimatelunchbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BoxTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_two);
        defineButtons();
    }


    final Context context = this;

    public void defineButtons() {

    findViewById(R.id.button4).setOnClickListener(buttons);

    findViewById(R.id.button5).setOnClickListener(buttons);

    findViewById(R.id.button3).setOnClickListener(buttons);
}
        private View.OnClickListener buttons = new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                switch (arg0.getId()) {

                    case R.id.button4:
                        Intent intent1 = new Intent(context, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.button5:
                        Intent intent = new Intent(context, BoxThree.class);
                        startActivity(intent);
                        break;
                    case R.id.button3:
                        Intent intent2 = new Intent(context, DailyRecords.class);
                        startActivity(intent2);
                        break;
                }
            }

        };

}
