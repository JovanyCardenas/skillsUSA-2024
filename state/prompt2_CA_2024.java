import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
SkillsUSA CA Project 2 4/6/2024
Completed Milestone 1-2
 */
public class prompt2 {
    public static ArrayList<File> filesFound = new ArrayList<>();
    public static String startingDir = "";
    public static int depth = -1;
    public static int counter = 0;
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);// initializes keyboard
        System.out.println("Enter filepath");
        String filepath = keyboard.nextLine(); // asks input for filepath
        startingDir = filepath;
        System.out.println("What are you searching for in that file?");
        String str = keyboard.nextLine(); // asks input for what to search


        //-1 = all dirs, 0 = only dir specified, 1 or > is how many subdirs
        System.out.println("Search Depth (-1, 0, 1):"); //Milestone 2
        depth = keyboard.nextInt();
        if (depth < -1){ // if number out of bounds, it asks again
            System.out.println("Error: Search Depth out bounds");
            System.out.println("Try again:");
            depth = keyboard.nextInt();
        }

        try{
            File f = new File(filepath); // opens starting folder
            File[] files = f.listFiles(); // adds all files to files list
            for (int x = 0; x < files.length;x++){ //Milestone 1
                if (String.valueOf(files[x]).contains(str)){ //Starting dir
                    filesFound.add(files[x]); // if the file name contains what you are looking for it will add it to the list
                }
                if((new File(String.valueOf(files[x])).isDirectory()) && ((depth > 0) || (depth == -1))){ // if the files are a subdirectory and contain other files
                    search(files[x], str); // checks subdir
                }
            }
        }catch (Exception e){ // catches all exceptions
            System.out.println(e.getMessage()); //prints exception message
        }
        System.out.println(filesFound);// outputs files found
    }
    private static void search(File file, String str){ //recursive function
        File[] subfiles = new File(String.valueOf(file)).listFiles();
        for (int y = 0; y < subfiles.length;y++){
            if (depth > 0){counter++;} // adds to the counter for depth
            if (String.valueOf(subfiles[y]).contains(str)){ // if the filepath contains what the user is searching for it adds it to the list
                filesFound.add(subfiles[y]);
            }
            if ((new File(String.valueOf(subfiles[y])).isDirectory()) ){ // recursive ; calls itself if it is a subdir to check those
                if (depth == -1 || depth > counter) { //makes sure it doesn't go past the number of depth entered
                    search(subfiles[y], str);
                }
            }
        }
    }
}