
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.*;
import java.net.*;
import java.io.*;
import java.nio.*;
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
        //System.out.println(text);
    }

    void showSchedule(){
        Pattern p = Pattern.compile("(<p>(.*)</p>)|(<h2>(.*)<small>(.*)</small></h2>)");
        Matcher m = p.matcher(text);
        List<String> sta = new ArrayList<>();
        String temp = "";
        while(m.find()){
            //System.out.println(m.group());
        //System.out.println(m.group(1));
            if(m.group().contains("<h2>")){
                 temp = m.group(4) +"  "+ m.group(5);
                 temp = "------------" + temp +"-----------";
            }else{
                 temp = m.group(2).replaceAll("&amp;","&");
            }
        sta.add(temp);
        }
        int count = 1;
        for(int i = 1; i<sta.size()-1;i++){
            if(!sta.get(i).contains("-")){
                System.out.println("Stop"+count+" "+sta.get(i));
                count++;
            }else{
                System.out.println(sta.get(i));
                count = 1;
            }



        }
       // m.find();
       // String part2 = m.group();
       // System.out.println(part1);

    }
}