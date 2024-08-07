package com.example.metrogo;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class functions {

    static List<Object> get_route1(List<String> line1, String ex_station, String start, List<String> all_available_routes) {
        int lastIndex = line1.size() - 1;
        StringBuilder sb = new StringBuilder();
        sb.append("The  direction is " + line1.get(lastIndex)+"\n");
        sb.append("the station that you change from is " + ex_station+"\n");
        String station="the station that you change from is " + ex_station;
        all_available_routes.addAll(line1.subList(line1.indexOf(start), line1.indexOf(ex_station) + 1));

        return  Arrays.asList(all_available_routes, sb,station);
    }

    static List<Object> get_route2(List<String> line2, String ex_station, String end, List<String> all_stations) {

        all_stations.addAll(line2.subList(line2.indexOf(ex_station) + 1, line2.indexOf(end) + 1));
        int lastIndex = line2.size() - 1;
        StringBuilder sb = new StringBuilder();
        sb.append("The  direction is " + line2.get(lastIndex));
        return Arrays.asList( all_stations,sb);
    }

    static int get_first_station(List<String> line1, String start, List<String> exchanges, List<String> line2) {//get first station of exchange
        int first_index_f = 0;
            first_index_f = line1.subList(line1.indexOf(start), line1.size())
                    .stream()
                    .filter(exchanges::contains)
                    .filter(line2::contains)
                    .findFirst()
                    .map(line1::indexOf).orElse(-1);

        return first_index_f;
    }
    public static String calc_time(byte count) {
        byte time = (byte) (count * 2);
        StringBuilder sb = new StringBuilder();
        if (time >= 60) {
            time = (byte) (time / 60);
            sb.append("The time taken to arrive = ").append(time).append(" hours\n");
        } else {
            sb.append("The time taken to arrive = ").append(time).append(" minutes\n");
        }
        return sb.toString();
    }

    static byte calc_ticket(byte count) {
        byte ticket = 0;
        if (count <= 9) {
            ticket = 6;
        } else if (count <= 16) {
            ticket = 8;
        } else if (count <= 23) {
            ticket = 12;
        } else {
            ticket = 15;
        }
        System.out.println("Ticket= " + ticket);
        return ticket;
    }
    public static String station_details1(byte count, List<String> line1, String start, String end) {
        StringBuilder sb = new StringBuilder();
        sb.append("The direction is ").append(line1.get(line1.size() - 1)).append("\n");
        sb.append("Number of stations = ").append(count+"\n");
        sb.append(functions.calc_time(count)); // Assuming calc_time returns a String
        sb.append("The stations are ").append(line1.subList(line1.indexOf(start), line1.indexOf(end) + 1)).append("\n");

        return sb.toString();
    }
    public static String station_details2(byte count, List<String> all_available_routes) {
        StringBuilder sb = new StringBuilder();

        sb.append("Number of stations = ").append(count).append("\n");
        sb.append(functions.calc_time(count)); // Assuming calc_time returns a String
        sb.append("Ticket ="+ functions.calc_ticket(count)).append("\n"); // Assuming calc_ticket returns a String
        sb.append("The stations are ").append(all_available_routes).append("\n");

        return sb.toString();
    }
    public static void get_routes_in_same(List<String>line1, ArrayList<String> ResultText, byte count, String start, String end){
        if (line1.indexOf(end) > line1.indexOf(start)) {
            count = (byte) ((line1.indexOf(end) - line1.indexOf(start)) + 1);
            String details1 = functions.station_details1(count,line1,start,end);
            Byte ticket = functions.calc_ticket(count);
            Collections.addAll(ResultText,details1+"Ticket ="+ticket.toString());
            //ResultText.add("Ticket ="+ticket.toString()+"\n");
        } else if (line1.indexOf(end) < line1.indexOf(start)) { //raga3 l wra
            Collections.reverse(line1);
            count = (byte) ((line1.indexOf(end) - line1.indexOf(start)) + 1);
            String details1= functions.station_details1(count,line1,start,end);
            Byte ticket = functions.calc_ticket(count);
            Collections.addAll(ResultText,details1+"Ticket ="+ticket.toString());
          //  ResultText.add("Ticket ="+ticket.toString()+"\n");
        }
    }
    public static void get_routes_between_2lines(List<String> all_available_routes, ArrayList<String> ResultText, byte count, String ex_station, String start , List<String> line1, List<String> line2, String end){
        String res1 = "";
        String res2 = "";
        ArrayList<String> stations=new ArrayList<>();
        if(start.equalsIgnoreCase(ex_station)){
            int lastIndex = line1.size() - 1;
//            ResultText.add("The  direction is " + line1.get(lastIndex)+"\n");
//            ResultText.add("the station that you change from is  " + ex_station+"\n");
            all_available_routes.add(start.toString());
            if (line2.indexOf(ex_station) < line2.indexOf(end)) {
                List<Object> result = functions.get_route2(line2,ex_station,end,all_available_routes);
                all_available_routes = (List<String>) result.get(0);
              //  ResultText.add(result.get(1).toString()+"\n");
                count = (byte) all_available_routes.size();
                String details= functions.station_details2(count,all_available_routes);
                //ResultText.add(details);
                ResultText.add("The  direction is " + line1.get(lastIndex)+"\n"+"the station that you change from is  " + ex_station+"\n"+result.get(1).toString()+"\n"+details);
               // ResultText.append("--------------------------\n");
            } else if (line2.indexOf(ex_station) > line2.indexOf(end)) { //raga3 l wra
                Collections.reverse(line2);
                List<Object> result = functions.get_route2(line2,ex_station,end,all_available_routes);
                all_available_routes = (List<String>) result.get(0);
               res2=result.get(1).toString()+"\n";
                //  ResultText.add(result.get(1).toString()+"\n");
                Collections.reverse(line2);
                count = (byte) all_available_routes.size();
                String details= functions.station_details2(count,all_available_routes);
               // ResultText.add(details);
                ResultText.add("The  direction is " + line1.get(lastIndex)+"\n"+"the station that you change from is  " + ex_station+"\n"+res2+"\n"+details);

                //  ResultText.add("--------------------------\n");
            }

        } else if (ex_station.equalsIgnoreCase(end)) {
            if (line1.indexOf(ex_station) > line1.indexOf(start)) {
                all_available_routes.clear();
                List<Object> result = functions.get_route1(line1, ex_station, start, all_available_routes);
                all_available_routes = (List<String>) result.get(0);
                res1= result.get(1).toString() ;
            } else if (line1.indexOf(ex_station) < line1.indexOf(start)) { //raga3 l wra
                Collections.reverse(line1);
                List<Object> result = functions.get_route1(line1, ex_station, start, all_available_routes);
                all_available_routes = (List<String>) result.get(0);
                 res1=result.get(1).toString() ;
                Collections.reverse(line1);
            }
            int lastIndex = line2.size() - 1;
            count = (byte) all_available_routes.size();
            String details= functions.station_details2(count,all_available_routes);
            ResultText.add("The  direction is " + res1+ "The  direction is " + line2.get(lastIndex)+"\n"+details);


    } else {
            if (line1.indexOf(ex_station) > line1.indexOf(start)) {
                all_available_routes.clear();
                List<Object> result = functions.get_route1(line1, ex_station, start, all_available_routes);
                all_available_routes = (List<String>) result.get(0);
                //ResultText.add(result.get(1).toString() + "\n");
                res1=result.get(1).toString();
            } else if (line1.indexOf(ex_station) < line1.indexOf(start)) { //raga3 l wra
                Collections.reverse(line1);
                List<Object> result = functions.get_route1(line1, ex_station, start, all_available_routes);
                all_available_routes = (List<String>) result.get(0);
                //ResultText.add(result.get(1).toString() + "\n");
                res1=result.get(1).toString() ;
                Collections.reverse(line1);
            }
            if (line2.indexOf(ex_station) < line2.indexOf(end)) {
                List<Object> result = functions.get_route2(line2, ex_station, end, all_available_routes);
                all_available_routes = (List<String>) result.get(0);
               // ResultText.add(result.get(1).toString() + "\n");
                res2=result.get(1).toString() + "\n";
                count = (byte) all_available_routes.size();
                String details = functions.station_details2(count, all_available_routes);
                ResultText.add(""+res1+res2+""+details);
               // ResultText.append("--------------------------\n");
            } else if (line2.indexOf(ex_station) > line2.indexOf(end)) { //raga3 l wra
                Collections.reverse(line2);
                List<Object> result = functions.get_route2(line2, ex_station, end, all_available_routes);
                all_available_routes = (List<String>) result.get(0);
              //  ResultText.add(result.get(1).toString() + "\n");
                res2=result.get(1).toString() + "\n";
                Collections.reverse(line2);
                count = (byte) all_available_routes.size();
                String details = functions.station_details2(count, all_available_routes);
                ResultText.add(""+res1+res2+""+details);
                //ResultText.append("--------------------------\n");
            }
        }
    }
    public static List<Object> all_available_routes_between_3_lines(List<String> all_available_routes, String ex_station, String ex_station2, String start, List<String> line1, List<String> line2, List<String> line3, String end) {
        StringBuilder data=new StringBuilder();
        if (ex_station.equals(start)) {
            // Handle the case where ex_station is equal to start
            if (line3.indexOf(ex_station) < line3.indexOf(ex_station2)) {
                List<Object> result =get_route1(line3, ex_station2, ex_station, all_available_routes);
                all_available_routes = (List<String>) result.get(0);
                data.append( result.get(1).toString());
                //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
            } else if (line3.indexOf(ex_station) > line3.indexOf(ex_station2)) {
                Collections.reverse(line3);
                List<Object> result =get_route1(line3, ex_station2, ex_station, all_available_routes);
                all_available_routes = (List<String>) result.get(0); //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
                data.append(result.get(1).toString());
                // all_available_routes = Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
            }

            if (line2.indexOf(ex_station2) < line2.indexOf(end)) {
                List<Object> result =get_route2(line2, ex_station2, end, all_available_routes);
                all_available_routes = (List<String>) result.get(0); //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
                data.append(result.get(1).toString());
                // all_available_routes = Functions.get_route2(line2, ex_station2, end, all_available_routes);
            } else if (line2.indexOf(ex_station2) > line2.indexOf(end)) {
                Collections.reverse(line2);
                List<Object> result =get_route2(line2, ex_station2, end, all_available_routes);
                all_available_routes = (List<String>) result.get(0); //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
                data.append( result.get(1).toString());
                //all_available_routes = Functions.get_route2(line2, ex_station2, end, all_available_routes);
            }
        } else {
            if (line1.indexOf(start) < line1.indexOf(ex_station)) {
                List<Object> result =get_route1(line1, ex_station, start, all_available_routes);
                all_available_routes = (List<String>) result.get(0); //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
                data.append( result.get(1).toString());
                // all_available_routes = Functions.get_route1(line1, ex_station, start, all_available_routes);
            } else if (line1.indexOf(start) > line1.indexOf(ex_station)) {
                Collections.reverse(line1);
                List<Object> result =get_route1(line1, ex_station, start, all_available_routes);
                all_available_routes = (List<String>) result.get(0); //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
                data.append( result.get(1).toString());
                //all_available_routes = Functions.get_route1(line1, ex_station, start, all_available_routes);
            }

            if (line3.indexOf(ex_station) < line3.indexOf(ex_station2)) {
                all_available_routes.remove(all_available_routes.indexOf(ex_station));
                List<Object> result =get_route1(line3, ex_station2, ex_station, all_available_routes);
                all_available_routes = (List<String>) result.get(0); //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
                data.append( result.get(1).toString());
                //all_available_routes = Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
            } else if (line3.indexOf(ex_station) > line3.indexOf(ex_station2)) {
                all_available_routes.remove(all_available_routes.indexOf(ex_station));
                Collections.reverse(line3);
                List<Object> result =get_route1(line3, ex_station2, ex_station, all_available_routes);
                all_available_routes = (List<String>) result.get(0); //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
                data.append( result.get(1).toString());
                //all_available_routes = Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
            }
            if (line2.indexOf(ex_station2) < line2.indexOf(end)) {
                List<Object> result =get_route2(line2, ex_station2, end, all_available_routes);
                all_available_routes = (List<String>) result.get(0); //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
                data.append( result.get(1).toString());
                //all_available_routes = Functions.get_route2(line2, ex_station2, end, all_available_routes);
            } else if (line2.indexOf(ex_station2) > line2.indexOf(end)) {
                Collections.reverse(line2);
                List<Object> result =get_route2(line2, ex_station2, end, all_available_routes);
                all_available_routes = (List<String>) result.get(0); //Functions.get_route1(line3, ex_station2, ex_station, all_available_routes);
                data.append( result.get(1).toString());
                // all_available_routes = Functions.get_route2(line2, ex_station2, end, all_available_routes);
            }
        }
        return Arrays.asList(all_available_routes,data) ;
    }
    public static String get_nearest_station(List<String> line1, String start, List<String> exchanges, List<String> line2, List<String> all_stations, String end) {
        int first_index_f = functions.get_first_station(line1, start, exchanges, line2);
        String ex_st1 = "";
        if (first_index_f != -1) ex_st1 = line1.get(first_index_f);
        Collections.reverse(line1);
        //to get the shortest path
        int first_index_b = functions.get_first_station(line1, start, exchanges, line2);
        String ex_st2 = "";
        if (first_index_b != -1) ex_st2 = line1.get(first_index_b);
        Collections.reverse(line1);
        //if first_index_f==-1
        if (ex_st1 == "") ex_st1 = line1.get(first_index_b);
        //if first_index_b==-1
        if (ex_st2 == "") ex_st2 = line1.get(first_index_f);
        int min1 = 0;
        int min2 = 0;
        if (first_index_f != -1 && first_index_b != -1) {
            if (line1.indexOf(ex_st1) < line1.indexOf(start)) {
                min1 = line1.indexOf(start) - line1.indexOf(ex_st1);
            } else if (line1.indexOf(ex_st1) > line1.indexOf(start)) {
                min1 = line1.indexOf(ex_st1) - line1.indexOf(start);
            }
            if (line1.indexOf(ex_st2) < line1.indexOf(start)) {
                min2 = line1.indexOf(start) - line1.indexOf(ex_st2);
            } else if (line1.indexOf(ex_st2) > line1.indexOf(start)) {
                min2 = line1.indexOf(ex_st2) - line1.indexOf(start);
            }
        }
        String ex_station = "";
        if (min1 < min2) ex_station = ex_st1;
        else ex_station = ex_st2;
        return ex_station;
    }
    public  static  void get_all_info(List<String> all_available_routes, TextView ResultText,byte count,String ex_station1,String ex_station2,String start ,List<String> line1,List<String> line2,List<String> line3,String end){
        List<Object> result = functions.all_available_routes_between_3_lines(all_available_routes,ex_station1,ex_station2,start,line1,line2,line3,end);
        all_available_routes = (List<String>) result.get(0);
        ResultText.append(result.get(1).toString()+"\n");
        count = (byte) all_available_routes.size();
        String details= functions.station_details2(count,all_available_routes);
        ResultText.append(details);
    }
}