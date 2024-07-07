import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
    NLSC Project #2
    Contestant #5105
    6/26/2024
    Finished: 11:00 AM
 */

public class project2 extends JFrame {
    private final ArrayList<String> data = new ArrayList<>();
    private final ArrayList<String> output = new ArrayList<>();

    public static void main(String[] args) {
        new project2(); //Creates GUI
    }

    public project2() { //Create GUI with the title, size...
        super.setTitle("Payroll Calculator");
        super.setSize(600, 800);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        build(); //Calls to build function to create GUI
        super.setVisible(true);
    }

    private void build() {
        JTextArea area = new JTextArea(); //Creates Text Area where Output will be
        JScrollPane scroll = new JScrollPane(area); //Creates scrollable area
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter filename: ");
        JTextField textField = new JTextField("payroll.csv", 15);
        JButton submit = new JButton("Submit");

        panel.add(label);
        panel.add(textField);
        panel.add(submit);

        JButton exit = new JButton("Exit"); //Will close the application
        exit.setPreferredSize(new Dimension(50, 50));
        area.setEditable(false); //To make the Area non-editable

        super.add(scroll, BorderLayout.CENTER); //Adds both panels to the main GUI and puts them where they should be.
        super.add(exit, BorderLayout.SOUTH);
        super.add(panel, BorderLayout.NORTH);

        String fileName = textField.getText();
        calculate(fileName); //Calls to calculate function which calculates all the information
        for (String x : output) { //Loops through every person saved in the output arraylist and adds it to the GUI
            area.append(x);
        }
        area.append("---------------------- EOF ----------------------\n\n\n"); //End of File

        submit.addActionListener(event -> {
            output.clear();
            data.clear();
            String file = textField.getText();
            calculate(file); //Calls to calculate function which calculates all the information
            for (String x : output) { //Loops through every person saved in the output arraylist and adds it to the GUI
                area.append(x);
            }
            area.append("---------------------- EOF ----------------------\n\n\n"); //End of File
        });
        exit.addActionListener(event -> { //This exits the program when the button is pressed
            System.exit(0);
        });
    }

    private void calculate(String fileName) {
        readCSV(fileName); //Reads the CSV file and gets information

        //Calculates employ information
        //The loop goes through each individual entry and calculates their information
        for (String person : data) {  //Gets Data and splits it into different values
            String[] token = person.split(","); //Splits the line by commas

            if (token.length != 5) { //Check input from file that there is 5 items
                JOptionPane.showMessageDialog(null, "There is incorrect input in the file\n" +
                        "It should be FirstName,LastName,SSN,PayCode,HoursWorked", "Failure", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }


            String name = token[0] + " " + token[1]; //Saves the token values as a String variable
            String SSN = token[2];
            String paycode = token[3];
            String hoursWorked = token[4];
            final double insuranceCost = 12; //Constant value of Insurance cost

            //Calculate all the different wages, hours, and costs
            double wage = wageCalculator(Integer.parseInt(paycode));
            String wageString = String.format("%.2f", wage); //Will format it for display purposes
            double OTHours = calculateOTHours(Integer.parseInt(hoursWorked));
            double regHours;
            if (OTHours > 0) {  //This calculates regular hours by seeing if there is overtime or not
                regHours = 40;
            } else {
                regHours = Integer.parseInt(hoursWorked);
            }
            double regPay = calculateRegPay(wage, regHours);
            String regPayString = String.format("%.2f", regPay); //Will format it for display purposes
            double OTPay = calculateOTPay(wage, OTHours);
            String OTPayString = String.format("%.2f", OTPay); //..
            double taxes = calculateTaxes(regPay, OTPay);
            String taxesString = String.format("%.2f", taxes); //..
            double totalPay = calculateTotalPay(regPay, OTPay, taxes, insuranceCost); //calculate final pay
            String totalPayString = String.format("%.2f", totalPay); //..

            //This is the final Output per person
            output.add(name + "\n" +
                    "SS#: " + SSN +
                    "\n–-----------------\n" +
                    "Wage: $" + wageString + "\n" +
                    "Reg Hours: " + regHours + "\n" +
                    "Reg Pay: $" + regPayString +
                    "\nOT Hours: " + OTHours +
                    "\nOT Pay: $" + OTPayString +
                    "\nTaxes: $" + taxesString +
                    "\nInsurance: $12.00" +
                    "\n–-----------------\n" +
                    "Total Pay: $" + totalPayString + "\n\n\n");
        }
    }

    private double calculateTotalPay(double RegPay, double OTPay, double taxes, double insurance) { //Calculates Total Pay
        return RegPay + OTPay - taxes - insurance;
    }

    private double calculateTaxes(double RegPay, double OTPay) { //Calculates Taxes
        double beforeTax = RegPay + OTPay;
        return beforeTax * 0.1; //Gets 10% of the total pay as taxes
    }

    private double calculateRegPay(double wage, double regHours) { //Calculates Pay that is not OverTime
        return wage * regHours;
    }

    private double calculateOTPay(double wage, double OTHours) { //Calculates Pay that is OverTime
        return (wage * 1.5) * OTHours;
    }

    private double wageCalculator(int paycode) { //Calculates the Wage per paycode
        double wage = 0;
        if (paycode == 1) {
            wage = 15;
        } else if (paycode == 2) {
            wage = 17.5;
        } else if (paycode == 3) {
            wage = 20;
        } else if (paycode == 4) {
            wage = 22.25;
        } else if (paycode == 5) {
            wage = 25;
        } else {
            System.out.println("Invalid Pay code");
        }
        return wage;
    }

    private double calculateOTHours(int hoursWorked) { //Calculates the OverTime Hours worked
        double overtimeHours = 0;
        if (hoursWorked > 40) {
            overtimeHours = hoursWorked - 40;
        }
        return overtimeHours;
    }

    private void readCSV(String fileName) { //Reads CSV payroll file and saves information
        //FirstName, LastName, Last4SSN, PayCode, HoursWorked
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) { //Adds each line to ArrayList
                String nextLine = scanner.nextLine();
                data.add(nextLine);
            }
        } catch (Exception e) { //Catches any errors when reading the CSV file
            System.out.println(e.getMessage());
        }
    }
}
