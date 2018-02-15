package com.elec339.berkay.ultimatelunchbox;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by Eralp on 23.12.2017.
 */

@DynamoDBTable(tableName = "box-data")
public class Product {
    private String food;
    private int weight;
    private int temperature;
    private String timestamp;
    private int record_day;
    private int record_month;
    private int record_year;
    private int record_hour;
    private int record_minute;
    private String device_id;


    @DynamoDBHashKey(attributeName = "device_id")
    public String getdevice_id() {
        return device_id;
    }

    public void setdevice_id(String device_id) {
        this.device_id = device_id;
    }

    @DynamoDBAttribute(attributeName = "Food")
    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    @DynamoDBAttribute(attributeName = "Food Weight")
    public int  getWeight() {
        return weight;
    }

    public void setWeight(int  weight) {
        this.weight = weight;
    }

    @DynamoDBRangeKey(attributeName = "timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDBAttribute(attributeName = "Temperature")
    public int  getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @DynamoDBAttribute(attributeName = "Day")
    public int getDay() {
        return record_day;
    }

    public void setDay(int record_day) {
        this.record_day = record_day;
    }

    @DynamoDBAttribute(attributeName = "Month")
    public int getMonth() {
        return record_month;
    }

    public void setMonth(int record_month) {
        this.record_month = record_month;
    }

    @DynamoDBAttribute(attributeName = "Year")
    public int getYear() {
        return record_year;
    }

    public void setYear(int record_year) {
        this.record_year = record_year;
    }

    @DynamoDBAttribute(attributeName = "Hour")
    public int getHour() {
        return record_hour;
    }

    public void setHour(int record_hour) {
        this.record_hour = record_hour;
    }

    @DynamoDBAttribute(attributeName = "Minute")
    public int getMinute() {
        return record_minute;
    }

    public void setMinute(int record_minute) {
        this.record_minute = record_minute;
    }



    @Override
    public String toString() {
        return "Product{" +
                "food='" + food + '\'' +
                ", weight=" + weight +
                ", temperature=" + temperature +
                ", timestamp='" + timestamp + '\'' +
                ", record_time=" + record_day + "/"+record_month+"/"+record_year+"-"+record_hour+"/"+record_minute+
                ", device_id='" + device_id + '\'' +
                '}';
    }
}
