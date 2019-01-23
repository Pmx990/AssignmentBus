
import java.nio.charset.Charset;
import java.util.regex.*;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;

public class FindSchedule {
    String num;
    String text = "";

    FindSchedule(String n)throws Exception {
        num = n;
        loadPage();
        showSchedule();
    }
    //----------------------------------------------------------------------------------------------------
    /*
    Download the html
     */
    void loadPage()throws Exception{
        String theurl= "https://www.communitytransit.org/busservice/schedules/route/"+num;
        System.out.println(theurl);
        URLConnection web = new URL(theurl).openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(web.getInputStream(), Charset.forName("UTF-8")));
        String inputLine = "";
        while((inputLine= in.readLine()) != null){
            text += inputLine + "\n";
        }
        in.close();
    }
    //-----------------------------------------------------------------------------------------------------
    /*
    Use Reg the find the stops and directions, use '-' to differentiate
     */
    void showSchedule() {
        Pattern p = Pattern.compile("(<p>(.*)</p>)|(<h2>(.*)<small>(.*)</small></h2>)");
        Matcher m = p.matcher(text);
        List<String> sta = new ArrayList<>();
        String temp = "";
        while (m.find()) {
            if (m.group().contains("<h2>")) {
                temp = m.group(4) + "-- " + m.group(5);
                temp = "------------" + temp + "-----------";
            } else {
                temp = m.group(2).replaceAll("&amp;", "&");
            }
            sta.add(temp);
        }
        int count = 1;
        /*
        Print the result, since i used a indistinct reg above, i need some ifs to avoid printing useless things
         */
        for (int i = 1; i < sta.size() - 1; i++) {
            if (sta.get(i).contains("<")) {
                continue;
            } else if (!sta.get(i).contains("---")) {
                System.out.println("Stop" + count + " " + sta.get(i));
                count++;
            } else {
                System.out.println(sta.get(i));
                count = 1;
            }
        }
    }

        }


