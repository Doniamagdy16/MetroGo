package com.example.metrogo;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class get_nearest_station extends AppCompatActivity {
    EditText current_location;
    TextView result;
    String current;
    List<Station>all_data=new ArrayList<>();
    ArrayList<Double> distances=new ArrayList<>();
    Button rout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_nearest_station);
        current_location=findViewById(R.id.current_location);
        result=findViewById(R.id.result);
        rout=findViewById(R.id.get_rout);
        distances.clear();
        result.setText("");
        all_data= Arrays.asList(new Station("helwan",29.848889,31.334167),
                new Station("ain helwan",29.862778,31.325052),
                new Station("helwan university",29.868889  ,31.320278),
                new Station("hadayeq helwan",29.897222,31.304167),
                new Station("el-maasara", 29.906111,31.299722),
                new Station("tora el-asmant",29.925833,31.287777),
                new Station("kozzika",29.936111,31.281667),
                new Station("tora el-balad",29.946389,31.273611),
                new Station("thakanat el-maadi",29.952778,31.263333),
                new Station("maadi",29.959722,31.258056),
                new Station("hadayeq el-maadi",29.97,31.250556),
                new Station("dar el-salam",29.981944,31.242222),
                new Station("el-zahraa",29.995278,31.231667),
                new Station("mar girgis",30.005833,31.229444),
                new Station("el-malek el-saleh", 30.016944,31.230833),
                new Station("al sayeda zeinab", 30.029167,31.235278),
                new Station("saad zaghloul",30.036667,31.238056),
                new Station("sadat", 30.044444,31.235556),
                new Station("nasser",30.053611,31.238889),
                new Station("urabi",30.0575,31.2425),
                new Station("al-shohadaa",30.0625,31.246111),
                new Station("ghamra", 30.068889,31.264722),
                new Station("el-demerdash",30.077222,31.277778),
                new Station("manshiet el-sadr",30.082222, 31.287778),
                new Station("kobri el-qobba",30.086944,31.293889),
                new Station("hammamat el-qobba",30.0875,31.298056),
                new Station("saray el-qobba",30.098056,31.304722),
                new Station("hadayeq el-zaitoun",30.105278,31.31),
                new Station("helmeyet el-zaitoun",30.114444,31.313889),
                new Station("el-matareyya",30.121389,31.313889),
                new Station("ain shams",30.131111,31.319167),
                new Station("ezbet el-Nakhl",30.139167,31.324444),
                new Station("el-marg",30.152222,31.335556),
                new Station("new el-marg",30.163333,31.338333),
                new Station("el mounib",29.981389,31.211944),
                new Station("sakiat mekki",29.995556,31.208611),
                new Station("omm el misryeen",30.005278,31.208056),
                new Station("giza",30.010556,31.206944),
                new Station("faisal",30.017222,31.203889),
                new Station("cairo university",30.026111,31.201111),
                new Station("el behoos",30.035833,31.200278),
                new Station("dokki",30.038333,31.211944),
                new Station("opera",30.041944,31.225278),
                new Station("mohamed naguib",30.045278,31.244167),
                new Station("ataba",30.0525,31.246944),
                new Station("massara",30.071111 ,31.245),
                new Station("road el-farag", 30.080556,31.245556),
                new Station("sainte teresa",30.088333,31.245556),
                new Station("khalafawy",30.098056,31.245278),
                new Station("mezallat",30.105,31.246667),
                new Station("koliet el-zeraa", 30.113889,31.248611),
                new Station("shobra el kheima", 30.1225,31.244722),
                new Station("adly mansour", 30.146944,31.421389),
                new Station("el haykestep", 30.143889,31.404722),
                new Station("omar ibn el khattab", 30.140556,31.394167),
                new Station("qubaa", 30.134722,31.3838891),
                new Station("hisham barakat", 30.131111,31.372778),
                new Station("el-nozha", 30.128333,31.360000),
                new Station("el-shams club", 30.122222,31.343889),
                new Station("alf masken", 30.118056 ,31.339722),
                new Station("heliopolis square", 30.108056,31.338056),
                new Station("Haroun", 30.101111,31.332778),
                new Station("al ahram",  30.091389,31.326389),
                new Station("koleyet el banat", 30.083611,31.328889),
                new Station("cairo stadium", 30.073056 ,31.3175),
                new Station("fair zone", 30.073333 ,31.301111),
                new Station("el-abaseya", 30.069722 ,31.280833),
                new Station("abdou pasha", 30.064722,31.274722),
                new Station("el geish", 30.0625,31.266944),
                new Station("bab el shaaria",  30.053889,31.256111),
                new Station("maspero", 30.055556,31.232222),
                new Station("zamalek", 30.0625,31.2225),
                new Station("kit kat", 30.06666,31.213056),
                new Station("Sudan", 30.069722,31.205278),
                new Station("imbaba", 30.075833,31.2075),
                new Station("el-bohy", 30.082222,31.210556),
                new Station("el-qawmia", 30.093333,31.208889),
                new Station("Ring Road",  30.096389 ,31.199722),
                new Station("rod al-farag corridor", 30.101944,31.184167),
                new Station("al tawfikeya", 30.065278,31.2025),
                new Station("wadi el nile", 30.063889,31.201111),
                new Station("gameat al dewal al arabeya", 30.050833,31.199722),
                new Station("boulaq al dakrour", 30.036111,31.196389)
                );
    }
    public void get_rout(View view) {
        current=current_location.getText().toString();
        distances.clear();
        Geocoder geocoder=new Geocoder(this);
        try {
            List<Address> address1 = geocoder.getFromLocationName(current, 1);
            if(!address1.isEmpty()) {
                double lat1 = address1.get(0).getLatitude();
                double long1 = address1.get(0).getLongitude();
                Location location1 = new Location("");
                location1.setLatitude(lat1);
                location1.setLongitude(long1);
                for (Station station : all_data) {
                    double lat2 = station.getLatitude();
                    double long2 = station.getLongitude();
                    Location location2 = new Location("");
                    location2.setLatitude(lat2);
                    location2.setLongitude(long2);
                    distances.add((double) location1.distanceTo(location2));
                }
                Double min = Collections.min(distances);
                int index = distances.indexOf(min);
                result.setText("nearest station is " + all_data.get(index).getName() + " station");
            } } catch (IOException e) {
            Toast.makeText(this, "cannot find this location please ensure from it's spilling", Toast.LENGTH_SHORT).show();
           // throw new RuntimeException(e);
        }
    }

}