import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Alien{
    static String outfile = "";
    public static void main(String[] args){
        String pgm = "";
        String test = "";
        if (args.length == 0){
            System.out.println("<///AlienOS///>\nHuman problems require human solutions");
            System.out.println("1. Orbit test");
            System.out.println("2. Log test");
            System.out.println("3. Build test");
            System.out.print("Enter the number of the test you would like to run> ");

            Scanner scan =new Scanner(System.in);
            String choice = scan.nextLine();
            scan.close();

            if (choice.trim().equals("1")){
                pgm = "orbit";
            } else if (choice.trim().equals("2")){
                pgm = "log";
            } else if (choice.trim().equals("3")){
                pgm = "build";
            } else {
                System.err.println("That was not a valid option!");
                System.exit(1);
            }
            test = "test";


        } else if (args.length == 2 || args.length == 3){
            pgm = args[0];
            test = args[1];
            if (args.length == 3){
                outfile = args[2];
            }

            if(! (pgm.equals("orbit") || pgm.equals("log") || pgm.equals("build"))){
                printErrorExit();
            }

            if (!checkFile(test)){
                printErrorExit();
            }
        } else {
            printErrorExit();
        }
        

        if (pgm.equals("orbit")){
            runOrbitTest(test);
        } else if (pgm.equals("log")){
            runLogTest(test);
        } else if (pgm.equals("build")){
            runBuildTest(test);
        } else {
            printErrorExit();
        }
    }

    public static void runOrbitTest(String testName){
        if (testName.equals("test")){
            testName = "orbit-test.txt";
        }
        File input = new File(testName);
        Scanner scanner;
        try{
            scanner = new Scanner(input);
        } catch (FileNotFoundException e){
            System.err.println("File not found");
            return;
        }
        
        double radius = 0;
        double altitude = 0;
        while(scanner.hasNextDouble()){
            try {
                radius = scanner.nextDouble();
                altitude = scanner.nextDouble();
            } catch (Exception e){
                System.err.println("Invalid test format!");
                scanner.close();
                return;
            }
        }
        scanner.close();
        double answer = AlienHelp.orbit(radius, altitude);
        NumberFormat formatter = new DecimalFormat("#0.000");  
        write("" + formatter.format(answer));
    }

    public static void runLogTest(String testName){
        if (testName.equals("test")){
            testName = "log-test.txt";
        }

        String[] results = AlienHelp.log(testName);
        for(String str : results){
            if (str != null){
                write(str.toLowerCase());
            }
        }
    }

    public static void runBuildTest(String testName){
        if (testName.equals("test")){
            testName = "build-test.txt";
        }
        
        ArrayList<String> inventory = new ArrayList<>();
        File input = new File(testName);
        Scanner in;
        try{
            in = new Scanner(input);
        } catch (FileNotFoundException e){
            System.err.println("File not found");
            return;
        }
        while (in.hasNextLine()){
            String line = in.nextLine();
            inventory.add(line);
        }
        in.close();

        ArrayList<String> answer = AlienHelp.build("Recipes.csv", inventory);
        for (String item : answer){
            write(item);
        }
    }

    public static void printErrorExit(){
        System.err.println("Please enter valid arguments!");
        System.err.println("[orbit | log | build] test");
        System.exit(1);
    }

    public static boolean checkFile(String file){
        if (file.equals("test")){
            return new File("orbit-test.txt").isFile() && new File("log-test.txt").isFile() && new File("build-test.txt").isFile();
        }
        return new File(file).isFile();
    }

    private static void write(String text){
        System.out.println(text);
        if (outfile.equals("")){
            return;
        }
        try{
            FileWriter fw = new FileWriter(outfile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (Exception e){
            System.out.println("Unable to write to file!");
        }
        
    }
}
