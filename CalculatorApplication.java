/*
 * Name: Antonio Guadarrama
 * Class: CS480001
 * Lab: 3
 */
package calculatorapplication;

import java.util.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 *
 * @author Antonio Guadarrama
 */
public class CalculatorApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ScriptException {
        // TODO code application logic here
        CalculatorApplication.run();
    }

    private static void run() throws ScriptException {
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
        } else if(userInput.length() < 3) {
            System.out.println("Error: Input too short\n");
            return false;
        } else if(checkParenthesis(userInput) == false) {
            return false;
        } else if(checkSymbols(userInput) == false) {
            return false;
        }

        if(userInput.contains("+") 
            || userInput.contains("-") 
            || userInput.contains("*") 
            || userInput.contains("/")
            || userInput.contains("^")){
        } else {
            System.out.println("Error: Equation not valid");
            return false;
        }
        return true;
    }
    
    public static boolean checkSymbols(String userInput) {
        if(userInput.contains("++") // doubles
            || userInput.contains("--")
            || userInput.contains("**")
            || userInput.contains("//")
            || userInput.contains("^^")
            || userInput.contains("+-") // +_
            || userInput.contains("+*")
            || userInput.contains("+/")
            || userInput.contains("+^")
            || userInput.contains("--") // -_
            || userInput.contains("-*")
            || userInput.contains("-/")
            || userInput.contains("-^")
            || userInput.contains("*+") // *_
            || userInput.contains("*-")
            || userInput.contains("*/")
            || userInput.contains("*^")
            || userInput.contains("/+") // /_
            || userInput.contains("/-")
            || userInput.contains("/*")
            || userInput.contains("/^")
            || userInput.contains("^+") // ^_
            || userInput.contains("^-")
            || userInput.contains("^*")
            || userInput.contains("^/")
            || userInput.contains("-+") // _+
            || userInput.contains("*+")
            || userInput.contains("/+")
            || userInput.contains("^+")
            || userInput.contains("+-") // _-
            || userInput.contains("*-")
            || userInput.contains("/-")
            || userInput.contains("^-")
            || userInput.contains("+*") // _*
            || userInput.contains("-*")
            || userInput.contains("/*")
            || userInput.contains("^*")
            || userInput.contains("+/") // _/
            || userInput.contains("-/")
            || userInput.contains("*/")
            || userInput.contains("^/")
            || userInput.contains("+^") // _^
            || userInput.contains("-^")
            || userInput.contains("*^")
            || userInput.contains("/^")){
            System.out.println("Error: Invalid input");
            return false;
        }
        return true;
    }
    
    public static boolean checkParenthesis(String userInput) {
        // variables
        ArrayList<String> characters = new ArrayList<String>(Arrays.asList(userInput.split("")));
        
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
    public static double computeEquation(String userInput) throws ScriptException {
        double answer = 0.0;
        int startIndex = 0;
        int endIndex = 0;
        String currentEquation = "";
        boolean equationSolved = false;
        double temp = 0.0;
        
        userInput = addMultiplication(userInput);
        System.out.println("--after--" + userInput);
        //userInput = "(" + userInput + ")";
        
        while(!equationSolved) {
            if(userInput.contains("+")  ||
                userInput.contains("*") || userInput.contains("/")) {
                // continue solving
            } else {
                equationSolved = true;
            }
            
            ArrayList<String> characters = new ArrayList<String>(Arrays.asList(userInput.split("")));
            startIndex = findStart(characters); // starting index is updated
            //System.out.println("Start index: " + startIndex);
            endIndex = findEnd(characters, startIndex);
            //System.out.println("End index: " + endIndex);
            // parse the substring between the start index and end index
            currentEquation = userInput.substring(startIndex, endIndex + 1);
            // then solve
            temp = solveEquation(currentEquation);
            // update userInput
            //System.out.println("0 to startIndex: " + userInput.substring(0, startIndex));
            //System.out.println(" temp " + temp);
            //System.out.println("endIndex to length: " + userInput.substring(endIndex + 1, userInput.length()));
            userInput = userInput.substring(0, startIndex) + temp
                    + userInput.substring(endIndex + 1, userInput.length());
            System.out.println("\n" + userInput);
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
        return characters.size()-1; // 
    }
    
    public static double solveEquation(String equation) throws ScriptException {
        double returnValue = 0.0;
        // remove parenthesis at beginning and end
        //equation = equation.substring(1, equation.length()-1);
        equation.replace("(", "");
        equation.replace(")", "");
        System.out.println("equation: " + equation);
        
        
        
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        
        while(equation.contains("^")) {
            String base = solveBase(equation, equation.indexOf("^"));
            double tempBase = Double.parseDouble(base);
            String power = solvePower(equation, equation.indexOf("^"));
            double tempPower = Double.parseDouble(power);
            double result = Math.pow(tempBase, tempPower);
            String resultString = Double.toString(result);
            
            equation = equation.replace(base+"^"+power, resultString);
        }
        
        Object result = (engine.eval(equation));
        System.out.println(result);
        returnValue = Double.parseDouble(result.toString());
        System.out.println("return val: " + returnValue);
        return returnValue;
    }
    
    public static String addMultiplication(String userInput) {
        userInput = userInput.replace("0(", "0*(");
        userInput = userInput.replace("1(", "1*(");
        userInput = userInput.replace("2(", "2*(");
        userInput = userInput.replace("3(", "3*(");
        userInput = userInput.replace("4(", "4*(");
        userInput = userInput.replace("5(", "5*(");
        userInput = userInput.replace("6(", "6*(");
        userInput = userInput.replace("7(", "7*(");
        userInput = userInput.replace("8(", "8*(");
        userInput = userInput.replace("9(", "9*(");
        userInput = userInput.replace(")(", ")*(");
        return userInput;
    }
    
    public static String solveBase(String equation, int endIndex) {
        int maxIndex = 0;
        int currentIndex = 0;
        for(int i = 0; i < endIndex; i++) {
            if(equation.charAt(endIndex-i) == '+' || 
               equation.charAt(endIndex-i) == '-' ||
               equation.charAt(endIndex-i) == '/' ||
               equation.charAt(endIndex-i) == '*') {
                currentIndex = endIndex-i;
                if(currentIndex > maxIndex) {
                    maxIndex = currentIndex;
                }
            }
        }
        System.out.println(equation.substring(maxIndex + 1, endIndex));
        return equation.substring(maxIndex + 1, endIndex);
    }
    
    public static String solvePower(String equation, int startIndex) {
        int maxIndex = 0;
        int currentIndex = 0;
        for(int i = 0; i < equation.length() - startIndex; i++) {
            if(equation.charAt(startIndex + i) == '+' || 
               equation.charAt(startIndex + i) == '-' ||
               equation.charAt(startIndex + i) == '/' ||
               equation.charAt(startIndex + i) == '*') {
                currentIndex = startIndex + i;
                if(currentIndex > maxIndex) {
                    maxIndex = currentIndex;
                }
            }
            
        }
        if(maxIndex == 0) {
            maxIndex = equation.length();
        }
        System.out.println(equation.substring(startIndex + 1, maxIndex));
        return equation.substring(startIndex + 1, maxIndex);
    }
}
