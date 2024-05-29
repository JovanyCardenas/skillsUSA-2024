import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*
Program 1 Finished 9:20am 4/6/24
 */
public class prompt1 {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in); //Initializes keyboard input
        System.out.println("Enter a Range ex. 1,2,3,5-8"); //Prints out to console
        String str = keyboard.nextLine(); //Reads input and saves as str
        System.out.println(rangeInterpreter(str)); //Calls to function
    }
    public static ArrayList<Integer> rangeInterpreter(String str){
        String arr[] = str.split(","); //Splits string into individual components
        ArrayList<Integer> output = new ArrayList<>(); //Initializes output arraylist ; arraylist because I don't know how many numbers are in a range
        try {
            for (int x = 0; x < arr.length; x++) {
                if (arr[x].contains("-")) { //If there is a hyphen then it checks to see if it starts as a neg. # or a range
                    String current = arr[x];

                    if (current.startsWith("-")) { //Catches negative numbers
                        if (current.length() > 2) {
                            if ((current.charAt(2) == '-') && (current.charAt(3) == '-')) {
                                // -5--1 ; -5,-4,-3,-2,-1
                                int num1 = (-1 * current.charAt(1)) + 48; //first number in the current range
                                int num2 = (-1 * current.charAt(current.length() - 1)) + 48; //last number in the current range
//                                System.out.println(num1);
//                                System.out.println(num2);
                                if (num1 < num2) {
                                    for (int i = num1; i <= num2; i++) {
                                        output.add(i);//adds values to output array
                                    }
                                } else {
                                    System.out.println("Range is reversed and invalid!");
//                                    System.out.println("test3");
                                    break; //Exits loop
                                }
                            } else if (current.charAt(0) == '-' && current.charAt(2) == '-' && current.charAt(3) != '-') { // only one -1-5 ; -1,0,1,2,3,4,5
                                int num1 = current.charAt(0) + current.charAt(1) + (-48); //first number in the current range
                                int num2 = current.charAt(current.length() - 1) - 48; //last number in the current range
                                if (num1 > num2) {
                                    for (int i = num1; i <= num2; i++) {
                                        output.add(i);//adds values to output array
                                    }
                                } else {
                                    System.out.println("Range is reversed and invalid!");
//                                    System.out.println("test2");
                                    break; //Exits loop
                                }
                            }
                        } else {
                            output.add(Integer.parseInt(current)); //usually something like -5
                        }
                    } else if ((current.length() > 3 && Character.isDigit(current.charAt(3)))) { //ex 5-11 ; 5,6,7,8,9,10,11
                        //Need to check for numbers with double digits
                        int num1 = current.charAt(0) - 48; //first number in the current range
                        String num2str = "" + (current.charAt(current.length() - 2) + (current.charAt(current.length() - 1) - 87)); //last number in the current range
                        int num2 = Integer.parseInt(num2str);
//                        System.out.println(num1);
//                        System.out.println(num2);
                        if (num1 < num2) {
                            for (int i = num1; i <= num2; i++) {
                                output.add(i);//adds values to output array
                            }
                        } else {
                            System.out.println("Range is reversed and invalid!");
//                            System.out.println("test");
                        }
                    }else {
                        int num1 = current.charAt(0) - 48; //first number in the current range
                        int num2 = current.charAt(current.length() - 1) + (-48); //last number in the current range
//                        System.out.println(num1);
//                        System.out.println(num2);
                        if (num1 < num2) {
                            for (int i = num1; i <= num2; i++) {
                                output.add(i);//adds values to output array
                            }
                        } else {
                            System.out.println("Range is reversed and invalid!");
//                            System.out.println("test");
                        }
                    }
                } else {
                    output.add(Integer.parseInt(arr[x])); //if non of the cases are triggered it adds it to output list
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Collections.sort(output); // Sorts range in order so if it is 5,7,9,3,2 ; it will display 2,3,5,7,9
        ArrayList<Integer> noDupes = new ArrayList<>(); // removes all duplicate numbers
        for (int current : output){
            if (!(noDupes.contains(current))){
                noDupes.add(current);
            }
        }
        return noDupes; //returns the output
    }
}