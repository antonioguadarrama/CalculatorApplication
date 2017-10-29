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
            System.out.println("Error: Bad user input\n");
            return false;
        } else if(userInput.equals("q")) {
            // end the program
            System.out.println("---Program will now exit---");
            System.exit(0);
        } else if(userInput.length() > 50) {
            System.out.println("Error: Input too long\n");
            return false;
        } else if(checkParenthesis(userInput) == false) {
            return false;
        }
        return true;
    }
    
    public static boolean checkParenthesis(String userInput) {
        // variables
        //StringTokenizer token = new StringTokenizer(userInput, "^*/+-0123456789");
        ArrayList<String> characters = new ArrayList<String>(Arrays.asList(userInput.split("")));
        
        // add the tokens into the arraylist
        //while(token.hasMoreTokens()) {
        //    characters.add(token.nextToken());
        //}
        
        int leftParenthesis = 0;
        int rightParenthesis = 0;
        
        // count the number of left & right parenthesis (should be equal)
        for (String character : characters) {
            if(rightParenthesis > leftParenthesis) {
                System.out.println("Error: closed parentheses before open parentheses\n");
                return false;
            }
            if (character.equals("(")) {
                leftParenthesis++;
            } else if (character.equals(")")) {
                rightParenthesis++;
            }
        }
        // if number of open and closed parenthesis are not equal, return false
        if(leftParenthesis != rightParenthesis) {
            System.out.println("Error: number of open and closed parentheses are not equal\n");
            return false;
        }   // check if there are the same number of closed parenthesis as open parenthesis
        return true;
    }    
    public static double computeEquation(String userInput) {
        double answer = 0.0;
        int startIndex = 0;
        int endIndex = 0;
        String currentEquation = "";
        boolean equationSolved = false;
        
        userInput = "(" + userInput + ")";
        
        while(!equationSolved) {
            ArrayList<String> characters = new ArrayList<String>(Arrays.asList(userInput.split("")));
            startIndex = findStart(characters); // starting index is updated
            System.out.println("Start index: " + startIndex);
            endIndex = findEnd(characters, startIndex);
            System.out.println("End index: " + endIndex);
            // parse the substring between the start index and end index
            currentEquation = userInput.substring(endIndex, endIndex);
            // then solve
            solveEquation(currentEquation);
            // update userInput
            userInput = userInput.substring(0, startIndex) + currentEquation
                    + userInput.substring(endIndex, userInput.length());
        }
        return answer;
    }
    
    public static int findStart(ArrayList<String> characters) {
        int level = 0;      // current level of parenthesis
        int maxLevel = 0;   // max level of parenthesis found
        int maxIndex = 0;   // index of max level parenthesis found
        
        // find the furthest set of parenthesis and start solving there
        for (int i = 0; i < characters.size(); i++) {
            if(characters.get(i).equals("(")) {
                level++;
            } else if(characters.get(i).equals(")")) {
                level--;
            }
            if(level > maxLevel) {
                maxLevel = level;
                maxIndex = i;
            }
        }
        
        return maxIndex;
    }
    
    public static int findEnd(ArrayList<String> characters, int startIndex) {
        for(int i = startIndex; i < characters.size(); i++) {
            if(characters.get(i).equals(")")) {
                return i;
            }
        }
        return characters.size(); // 
    }
    
    public static double solveEquation(String equation) {
        double returnValue = 0.0;
        // remove parenthesis at beginning and end
        equation = equation.substring(1, equation.length()-1);
        
        
        
        return returnValue;
    }
}
