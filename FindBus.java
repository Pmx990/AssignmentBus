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
        Pattern p2 = Pattern.compile("<option value=\"(\\d*|\\c*)-?(\\d*|\\c*)?\">(\\d*|\\c*]*)/?(\\d*|\\c*)?\\s-\\s(.*)\\s?(to|-)\\s(.*)</option>");
        int n = -2;
        text2 = text2.replaceAll("&amp;","&");
        m = p2.matcher(text2);
        while(m.find()){
            n++;
            if(!m.group(2).equals("")){
                hmap.put(m.group(7),m.group(1));
                hmap.put(m.group(7),m.group(2));
            }else{
                hmap.put(m.group(7),m.group(1));

            }
            //g1:bus num    g3:start  g4:end
           // System.out.println(m.group(5));
           // System.out.println(m.group(1));
           // System.out.println(m.group(2));
           // System.out.println("-----------------------");

            //System.out.println(m.group(6));
        }
    }

}



//System.out.println(n);
//System.out.println(text2);
