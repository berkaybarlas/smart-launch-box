package com.elec339.eralp.ultimatelunchbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;
import com.amazonaws.mobileconnectors.cognito.*;


import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineButtons();
    }

    final Context context = this;
    public void defineButtons() {
        findViewById(R.id.button2).setOnClickListener(buttons);
        findViewById(R.id.button11).setOnClickListener(buttons);
        findViewById(R.id.button).setOnClickListener(buttons);
    }

    private View.OnClickListener buttons = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.button2:
                    Intent intent = new Intent(context, BoxTwo.class);
                    startActivity(intent);
                    break;
                case R.id.button11:
                    Intent intent1 = new Intent(context, DailyRecords.class);
                    startActivity(intent1);
                    break;
                case R.id.button:
                    getData();
            }
        }

    };

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

    Product deneme1;
    private void getData() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                            getApplicationContext(),
                            "us-west-2:15850afe-bc9d-4480-b2cd-cfeaa2a00d29", // Identity pool ID
                            Regions.US_WEST_2 // Region
                    );

                    CognitoSyncManager syncClient = new CognitoSyncManager(
                            getApplicationContext(),
                            Regions.US_WEST_2, // Region
                            credentialsProvider);

                    AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
                    ddbClient.setRegion(Region.getRegion(Regions.US_WEST_2));

                    final DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);

                    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

                    List<Product> timestamps = mapper.scan(Product.class, scanExpression);
                    String maxTime = "1";
                    for (Product trial : timestamps){
                        if(trial.getTimestamp().compareTo(maxTime)>0){
                            maxTime = trial.getTimestamp();
                        }
                    }

                    deneme1 = mapper.load(Product.class, "kap1", ""+maxTime);

                } catch (final Exception e){

                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result){

                ImageView image = (ImageView)findViewById(R.id.imageView4);
                image.setImageResource(R.drawable.apple);
                TextView weight = (TextView)findViewById(R.id.textView8);
                weight.setText("Weight: "+deneme1.getWeight()+" gram");
                TextView temperature = (TextView)findViewById(R.id.textView9);
                if(deneme1.getTemperature() < 5) {
                    temperature.setText("The condition is available for storing. Temperature: " + deneme1.getTemperature());
                }else{
                    temperature.setText("Temperature has to be around 0°C! Temperature: " + deneme1.getTemperature());
                }
            }
        };
        runAsyncTask(task);
    }

}


