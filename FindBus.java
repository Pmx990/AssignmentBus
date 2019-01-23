import java.nio.charset.Charset;
import java.util.regex.*;
import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;
//using google guava here
//https://github.com/google/guava
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class FindBus {
    public static Multimap<String,String> hmap = ArrayListMultimap.create();
    //Multimap<Destination,Bus number>, since it is a multimap i can have one key with more than one values

    FindBus() throws Exception{
        getRoutes();
        chooseRoute();
    }

    void getRoutes() throws Exception{
        /*
        Connect to the website and download the html
         */
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
        /*
        p: cut the useful part from the original text
        p2: find the bus number and destination
         */
        Pattern p = Pattern.compile("Select Route</option>(\\s(.*)\\s)*<div class=\"panel panel-warning sidebarPanel\">");
        Matcher m = p.matcher(text);
        m.find();
        String text2 = m.group();
        Pattern p2 = Pattern.compile("<option value=\"((\\d*|\\c*)-?(\\d*|\\c*)?)\">((\\d*|\\c*)-?(\\d*|\\c*)?)/?(\\d*|\\c*)?\\s-\\s(.*)\\s?(to|-)\\s(.*)</option>");
        int n = -2;                                 //^ unreadable thing, i already forgot how i wrote it
        text2 = text2.replaceAll("&amp;","&");
        m = p2.matcher(text2);
        //---------------------------------------------------------------------
        /*
        With the bus number(value) and destination here, we can save them into the map.
        This step could be simplify though, i didn't expect i don't need the map in the
        finding schedule step
         */
        while(m.find()){
            n++;
            //System.out.println(m.group(10));
            //System.out.println(m.group(1));
            hmap.put(m.group(10),m.group(1));
        }
    }
        //-----------------------------------------------------------------------
    void chooseRoute()throws Exception{
        Scanner scan = new Scanner(System.in);
        System.out.println("Please type your destinations(with the top several or all character(s)");
        String input;
        input = scan.next();
        input = "[\\[,]\\s*("+input+"[a-zA-Z]*(\\s*[a-zA-Z]*)*)[,]";
        /*
        Make the input as a Regular expression that will check the destination begins as the input
         */
        Pattern p3 = Pattern.compile(input,Pattern.CASE_INSENSITIVE);
        Matcher m3 = p3.matcher(hmap.keySet().toString());
        //------------------------------------------------------------
        /*
        Show the bus numbers and let the user choose
         */
        while(m3.find()){
        System.out.println(m3.group(1));
        System.out.println(hmap.get(m3.group(1)));
        }
        System.out.println("Bus Number:");
        input = scan.next();
        //------------------------------------------------------------
        new FindSchedule(input);//call the Find method
    }

}



//System.out.println(n);
//System.out.println(text2);
