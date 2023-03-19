package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



//// Weather값에 따라 다른 배경과 화면을 출력 변경할때 사용
//public class Weather {
//    public double temp(){
//        double temp = 27.6;
//        return temp;
//    }
//    public String weather(){
//        String weather = "Rain";
//        return weather;
//    }
//    public int forecastTime(){
//        int forecastTime = 18;
//        return forecastTime;
//    }
//}

public class Weather {
    GPS gps;
    double gpsLon;
    double gpsLat;
    Message msg;
    ArrayList<getWeather> weatherArray = new ArrayList<getWeather>();
    getWeather getweather;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/").addConverterFactory(GsonConverterFactory.create()).build();
    WeatherApi weatherApi;
    private final static String appKey = "6236d803b5111857a6fd9f8c518e240a";
    final String[] json = {""};
    Context context;

    public void weather(Handler handler, double Lon, double Lat){
//         , double lat, double lon

        Thread th = new Thread(){

            @Override
            public void run() {
//            Log.i("choi", lat+"");


                weatherApi = retrofit.create(WeatherApi.class);

                Call<Object> getWeather = weatherApi.getWeather(Lat,Lon,"metric","5",appKey);
                Log.i("choi", getWeather.toString());
                try{
                    json[0] = new Gson().toJson(getWeather.execute().body());

//                    String result = json[0];
                    String result = json[0];

//                    Message msg = myHandler.obtainMessage(1, result);

//                    myHandler.sendMessage(msg);
                    JSONParser jsonParser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
                        JSONArray jsonArray = (JSONArray)jsonObject.get("list");
                        for(int i = 0; i<jsonArray.size(); i++){
                            JSONObject jsobj = (JSONObject)jsonArray.get(i);
                            JSONArray jsobj2 = (JSONArray) jsobj.get("weather");
                            JSONObject jsobj3 = (JSONObject)jsobj.get("main");
                            JSONObject aa21 = (JSONObject) jsobj2.get(0);
                            String weathermain = (String)aa21.get("main");
                            String dttxt = (String)jsobj.get("dt_txt");
                            String[] aaa = dttxt.split(" ");
                            aaa = aaa[1].split(":");
                            int datenum = Integer.valueOf(aaa[0]);
                            double temp= (double)jsobj3.get("temp");
                            getweather = new getWeather(weathermain, temp, datenum);
                            msg = new Message();
                            msg.what =i+1;
                            msg.obj = getweather;
                            handler.sendMessage(msg);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();

                    }

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        th.start();
    }
    ArrayList<getWeather> getWeathers(){
        return weatherArray;
    }
}