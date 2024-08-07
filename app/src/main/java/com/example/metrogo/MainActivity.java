package com.example.metrogo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mumayank.com.airlocationlibrary.AirLocation;

public class MainActivity extends AppCompatActivity implements AirLocation.Callback {
    Spinner startSpinner, endSpinner;
    //List<String> Items = new ArrayList<>();
    String start, end, startref, endref;
    byte icon1=0;
    AirLocation airLocation;
    ImageView location_icon1,location_icon2;
    SharedPreferences pref;

    static boolean clear;
    static boolean mainPage = false;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        icon1=0;
        startSpinner = findViewById(R.id.startSpinner);
        endSpinner = findViewById(R.id.endSpinner);
        location_icon1=findViewById(R.id.location_icon1);
        location_icon2=findViewById(R.id.location_icon2);
//        Collections.addAll(Items,  "helwan", "ain helwan", "helwan university",
//                "wadi hof", "hadayeq helwan", "el-maasara", "tora el-asmant", "kozzika",
//                "tora el-balad", "thakanat el-maadi", "maadi", "hadayeq el-maadi",
//                "dar el-salam", "el-zahraa", "mar girgis", "el-malek el-saleh",
//                "el-sayeda zeinab", "saad zaghloul", "sadat", "naser", "urabi",
//                "al-shohadaa", "ghamra", "el-demerdash", "manshiet el-sadr",
//                "kobri el-qobba", "hammamat el-qobba", "saray el-qobba", "hadayeq el-zaitoun",
//                "helmeyet el-zaitoun", "el-matareyya", "ain shams", "ezbet el-Nakhl", "el-marg",
//                "new el-marg", "el mounib", "sakiat mekki", "omm el misryeen",
//                "giza", "faisal", "el behoos", "dokki", "opera", "mohamed naguib", "ataba", "massara", "road el-farag",
//                "sainte teresa", "khalafawy", "mezallat", "koliet el-zeraa", "shobra el kheima"
//                , "adly mansour", "el haykestep", "omar ibn el khattab", "qubaa", "hisham barakat",
//                "el-nozha", "el-shams club", "alf masken", "heliopolis square", "haroun", "al ahram", "koleyet el banat",
//                "cairo stadium", "fair zone", "el-abaseya", "abdou pasha", "el geish", "bab el shaaria", "maspero", "zamalek", "kit kat", "sudan", "imbaba", "el-bohy", "el-qawmia", "ring road", "rod al-farag corridor"
//                , "tawfikia", "wadi el nile", "gameat al dewal al arabeya", "boulaq al dakrour"
//                , "cairo university");
   //     Collections.sort(Items);
       // Items.add(0,"please select");
//        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Items);
//        startSpinner.setAdapter(adapter);
//        startSpinner.setSelection(0);
//        endSpinner.setAdapter(adapter);
//        endSpinner.setSelection(0);
        start = startSpinner.getSelectedItem().toString();
        end = endSpinner.getSelectedItem().toString();
        pref = getSharedPreferences("data", MODE_PRIVATE);
        startref = pref.getString("start", "");
        endref = pref.getString("end", "");
        if (clear == true) {
            clear = false;
            editor = pref.edit();
            startref = "";
            endref = "";
            editor.clear();
            editor.apply();
        }

        if (!startref.equals("") && !endref.equals("") && !startref.equalsIgnoreCase("please select") && !endref.equalsIgnoreCase("please select")) {
            Intent a = new Intent(this, Result.class);
            a.putExtra("start", startref);
            a.putExtra("end", endref);
            startActivity(a);
            finish();


        }
    }

    @Override
    public void onBackPressed() {
        editor = pref.edit();
        editor.putString("start", start);
        editor.putString("end", end);
        editor.apply();
        mainPage = false;
        finish();
        super.onBackPressed();
    }
    public void calc(View view) {
        start = startSpinner.getSelectedItem().toString();
        end = endSpinner.getSelectedItem().toString();
        mainPage = true;
        //ensure that start and end stations exist and not equals to each other and not please select
        if (!start.equalsIgnoreCase("please select") && !end.equalsIgnoreCase("please select") && !end.equalsIgnoreCase(start)) {
            Intent a = new Intent(this, Result.class);
            a.putExtra("start", start);
            a.putExtra("end", end);
            startActivity(a);
        } else {
            Toast.makeText(this, "Please enter valid start and end stations", Toast.LENGTH_SHORT).show();


        }

    }


    public void location_icon1(View view) {
        airLocation=new AirLocation(this,this,true,0,"");
        airLocation.start();
        icon1=1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void location_icon2(View view) {
        airLocation=new AirLocation(this,this,true,0,"");
        airLocation.start();
        icon1=2;
    }

    @Override
    public void onFailure(@NonNull AirLocation.LocationFailedEnum locationFailedEnum) {
        Toast.makeText(this, "cannot getting location", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onSuccess(@NonNull ArrayList<Location> arrayList) {
        start = startSpinner.getSelectedItem().toString();
        end = endSpinner.getSelectedItem().toString();
        Geocoder geocoder=new Geocoder(this);
        try {
            if(!start.equals("")&& !start.equals("please select")&&icon1==1) {
                List<Address> address1 = geocoder.getFromLocationName(start +" metro station", 1);
                if (address1 != null && !address1.isEmpty() ) {
                    double lat1 = address1.get(0).getLatitude();
                    double long1 = address1.get(0).getLongitude();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat1+","+long1));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this, "Location not found for the given name", Toast.LENGTH_SHORT).show();
                }
            } if (!end.equals("")&& !end.equals("please select")&&icon1==2) {

                List<Address> address2 = geocoder.getFromLocationName(end + " metro station", 1);
                if (address2 != null && !address2.isEmpty() && !end.equals("please select")) {
                    double lat1 = address2.get(0).getLatitude();
                    double long1 = address2.get(0).getLongitude();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat1+","+long1));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Location not found for the given name", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//


    }

    public void search_station(View view) {
        Intent a=new Intent(this, get_nearest_station.class);
        startActivity(a);
    }
}