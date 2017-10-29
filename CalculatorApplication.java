/*
 * Name: Antonio Guadarrama
 * Class: CS480001
 * Lab: 3
 */
package calculatorapplication;

import java.util.*;

/**
 *
 * @author Antonio Guadarrama
 */
public class CalculatorApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        CalculatorApplication.run();
    }

    private static void run() {
        // variables
        Scanner kb = new Scanner(System.in);
        boolean run = true;
        boolean validInput = true;
        
        while(run) {
            System.out.println("Type a new equation. Type q end the program");
            String userInput = "";
            double output = 0.0;

            // get user input (string)
            userInput = kb.nextLine();

            // check user input
            validInput = checkUserInput(userInput);
            
            if(validInput == false) {
                // if is not a valid user input, restart the loop (restart program)
                continue;
            }
            
            output = computeEquation(userInput);
            
        }
    }
    
    public static boolean checkUserInput(String userInput) {
        if(userInput.isEmpty()) {
            // bad user input (no input) return error and ask for new input
            System.out.println("Bad user input\n");
            return false;
        } else if(userInput.equals("q")) {
            // end the program
            System.out.println("---Program will now exit---");
            System.exit(0);
        } else if(checkParenthesis(userInput) == false) {
            return false;
        }
        return true;
    }
    
    public static boolean checkParenthesis(String userInput) {
        // check if there are the same number of closed parenthesis as open parenthesis
        return true;
    }
    
    public static double computeEquation(String userInput) {
        double answer = 0.0;
        
        return answer;
    }
    
}
