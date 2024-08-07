package com.example.metrogo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStore;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.util.Log;
public class Result extends AppCompatActivity {
    ArrayList<String> ResultText = new ArrayList<>();
    TextToSpeech tts;

    ListView listView;
    TextView fromtoText;
    boolean sound=false;
    Button clear;
    List<String> Line1 = Arrays.asList("helwan", "ain helwan", "helwan university",
            "wadi hoff", "hadayeq helwan", "el-maasara", "tora el-asmant", "kozzika",
            "tora el-balad", "thakanat el-maadi", "maadi", "hadayeq el-maadi",
            "dar el-salam", "el-zahraa", "mar girgis", "el-malek el-saleh",
            "al sayeda zeinab", "saad zaghloul", "sadat", "nasser", "urabi",
            "al-shohadaa", "ghamra", "el-demerdash", "manshiet el-sadr",
            "kobri el-qobba", "hammamat el-qobba", "saray el-qobba", "hadayeq el-zaitoun",
            "helmeyet el-zaitoun", "el-matareyya", "ain shams", "ezbet el-Nakhl", "el-marg",
            "new el-marg"
    );
    List<String> Line2 = Arrays.asList("el mounib", "sakiat mekki", "omm el misryeen",
            "giza", "faisal", "cairo university", "el behoos", "dokki", "opera",
            "sadat","mohamed naguib", "ataba", "al-shohadaa", "massara", "road el-farag",
            "sainte teresa", "khalafawy", "mezallat", "koliet el-zeraa", "shobra el kheima"
    );
    List<String> Line3 = Arrays.asList("adly mansour", "el haykestep", "omar ibn el khattab", "qubaa", "hisham barakat",
            "el-nozha", "el-shams club", "alf masken", "heliopolis square", "Haroun", "al ahram", "koleyet el banat",
            "cairo stadium", "fair zone", "el-abaseya", "abdou pasha", "el geish", "bab el shaaria", "ataba",
            "nasser", "maspero", "zamalek", "kit kat", "Sudan", "imbaba", "el-bohy", "el-qawmia", "Ring Road", "rod al-farag corridor"
    );
    // eltfre3a
    List<String> Line4 = Arrays.asList("adly mansour", "el haykestep", "omar ibn el khattab", "qubaa", "hisham barakat",
            "el-nozha", "el-shams club", "alf masken", "heliopolis square", "Haroun", "al ahram", "koleyet el banat",
            "cairo stadium", "fair zone", "el-abaseya", "abdou pasha", "el geish", "bab el shaaria", "ataba",
            "nasser", "maspero", "zamalek", "kit kat", "al tawfikeya", "wadi el nile", "gameat al dewal al arabeya", "boulaq al dakrour"
            , "cairo university"
    );

    List<String> line1 = Arrays.asList();
    List<String> line2 = Arrays.asList();
    List<String> line3 = Arrays.asList();
    List<String> line4 = Arrays.asList();
    String start = "";
    String end = "";
    byte n=0;
    List<String> exchanges = Arrays.asList("sadat", "nasser", "al-shohadaa", "ataba");
    List<String> all_available_routes = new ArrayList<>();
    ArrayList<String> stations=new ArrayList<>();
    boolean clearing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        stations.clear();
        ResultText.clear();
        n=0;
        listView = findViewById(R.id.listView);
        tts=new TextToSpeech(this,new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // Set the language (optional)
                    tts.setLanguage(Locale.US); // Change locale if needed
                } else {
                    // Handle TTS initialization error
                    Log.e("MainActivity", "TTS Initialization Failed!");
                }
            }
        });
        clearing = false;
        stations.clear();
        //enable scrolling
//        ResultText.setMovementMethod(new ScrollingMovementMethod());
        fromtoText = findViewById(R.id.FromToText);
        clear = findViewById(R.id.Clear);
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        fromtoText.setText("From " + start.toString() + " To " + end.toString());
        int same_line = 0;
        byte count = 0;
        //same lines
        if (Line1.contains(start) && Line1.contains(end)) {
            same_line = 1;
            line1 = Line1;
            line2 = Line2;
            line3 = Line3;
            line4 = Line4;
        } else if (Line2.contains(start) && Line2.contains(end)) {
            same_line = 1;
            line1 = Line2;
            line2 = Line1;
            line3 = Line3;
            line4 = Line4;
        } else if (Line3.contains(start) && Line3.contains(end)) {
            same_line = 1;
            line1 = Line3;
            line2 = Line1;
            line3 = Line2;
            line4 = Line4;
        }
        //eltfre3a
        else if (Line4.contains(start) && Line4.contains(end)) {
            same_line = 1;
            line1 = Line4;
            line2 = Line1;
            line3 = Line2;
            line4 = Line3;
        }
        //diff lines

        else {
            if (Line1.contains(start)) {
                if (Line2.contains(end)) {
                    line1 = Line1;
                    line2 = Line2;
                    line3 = Line3;
                    line4 = Line4;
                    same_line = 2;
                } else if (Line3.contains(end)) {
                    line1 = Line1;
                    line2 = Line3;
                    line3 = Line2;
                    line4 = Line4;
                    same_line = 2;
                }
                //eltfre3a
                else if (Line4.contains(end)) {
                    line1 = Line1;
                    line2 = Line4;
                    line3 = Line2;
                    line4 = Line3;
                    same_line = 2;
                }


            } else if (Line2.contains(start)) {
                if (Line1.contains(end)) {
                    line1 = Line2;
                    line2 = Line1;
                    line3 = Line3;
                    line4 = Line4;
                    same_line = 2;
                } else if (Line3.contains(end)) {
                    line1 = Line2;
                    line2 = Line3;
                    line3 = Line1;
                    line4 = Line4;
                    same_line = 2;

                } else if (Line4.contains(end)) {
                    line1 = Line2;
                    line2 = Line4;
                    line3 = Line1;
                    line4 = Line3;
                    same_line = 2;
                }
            } else if (Line3.contains(start)) {
                if (Line1.contains(end)) {
                    same_line = 2;
                    line1 = Line3;
                    line2 = Line1;
                    line3 = Line2;
                    line4 = Line4;
                } else if (Line2.contains(end)) {
                    same_line = 2;
                    line1 = Line3;
                    line2 = Line2;
                    line3 = Line1;
                    line4 = Line4;
                } else if (Line4.contains(end)) {
                    same_line = 2;
                    line1 = Line3;
                    line2 = Line4;
                    line3 = Line1;
                    line4 = Line2;
                }
                //eltfre3a
            } else if (Line4.contains(start)) {
                if (Line1.contains(end)) {
                    same_line = 2;
                    line1 = Line4;
                    line2 = Line1;
                    line3 = Line2;
                    line4 = Line3;
                } else if (Line2.contains(end)) {
                    same_line = 2;
                    line1 = Line4;
                    line2 = Line2;
                    line3 = Line1;
                    line4 = Line3;
                } else if (Line3.contains(end)) {
                    same_line = 2;
                    line1 = Line4;
                    line2 = Line3;
                    line3 = Line1;
                    line4 = Line2;
                }
            }
        }
        List<String> startline = new ArrayList<>(line1);
        List<String> endline = new ArrayList<>(line2);
        String ex_station = "";
       // ResultText.setText("");
        if(same_line==1){
            stations.clear();
            functions.get_routes_in_same(line1,ResultText,count,start,end);
            //get all available routes of line1 or line 2
//            if(line1.equals(Line1)||line1.equals(Line2)){
//                ResultText.append("--------------------------\n");
//                //from line1 to line2 to line1 from sadat or from line2 to line1 to line2 from sadat
//                Functions.get_all_info(all_available_routes,ResultText,count,"sadat","al shohadaa",start,line1,line1,line2,end);
//                ResultText.append("--------------------------\n");
//                all_available_routes.clear();
//                //from line1 to line2 to line1 from elshohadaa or from line2 to line1 to line2 from elshohadaa
//                Functions.get_all_info(all_available_routes,ResultText,count,"al shohadaa","sadat",start,line1,line1,line2,end);
//            }
            //if start or end exist in many lines
            //if start or end exist in more than one line
            if(!Line1.equals(line1)&&(Line1.contains(start)||Line1.contains(end))){
                ex_station="";
                if(Line1.contains(start)) {
                    ex_station = functions.get_nearest_station(Line1, start, exchanges, line1, all_available_routes, end);
                    stations.add(ex_station);
                    all_available_routes.clear();
                    functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line1, line1, end);
                    //another available route of line1 or line2
                    if(Line2.contains(end)) {
                        if (ex_station.equalsIgnoreCase("sadat")) ex_station = "al-shohadaa";
                        else ex_station = "sadat";
                        all_available_routes.clear();
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line1, Line2, end);
                        stations.add(ex_station);
                    }
                    if(Line1.contains(end)){
                        all_available_routes.clear();
                        functions.get_routes_in_same(Line1,ResultText,count,start,end);
                        stations.add(ex_station);
                    }
                }
                if (Line1.contains(end)) {
                    ex_station = functions.get_nearest_station(line1, start, exchanges, Line1, all_available_routes, end);
                    all_available_routes.clear();
                    functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, Line1, end);
                    stations.add(ex_station);
                }
            }
            if(!Line2.equals(line1)&&(Line2.contains(start)||Line2.contains(end))){
                ex_station = "";//save nearst station
                if(Line2.contains(start)) {
                    ex_station = functions.get_nearest_station(Line2, start, exchanges, line1, all_available_routes, end);
                    all_available_routes.clear();
                    //if transition between line1 and line2 and start is ex station
                    functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line2, line1, end);
                    stations.add(ex_station);
                    //another available route of line1 or line2
                    if(Line1.contains(end)&&Line1.equals(line1)) {
                        if (ex_station.equalsIgnoreCase("sadat")) ex_station = "al-shohadaa";
                        else ex_station = "sadat";
                        all_available_routes.clear();
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line2, Line1, end);
                        stations.add(ex_station);
                    }
                    if(Line2.contains(end)){
                        all_available_routes.clear();
                        functions.get_routes_in_same(Line2,ResultText,count,start,end);

                    }
                }
                if (Line2.contains(end)) {
                    ex_station = functions.get_nearest_station(line1, start, exchanges, Line2, all_available_routes, end);
                    all_available_routes.clear();
                    functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, Line2, end);
                    stations.add(ex_station);
                }
            }
            if(!line1.equals(Line3)&&(Line3.contains(start)||Line3.contains(end))) {
                ex_station="";
                if (Line3.contains(start)) {

                    //if start and end in Line3 and Line4 3shan ma3amlsh elly b3d kit kat 5t geded
                    if ((Line3.contains(start) && Line4.contains(end)) || (Line4.contains(start) && Line3.contains(end)))
                        ex_station = "kit kat";
                    else
                        ex_station = functions.get_nearest_station(Line3, start, exchanges, line1, all_available_routes, end);
                    all_available_routes.clear();
                    functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line3, line1, end);
                    stations.add(ex_station);
                    if (Line3.contains(end)) {
                        all_available_routes.clear();
                        functions.get_routes_in_same(Line3, ResultText, count, start, end);

                    }

                } else if (Line3.contains(end)) {
                    if ((Line3.contains(start) && Line4.contains(end)) || (Line4.contains(start) && Line3.contains(end)))
                        ex_station = "kit kat";
                    else
                        ex_station = functions.get_nearest_station(line1, start, exchanges, Line3, all_available_routes, end);
                    all_available_routes.clear();
                    functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, Line3, end);
                    stations.add(ex_station);
                }
            }
            if (!line1.equals(Line4) &&!line1.equals(Line3)) {
                ex_station="";
                if (Line4.contains(start) && !Line3.contains(start)) {
                    ex_station = functions.get_nearest_station(Line4, start, exchanges, line1, all_available_routes, end);
                    all_available_routes.clear();
                    functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line4, line1, end);
                    stations.add(ex_station);
                    if(Line4.contains(end)){
                        all_available_routes.clear();
                        functions.get_routes_in_same(Line4,ResultText,count,start,end);

                    }
                }
                if (Line4.contains(end) && !Line3.contains(end)) {
                    if (Line3.contains(start)) {
                        ex_station = "kit kat";
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line3, Line4, end);
                        stations.add(ex_station);
                    } else {
                        ex_station = functions.get_nearest_station(line1, start, exchanges, Line4, all_available_routes, end);
                        all_available_routes.clear();
                       // ResultText.append("--------------------------\n");
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, Line4, end);
                       stations.add(ex_station);
                    }
                }

            }

        }  else if(same_line==2) {
            ex_station = functions.get_nearest_station(line1, start, exchanges, line2, all_available_routes, end);
            stations.add(ex_station);
            all_available_routes.clear();
            functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, line2, end);
            if (line1.equals(Line1) && line2.equals(Line2) || line1.equals(Line2) && line2.equals(Line1)) {
                all_available_routes.clear();
                if (ex_station.equalsIgnoreCase("sadat")) {
                    ex_station = "al-shohadaa";
                } else if (ex_station.equalsIgnoreCase("al-shohadaa")) ex_station = "sadat";
                stations.add(ex_station);
                functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, line2, end);
            }
            //if start or ends exist in many lines

            if ((Line1.contains(start) && !Line1.equals(line1)) || (Line1.contains(end) && !Line1.equals(line2))) {
                if (Line1.contains(start)) {
                    line1 = Line1;
                    ex_station = functions.get_nearest_station(line1, start, exchanges, line2, all_available_routes, end);
                } else if ((Line1.contains(end) && !Line1.equals(line2))) {
                    line2 = Line1;
                    ex_station = functions.get_nearest_station(line1, start, exchanges, line2, all_available_routes, end);
                }
                all_available_routes.clear();
                stations.add(ex_station);
                functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, line2, end);
                line1 = startline;
                line2 = endline;
            }
            if ((Line2.contains(start) && !Line2.equals(line1)) || (Line2.contains(end) && !Line2.equals(line2))) {
                if (Line2.contains(start)) {
                    line1 = Line2;
                    ex_station = functions.get_nearest_station(line1, start, exchanges, line2, all_available_routes, end);
                    stations.add(ex_station);
                } else if (Line2.contains(end)) {
                    line2 = Line2;
                    ex_station = functions.get_nearest_station(line1, start, exchanges, line2, all_available_routes, end);
                    stations.add(ex_station);
                }
                all_available_routes.clear();
                functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, line2, end);
                line1 = startline;
                line2 = endline;
            }

            if ((Line3.contains(start) && !Line3.equals(line1)) || (Line3.contains(end) && !Line3.equals(line2))) {
                if (Line3.contains(start)) {
                    line1 = Line3;
                    if (Line4.contains(end) && !Line4.equals(line2)) {
                        ex_station = "kit kat";
                        all_available_routes.clear();
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, Line4, end);
                        stations.add(ex_station);
                    } else {
                        ex_station = functions.get_nearest_station(line1, start, exchanges, line2, all_available_routes, end);
                        all_available_routes.clear();
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, line2, end);
                        stations.add(ex_station);
                    }
                } else if (Line3.contains(end)) {
                    if (Line4.contains(start)) {
                        ex_station = "kit kat";
                        all_available_routes.clear();
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line4, Line3, end);
                        stations.add(ex_station);
                    } else {
                        line2 = Line3;
                        ex_station = functions.get_nearest_station(line1, start, exchanges, line2, all_available_routes, end);
                        all_available_routes.clear();
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, line2, end);
                        stations.add(ex_station);
                    }
                }
                line1 = startline;
                line2 = endline;
            }

            if ((Line4.contains(start) && !Line4.equals(line1)) || (Line4.contains(end) && !Line4.equals(line2))) {
                if (Line4.contains(start) && !Line3.contains(start)) {
                    line1 = Line4;
                    if (Line3.contains(end)) {
                        ex_station = "kit kat";
                        all_available_routes.clear();
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line4, Line3, end);
                        stations.add(ex_station);
                    } else {
                        ex_station = functions.get_nearest_station(line1, start, exchanges, line2, all_available_routes, end);
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line4, line2, end);
                        stations.add(ex_station);
                    }
                } else if (Line4.contains(end) && !Line3.contains(end)) {
                    line2 = Line4;
                    if (Line3.contains(start)) {
                        ex_station = "kit kat";
                        all_available_routes.clear();
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, Line3, Line4, end);
                        stations.add(ex_station);
                    } else {
                        ex_station = functions.get_nearest_station(line1, start, exchanges, line2, all_available_routes, end);
                        all_available_routes.clear();
                        functions.get_routes_between_2lines(all_available_routes, ResultText, count, ex_station, start, line1, Line4, end);
                        stations.add(ex_station);
                    }
                }
                line1 = startline;
                line2 = endline;
            }
        }
        RoutesAdapter adapter=new RoutesAdapter(this,ResultText);
        listView.setAdapter(adapter);


    }


    public void Clear(View view) {
        MainActivity.clear = true;
        Toast.makeText(this, "cleared", Toast.LENGTH_SHORT).show();
        // setResult(RESULT_OK,a);
        if (MainActivity.mainPage == true) {
            MainActivity.mainPage = false;
            finish();
        } else {
            Intent a = new Intent(this, MainActivity.class);
            startActivity(a);
            finish();
        }
    }

    class RoutesAdapter extends ArrayAdapter<String> {
        public RoutesAdapter(@NonNull Context context, ArrayList<String> Results) {
            super(context, 0, Results);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.results_item, parent, false);
            TextView resultText = convertView.findViewById(R.id.ResultText);
            resultText.setText(getItem(position).toString());
            ImageView soundIcon = convertView.findViewById(R.id.sound_of);
            if (getItem(position).contains("the station that you change from is ")) {
                if (n < stations.size()) {
                    String name = stations.get(n);
                    n++;
                    if (soundIcon != null) {
                        soundIcon.setOnClickListener(v -> {
                                    if (sound == false) {
                                        soundIcon.setImageResource(R.drawable.sound_on);
                                        sound = true;
                                    } else {
                                        soundIcon.setImageResource(R.drawable.soun_off);
                                        sound = false;
                                        tts.stop();
                                    }

                                    if (sound == true)
                                        tts.speak("the station that you will change from is " + name + "station", TextToSpeech.QUEUE_FLUSH, null, null);
                                }
                        );

                    }

                }
             else soundIcon.setEnabled(false);
            return convertView;
        }

//          else if (stations != null && !stations.isEmpty()&&getItem(position).contains("the station that you change from is  ")) {
//                String name2 = stations.get(position);
//                if (soundIcon != null) {
//                    soundIcon.setOnClickListener(v -> {
//                        if(sound==false) {
//                            soundIcon.setImageResource(R.drawable.sound_on);
//                            sound=true;
//                        }
//                        else{
//                            soundIcon.setImageResource(R.drawable.soun_off);
//                            tts.stop();
//                            sound=false;
//                        }
//
//                       if(sound==true) tts.speak("the station that you will change from is "+name2+"station", TextToSpeech.QUEUE_FLUSH, null, null);
//                            }
//                    );
//
//                }
//
//        }
            else soundIcon.setEnabled(false);
            return convertView;
        }
    }

    @Override
    public void onBackPressed() {
        tts.stop();
        super.onBackPressed();
    }
}