import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.*;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.lang.*;
import java.util.*;
public class FindBus {

    FindBus() throws Exception{
        showRoutes();
    }
    void showRoutes() throws Exception{
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
        Pattern p2 = Pattern.compile("<option value=\"(\\d+[-]?\\d*)\">(\\d)*\\s-\\s(.*)?(amp;)(.*)to\\s(.*)</option>");
        m = p2.matcher(text2);
        int n = -2;
        HashMap<Integer,String> hmap = new HashMap<Integer,String>();
        while(m.find()){
            n++;

            //System.out.println(m.group(1));
            //System.out.println(m.group(3) + m.group(5));
            //System.out.println(m.group(6));
            //g1:bus num    g3g5:start  g6:end
            int a = Integer.valueOf(m.group(1));
            System.out.println(a);
            hmap.put(a,m.group(6));

        }
        Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("key is: "+ mentry.getKey() + " & Value is: ");
            System.out.println(mentry.getValue());
        }
        //System.out.println(n);
        //System.out.println(text2);

    }
}
