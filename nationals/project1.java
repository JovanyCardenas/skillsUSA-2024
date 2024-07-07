import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

/*
    NLSC Project #1
    Contestant #5105
    6/26/2024
    Finished: 9:15 AM
 */

public class project1 extends JFrame {
    public static void main(String[] args) {
        new project1();
    }

    public project1() { //Create GUI with the title, size...
        super.setTitle("Dice Roller");
        super.setSize(600, 400);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        build(); //Calls to build function to create GUI
        super.setVisible(true);
    }

    public void build() {
        JPanel panel = new JPanel(); //Main panel to have the inputs

        JLabel sides = new JLabel("How many sides do you want to roll for?");
        JTextField sideNum = new JTextField("6", 10);
        //Add inputs here!!

        panel.add(sides);
        panel.add(sideNum);

        JPanel buttonGroup = new JPanel(); //Second panel to group the buttons together
        JButton exit = new JButton("Exit"); //Will close the application
        JButton rollDice = new JButton("Roll Dice"); //Will run the code
        buttonGroup.add(exit);
        buttonGroup.add(rollDice);

        super.add(panel, BorderLayout.CENTER); //Adds both panels to the main GUI and puts them where they should be.
        super.add(buttonGroup, BorderLayout.SOUTH);

        exit.addActionListener(event -> { //This exits the program when the button is pressed
            System.exit(0);
        });
        rollDice.addActionListener(event -> { //This submits and runs the intended program when the button is pressed
            String text = sideNum.getText();
            boolean allNum = true; //Input check that it is a number all the time
            for (int y = 0; y < text.length(); y++) {
                if (!Character.isDigit(text.charAt(y))) { //if not a number it will set allnum to false
                    allNum = false; //Input Validation Check
                }
            }
            if (allNum) { //Number Validation
                int result = rollDice(Integer.parseInt(text)); //Calls to the main function
                int rollAgain = JOptionPane.showConfirmDialog(this, "Your Result: " + result + "\nDo you want to roll again?"); //Outputs result
                if (rollAgain == 0) {
                    rollDice.doClick();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Input needs to be a Number", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public static int rollDice(int sides) {
        Random random = new Random(); //To generate random side
        int[] dice = new int[sides]; //Creates array to fill with values
        int sideLanded = random.nextInt(sides); //generates random side


        //tbh with the random generator it probably doesn't need to select a side and could just output that random number
        for (int x = 0; x < sides; x++) { //creates an array with the total number on each side
            dice[x] = x + 1;
        }
        logResults(dice[sideLanded]);
        return dice[sideLanded]; //return the number the dice landed on
    }

    public static void logResults(int result) {
        String fileName = "roll_history.log"; //File name
        String date = getTime();
        File file = new File(fileName); //Gets the file
        try {
            if (!file.exists()) { //If the file doesn't exist it will create it
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(new FileWriter(file, true)); //Appends the file and adds to it

            String z = date + "  " + result + "\n";
            pw.append(z);
            pw.flush(); //close the printwriter
            pw.close();
        } catch (Exception e) { //If any error occurs it will show it
            System.out.println(e.getMessage());
            System.out.println("Could not log into file");
        }
    }

    public static String getTime() { //Creates the time to display in the log file

        //I know this is a depreciated API but this was the one I knew
        Date date = new Date();
        int seconds = date.getSeconds();
        int minute = date.getMinutes();
        int hour = date.getHours();
        int day = date.getDate();
        int month = date.getMonth() + 1; //I believe it starts the first month at 0 so I did +1
        int year = date.getYear() - 100; //It says 124 so I took 100 from it to get 24

        String time = String.valueOf(hour) + ":" + String.valueOf(minute) + ":" + String.valueOf(seconds);

        String today = String.valueOf(month) + "-" + String.valueOf(day) + "-" + String.valueOf(year) + "  " + time;
        return today;
    }
}
