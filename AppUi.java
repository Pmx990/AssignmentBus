import java.util.Scanner;

/*
This is a program design to find and print the bus info from communitytransit.org
 */
public class AppUi {
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);
        while (true){
            new FindBus();
            System.out.println("Do you want another search?(Y/N)");
            String input = scan.next();
            if(input.equalsIgnoreCase("n")){
                break;
            }
        }

    }

}

