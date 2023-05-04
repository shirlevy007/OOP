package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.lang.reflect.Array;
import java.util.*;

public class Shell {
    /**
     * actions options according to instructions:
     */
    private static final String EXIT_REQ = "exit";
    private static final String CHARS = "chars";
    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String ALL = "all";
    private static final String RES = "res";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String CONSOLE = "console";
    private static final String RENDER = "render";
    /**
     * one space string
     */
    private static final String SPACE = " ";
    /**
     * blank string
     */
    private static final String BLANK = "\\s+";
    /**
     * initial set to start from, digits 0-9.
     */
    private static final List<Character> INITIAL_SET =
            Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    /**
     * char space
     */
    private static final Character SPACE_CHAR = ' ';
    /**
     * hyphen string
     */
    private static final String HYPHEN = "-";
    /**
     * space - first addable char as written in forum
     */
    private static final int ASCII_FIRST_CHAR = 32;
    /**
     * ~ - last addable char as written in forum
     */
    private static final int ASCII_LAST_CHAR = 126;
    /**
     * empty string
     */
    private static final String EMPTY_STR = "";
    /**
     * up/down resolution
     */
    private static final int BASE = 2;
    /**
     * according to instructions
     */
    private static final int MIN_PIXELS_PER_CHAR = 2;
    /**
     * according to instructions
     */
    private static final int INITIAL_CHARS_IN_ROW = 64;
    /**
     * according to instructions
     */
    private static final String FONT_NAME = "Courier New";
    /**
     * according to instructions
     */
    private static final String OUTPUT_FILENAME = "out.html";
    /**
     * messages to user: actions and errors. All according to instructions:
     */
    private static final String START_RUN_MSG = ">>> ";
    private static final String WIDTH_CHANGED_MSG = "Width set to ";
    private static final String DIDNT_ADD_MSG = "Did not add due to incorrect format";
    private static final String DIDNT_REMOVE_MSG = "Did not remove due to incorrect format";
    private static final String DIDNT_CHANGE_RES_MSG = "Did not change due to exceeding boundaries";
    private static final String INCORRECT_COMMAND_MSG = "Did not executed due to incorrect command";
    /**
     * min length of valid remove command
     */
    private static final int REMOVE_8 = 8;
    /**
     * min length of valid add command
     */
    private static final int ADD_5 = 5;

    /**
     * image
     */
    private final Image img;
    /**
     * set of chars from user
     */
    private final Set<Character> charSet;
    /**
     * according to instructions
     */
    private final int minCharsInRow;
    /**
     * according to instructions
     */
    private final int maxCharsInRow;
    /**
     * according to instructions
     */
    private int charsInRow;
    /**
     * true when console command is requested (and on until run is done)
     */
    private boolean isConsole;
    /**
     * user input scanner
     */
    private final Scanner scanner = new Scanner(System.in);
    /**
     * char matcher
     */
    private final BrightnessImgCharMatcher charMather;
    private int startInt;
    private int endInt;

    /**
     * constructor
     * @param img image
     */
    public Shell(Image img) {
        this.img = img;
        this.charSet = new HashSet<>(INITIAL_SET);
        minCharsInRow = Math.max(1, img.getWidth() / img.getHeight());
        maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        this.charMather = new BrightnessImgCharMatcher(img, FONT_NAME);
        this.isConsole = false;
    }

    /**
     * chars command - shows all chars in format according to instructions
     */
    private void showChars() {
        for (var c : charSet) {
            System.out.print(c);
            System.out.print(SPACE);
        }
        System.out.println();
    }

    /**
     * adds single char
     * @param s string containing one char
     */
    private void addChar(String s) {
        Character[] charObjectArray =
                s.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        charSet.add(charObjectArray[0]);
    }

    /**
     * adds sequence of chars between the indexes given (only chars with valid ascii values)
     * @param startIndex min ascii val
     * @param endIndex max ascii val
     */
    private void addSequence(int startIndex, int endIndex) {
        // if trying to add a char out of ascii value 32-126: adds only all valid ones
        startIndex = Math.max(startIndex, ASCII_FIRST_CHAR);
        endIndex = Math.min(endIndex, ASCII_LAST_CHAR);
        for (int i = startIndex; i <= endIndex; i++) {
            charSet.add((char) i);
        }
    }

    /**
     * adds sequence of chars between the indexes given (only chars with valid ascii values)
     * @param seq as string
     */
    private void addSequence(String seq) {
        if (sequenceIndexes(seq)){
            addSequence(startInt, endInt);
        }
        else {
            System.out.println(DIDNT_ADD_MSG);
        }

    }

    /**
     * adds char\sequence of chars according to user input
     * @param userInput as string
     */
    void setAdd(String userInput) {
        String[] subUserInput = userInput.split(EMPTY_STR);
        if (userInput.length() == ADD_5 && subUserInput[(userInput.length()) - 1].equals(SPACE)) {
            charSet.add(SPACE_CHAR);
            return;
        }
        if (userInput.length() > ADD_5 && subUserInput[(userInput.length()) - 1].equals(SPACE)) {
            System.out.println(DIDNT_ADD_MSG);
            return;
        }
        subUserInput = userInput.split(SPACE);
        if (subUserInput[1].length() == 1) {
            addChar(subUserInput[1]);
            return;
        }
        if (subUserInput[1].equals(ALL)) {
            addSequence(ASCII_FIRST_CHAR, ASCII_LAST_CHAR);
            return;
        }
        if (subUserInput[1].length() == 3 && subUserInput[1].contains(HYPHEN)) {
            addSequence(subUserInput[1]);
            return;
        }
        System.out.println(DIDNT_ADD_MSG);
    }

    /**
     * removes single char
     * @param s char as string
     */
    private void removeChar(String s) {
        Character[] charObjectArray =
                s.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        charSet.remove(charObjectArray[0]);
    }

    /**
     * removes sequence of chars between the indexes given (only chars with valid ascii values)
     * @param startIndex min ascii val
     * @param endIndex max ascii val
     */
    private void removeSequence(int startIndex, int endIndex) {
        for (int i = startIndex; i <= endIndex; i++) {
            charSet.remove((char) i);
        }
    }

    /**
     * removes sequence of chars between the indexes given (only chars with valid ascii values)
     * @param seq as string
     */
    private void removeSequence(String seq) {
        if (sequenceIndexes(seq)){
            removeSequence(startInt, endInt);
        }
        else {
            System.out.println(DIDNT_REMOVE_MSG);
        }
    }

    /**
     * char\sequence of chars according to user input
     * @param userInput as string
     */
    void setRemove(String userInput) {
        String[] subUserInput = userInput.split(EMPTY_STR);
        if (userInput.length() == REMOVE_8 && subUserInput[(userInput.length()) - 1].equals(SPACE)) {
            charSet.remove(SPACE_CHAR);
            return;
        }
        if (userInput.length() > REMOVE_8 && subUserInput[(userInput.length()) - 1].equals(SPACE)) {
            System.out.println(DIDNT_REMOVE_MSG);
            return;
        }
        subUserInput = userInput.split(SPACE);
        if (subUserInput[1].length() == 1) {
            removeChar(subUserInput[1]);
            return;
        }
        if (subUserInput[1].equals(ALL)) {
            removeSequence(ASCII_FIRST_CHAR, ASCII_LAST_CHAR);
            return;
        }
        if (subUserInput[1].length() == 3 && subUserInput[1].contains(HYPHEN)) {
            removeSequence(subUserInput[1]);
            return;
        }
        System.out.println(DIDNT_REMOVE_MSG);
    }

    /**
     * handles sequence to be added\removed.
     * changes startInt&endInt values to ascii values of the chars requested
     * @param seq as string
     * @return true if seq is valid, else false (to print an error)
     */
    boolean sequenceIndexes(String seq){
        String[] chars = seq.split(EMPTY_STR);
        if (!(chars[1].equals(HYPHEN))){
            return false;
        }
        Character[] charObjectArray =
                seq.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        Character startIndex = charObjectArray[0];
        Character endIndex = charObjectArray[charObjectArray.length - 1];
        if (startIndex > endIndex) {
            Character t = startIndex;
            startIndex = endIndex;
            endIndex = t;
        }
        startInt = startIndex;
        endInt = endIndex;
        return true;
    }

    /**
     * changes resolution by command
     * @param s user command as string
     */
    private void setResolution(String s) {
        switch (s) {
            case UP:
                this.charMather.removeAllBrightness();
                resUp();
                break;
            case DOWN:
                this.charMather.removeAllBrightness();
                resDown();
                break;
            default:
                System.out.println(INCORRECT_COMMAND_MSG);
        }
    }

    /**
     * raise up resolution upon request
     */
    private void resUp() {
        int val = charsInRow * BASE;
        if (val > maxCharsInRow) {
            System.out.println(DIDNT_CHANGE_RES_MSG);
            return;
        }
        charsInRow = val;
        System.out.println(WIDTH_CHANGED_MSG + charsInRow);
    }

    /**
     * raise down resolution upon request
     */
    private void resDown() {
        int val = charsInRow / BASE;
        if (val < minCharsInRow) {
            System.out.println(DIDNT_CHANGE_RES_MSG);
            return;
        }
        charsInRow = val;
        System.out.println(WIDTH_CHANGED_MSG + charsInRow);
    }

    /**
     * create ascii art of image - create output file or print to console upon request.
     */
    private void renderImg() {
        if (charSet.isEmpty()){
            return;
        }
        Character[] charSetAsArray = new Character[charSet.toArray().length];
        int i=0;
        for (var c:charSet){
            charSetAsArray[i]=c;
            i++;
        }
        var chars = this.charMather.chooseChars(charsInRow, charSetAsArray);
        if(isConsole){
            new ConsoleAsciiOutput().output(chars);
        }
        else {
            HtmlAsciiOutput asciiOutput = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
            asciiOutput.output(chars);
        }
    }

    /**
     * gets a command from user and follows it if valid
     */
    public void run() {
        System.out.print(START_RUN_MSG);
        String userInput = scanner.nextLine();
        while (!(userInput.equals(EXIT_REQ))) {
            if (userInput.isBlank()){
                System.out.println(INCORRECT_COMMAND_MSG);
                System.out.print(START_RUN_MSG);
                userInput = scanner.nextLine();
                continue;
            }
            String[] subUserInput = userInput.split(BLANK);
            switch (subUserInput[0]) {
                case CHARS:
                    showChars();
                    break;
                case ADD:
                    setAdd(userInput);
                    break;
                case REMOVE:
                    setRemove(userInput);
                    break;
                case RES:
                    if (subUserInput.length == 1) {
                        System.out.println(INCORRECT_COMMAND_MSG);
                        break;
                    }
                    setResolution(subUserInput[1]);
                    break;
                case CONSOLE:
                    if (subUserInput.length != 1) {
                        System.out.println(INCORRECT_COMMAND_MSG);
                        break;
                    }
                    isConsole=true;
                    break;
                case RENDER:
                    if (subUserInput.length != 1) {
                        System.out.println(INCORRECT_COMMAND_MSG);
                        break;
                    }
                    renderImg();
                    break;
                default:
                    System.out.println(INCORRECT_COMMAND_MSG);
            }
            System.out.print(START_RUN_MSG);
            userInput = scanner.nextLine();
        }
    }
}

