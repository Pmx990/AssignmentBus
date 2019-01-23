import java.util.Scanner;

/*
This is a program design to find and print the bus info from communitytransit.org
 */
public class AppUi {
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);
        outerloop:
        while (true){
            new FindBus();
            while(true){
                System.out.println("-------------------------------");
                System.out.println("Do you want another search?(Y/N)");
                String input = scan.next();
                if(input.equalsIgnoreCase("n")){
                    break outerloop;
                }else if(input.equalsIgnoreCase("y")){
                    break;
                }else{
                    System.out.println("Wrong input, please try again");
                }
            }

        }

    }

}

