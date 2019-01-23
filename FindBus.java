import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.*;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.lang.*;
import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class FindBus {
    public static Multimap<String,String> hmap = ArrayListMultimap.create();
    FindBus() throws Exception{
        getRoutes();
        chooseRoute();
    }
    void getRoutes() throws Exception{
        URLConnection web = new URL("https://www.communitytransit.org/busservice/schedules/").openConnection();
        web.setRequestProperty("user-Agent","Mozilla/5.0(Windows  NT 6.1; WOW64) AppleWebKit/537.11(KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        BufferedReader in = new BufferedReader(new InputStreamReader(web.getInputStream(), Charset.forName("UTF-8")));
        String inputLine = "";
        String text = "";
        while((inputLine= in.readLine()) != null){
            text += inputLine + "\n";
        }
        in.close();
    //------------------------------------------------------------------
        //System.out.println(text);
        Pattern p = Pattern.compile("Select Route</option>(\\s(.*)\\s)*<div class=\"panel panel-warning sidebarPanel\">");
        Matcher m = p.matcher(text);
        m.find();
        String text2 = m.group();
        //Pattern p2 = Pattern.compile("<option value=\"(\\d+)\">(\\d)*\\s-\\s(.*)\\sto(\\s)(.*)</option>");
        Pattern p2 = Pattern.compile("<option value=\"((\\d*|\\c*)-?(\\d*|\\c*)?)\">((\\d*|\\c*)-?(\\d*|\\c*)?)/?(\\d*|\\c*)?\\s-\\s(.*)\\s?(to|-)\\s(.*)</option>");
        int n = -2;
        text2 = text2.replaceAll("&amp;","&");
        m = p2.matcher(text2);
        while(m.find()){
            n++;
            /*if(!m.group(2).equals("")){
                hmap.put(m.group(7),m.group(1));//7 des
                hmap.put(m.group(7),m.group(2));
            }else{*/
            System.out.println(m.group(10));
            System.out.println(m.group(1));
                hmap.put(m.group(10),m.group(1));
            //}
        }
        System.out.println(hmap.keySet().toString());
    }
    void chooseRoute()throws Exception{
        Scanner scan = new Scanner(System.in);
        System.out.println("Please type your destinations");
        String input;
        input = scan.next();
        //input = "sea";
        input = "[\\[,]\\s*("+input+"[a-zA-Z]*(\\s*[a-zA-Z]*)*)[,]";
        Pattern p3 = Pattern.compile(input,Pattern.CASE_INSENSITIVE);
        Matcher m3 = p3.matcher(hmap.keySet().toString());
        System.out.println(input);
        System.out.println(hmap.keySet().toString());
        while(m3.find()){
        System.out.println(m3.group(1));
        System.out.println(hmap.get(m3.group(1)));
        }
        System.out.println("Bus Number:");
        input = scan.next();
    new FindSchedule(input);
    }

}



//System.out.println(n);
//System.out.println(text2);
