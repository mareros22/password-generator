// Rose Maresh
// University of Washington
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;
import java.awt.*;


/*
PasswordGenerator.java
- Generates random password based on user input-specified length
- Passwords include randomly generated upper and lower case letters,
    random number and random special character
- Optional functionality of copying password to user's clipboard
- Optional functionality of generating additional passwords
 */
public class Main {
    public static void main(String[] args) {
        boolean cont = true;
        int len;
        String pw;
        Scanner console = new Scanner(System.in);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        while(cont) {
            len = getLenInput(console);
            pw = genPassword(len);
            System.out.println("Your generated password is: " + pw);
            optCopy(console, pw, clipboard);
            cont = askToContinue(console);
        }
        System.out.println("Thank you for using the password generator!");
    }


    /*getLenInput
    - Behavior:
        - Asks the user to input the length of desired password
        - Verifies that the inputted length is valid (at least 5)
        - If necessary, asks the user for a new length
    - Parameters:
        - console: the System.in Scanner to receive input through
    - Return:
        - the inputted and verified length
     */
    private static int getLenInput(Scanner console){
        int len;
        System.out.println("How long would you like your password to be? ");
        len = console.nextInt();
        while(!checkLenInput(len)){
            System.out.println("Must have password length of at least 5");
            System.out.println("How long would you like your password to be? ");
            len = console.nextInt();
        }
        return len;
    }

    /*checkLenInput
    - Behavior:
        - verifies that a given integer is >=5 to check password length
    - Parameters:
        - num: the length to check
    - Return:
        - num >= 5
    */
    private static boolean checkLenInput(int num){
        return num >= 5;
    }

    /*genPassword
    - Behavior:
        - Generates a random password of a specified length
    - Parameters:
        - len: the length of the password to generate
    - Return:
        - the generated password
    */
    private static String genPassword(int len){
        char[] characters = new char[len];
        ArrayList<Integer> indices = fillIndices(len);
        int spCharIndex = selectRandomTerm(indices);
        int numIndex = selectRandomTerm(indices);
        int capIndex = selectRandomTerm(indices);
        characters[spCharIndex] = genRandomSpCh();
        characters[numIndex] = genRandomDigit();
        characters[capIndex] = genRandomChar('A');
        for(int i : indices){
            characters[i] = genRandomChar('a');
        }
        return charArrToString(characters);
    }

    /*fillIndices
    - Behavior:
        - Fills an ArrayList with the available indices (for generation use)
    - Parameters:
        - len: the length of the password
    - Return:
        - the filled ArrayList
     */
    private static ArrayList<Integer> fillIndices(int len) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            indices.add(i);
        }
        return indices;
    }

    /*selectRandomTerm
    - Behavior:
        - Selects a random term of an ArrayList and removes it
    - Parameters:
        - list: the ArrayList to select and remove from
    - Return:
        - the removed value
     */
    private static int selectRandomTerm(ArrayList<Integer> list){
        Random rand = new Random();
        int termIndex = rand.nextInt(list.size());

        return(list.remove(termIndex));
    }

    /*genRandomChar
    - Behavior:
        - Generates a random character within 26 of the indicated start character
            (so that it can be reused between lowercase and uppercase chars)
    - Parameters:
        - start: the character to begin selection from
    - Return:
        - the generated character
     */
    private static char genRandomChar(char start){
        Random rand = new Random();
        return (char)(rand.nextInt(26) + start);
    }

    /*genRandomDigit
    - Behavior:
        - Generates a random char-cast digit
    - Parameters:
        - NONE
    - Return:
        - char cast digit generated
     */
    private static char genRandomDigit(){
        Random rand = new Random();
        return Character.forDigit(rand.nextInt(10), 10);
    }


    /*genRandomSpCh
    - Behavior:
        - Generates a random special character
    - Parameters:
        - NONE
    - Return:
        - the randomly selected sp. character
     */
    private static char genRandomSpCh(){
        char[] specialLibrary = {'!', '$', '#', '%', '&'};
        Random rand = new Random();
        return specialLibrary[rand.nextInt(specialLibrary.length)];
    }

    /*charArrToString
    - Behavior:
        - Converts an array of chars to a String
    - Parameters:
        - chars: the array to convert
    - Return:
        - the converted String
     */
    private static String charArrToString(char[] chars){
        StringBuilder ret = new StringBuilder();
        for(char c : chars){
            ret.append(c);
        }
        return ret.toString();
    }

    /*optCopy
    - Behavior:
        - Asks the user if they would like to copy the generated password to clipboard
        - Verifies input and asks for re-input if necessary
        - If desired, copies password to system clipboard
    - Parameters:
        - console: the System.in Scanner to receive input through
        - pw: the password to (optionally) copy
        - cb: the clipboard
    - Return:
        - NONE
     */
    private static void optCopy(Scanner console, String pw, Clipboard cb){
        String input;
        while(true) {
            System.out.println("Would you like to copy to clipboard? ");
            input = console.next();
            if(checkIfYes(input)){
                copy(cb, pw);
                System.out.println("Copied!");
                return;
            }else if(checkIfNo(input)){
                System.out.println("Not copied.");
                return;
            }else{
                System.out.println("Sorry, I couldn't understand that answer.");
            }

        }
    }

    /*checkIfYes
    - Behavior:
        - Checks if the inputted string is Yes or similar
    - Parameters:
        - str: the string to check
    - Return:
        - true if the input is Yes or similar, false otherwise
     */
    private static boolean checkIfYes(String str){
        String[] synonyms = {"Yes", "Y", "Yeah", "Please"};
        for(String s : synonyms){
            if(str.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

    /*checkIfNo
    - Behavior:
        - Checks if the inputted string is No or similar
    - Parameters:
        - str: the string to check
    - Return:
        - true if the input is No or similar, false otherwise
     */
    private static boolean checkIfNo(String str){
        String[] synonyms = {"No", "N", "Nope"};
        for(String s : synonyms){
            if(str.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }


    /*copy
    - Behavior:
        - copies the specified string (password) to system clipboard
    - Parameters:
        - cb: the system clipboard
        - pw: the password to copy
    - Return:
        - NONE
     */
    private static void copy(Clipboard cb, String pw){
        StringSelection selection = new StringSelection(pw);
        cb.setContents(selection, selection);
    }


    /*askToContinue
    - Behavior:
        - Checks if the user would like to generate another password
        - If input cannot be verified, asks again
    - Parameters:
        - console: the Scanner to take input from
    - Return:
        - true if the user would like to generate another password, false otherwise
     */
    private static boolean askToContinue(Scanner console){
        String input;
        while(true){
            System.out.println("Would you like to generate another password?");
            input = console.next();
            if(checkIfYes(input)){
                return true;
            }
            if(checkIfNo(input)){
                return false;
            }
            System.out.println("I didn't understand that input");
        }
    }
}

