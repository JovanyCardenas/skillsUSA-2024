import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

//CONTESTANT 9258
//Regionals 2024

public class AlienHelp {
    /*
     * Geostationary Orbit - Problem 1
     * TODO - Write this function and document
     */
    public static double orbit(double radius, double altitude){
        final double pi = 3.14;
        final int hoursInADay = 24;
        // Your Code goes here
        double velocity = 2*pi*(radius+altitude)/hoursInADay; //V = 2 * pi (radius + altitude) / 24hrs  =  ___km/hr
        return velocity;
    }

    /*
     * DigBuild - Problem 2
     * TODO - Write this function and document
     */
    public static String[] log(String filename){
        String[] result = new String[6];

        // Your Code goes here
        //Initialize data
        String[] fileSt = new String[7]; //Stores File information here
        int usersJoined = 0; //user count
        String userDied = "False"; //if users have died
        int monstersSlayed = 0; //how many monsters killed
        String crash = "No crash"; //if there is a crash in the code
        String fileFormatted = "True"; //Is the file formatted correctly

        File file = new File(filename); //Read File
        Scanner fr;

        try {
            fr = new Scanner(file);
            int x = 0;
            while (fr.hasNextLine()){
                fileSt[x] = fr.nextLine();
                if (fileSt[x].contains("Joined")){ //Add to users joined counter
                    usersJoined++;
                }
                if (fileSt[x].contains("Was slain") || fileSt[x].contains("Has perished")){ //If users died set to true
                    userDied = "True";
                }
                if (fileSt[x].contains("Slayed monster")){ //Add to monsters killed counter
                    monstersSlayed++;
                }
                if (fileSt[x].contains("Unknown event")){ // if it crashed
                    crash = "Crashed";
                }
                x++;
            }
            if (fileSt[0].contains("Start")){ //Formatted correctly?
                if (fileSt[6].contains("End")){
                    fileFormatted = "True"; //yes
                } else{
                    fileFormatted = "False"; //no
                }
            } else{
                fileFormatted = "False"; //no
            }
            String[] token = fileSt[1].split(" ", 3); //split to get name only
            token[1] = token[1].substring(1, token[1].length()-1); // remove [ ]

            result[0] = token[1]; //First person to join
            result[1] = String.valueOf(usersJoined); // how many users joined
            result[2] = userDied; // if users died
            result[3] = String.valueOf(monstersSlayed); //how many monsters killed
            result[4] = crash; // if game crashed
            result[5] = fileFormatted; // if file is formatted correctly

        }catch(Exception e){ // if file not found or other exception it will print out error message
            System.out.println(e.getMessage());
        }

        return result; //outputs final results
    }

    /*
    * DigBuild Build System - Problem 3
    * TODO - Write this function and document
    */
   public static ArrayList<String> build(String filename, ArrayList<String> inventory){
       ArrayList<String> buildable = new ArrayList<>();
       // Your Code goes here
       ArrayList<String> fileArr = new ArrayList<>();
       ArrayList<String> fileArr2 = new ArrayList<>();
       ArrayList<String> counter = new ArrayList<>();

       for (String e : inventory){
           int i = Collections.frequency(inventory,e); //count total of the items
           if (!(counter.contains(e))){
               counter.add(i + "," + e);
           }
       }
       try{
           File file = new File(filename); //read file
           Scanner scanner = new Scanner(file);
           while(scanner.hasNextLine()) { //goes through each line in file
               String next = scanner.nextLine();
               fileArr2.add(next); //add file text to arraylist
           }
           for (String e : fileArr2){ //loops
               String next1[] = e.split(","); //splits into each individual item per loop
               if (inventory.contains(next1[2])) { //saves items for example inventory has Sticks, it adds to arr
                   fileArr.add(next1[0]);
               }
               buildable = fileArr;
           }
       }catch(Exception e){ //outputs error message if error occurs like filenotfound
           System.out.println(e.getMessage());
       }

        return buildable;
   }
}
