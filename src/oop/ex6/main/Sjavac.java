package oop.ex6.main;

import oop.ex6.componants.LineValidator;
import oop.ex6.componants.InvalidLineEndException;
import oop.ex6.componants.methods.GlobalScope;
import oop.ex6.componants.variables.InvalidVarDeclarationException;
import oop.ex6.componants.variables.InvalidVarTypeException;
import oop.ex6.componants.variables.ValueMismatchException;
import oop.ex6.componants.variables.VarNameInitializedException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sjavac {
    private static final int VALID_ARG_NUM = 1;
    private static final String VALID_CODE = "0";
    private static final String INVALID_CODE = "1";
    private static final String IO_ERROR_CODE = "2";
    private static final String ERROR_CODE = "ERROR: ";
    private static final String LINE_CODE = " (line %d)";

    private static final String IO_ERR_MSG = "File not found";
    private static final String ARG_ERR_MSG = "Wrong number of arguments entered";
    private static final String COMMENT_REGEX = "^[/]{2}.*";
    private static final String SPACE_REGEX = "\\s*";

    public static void main(String[] args) {
        if (args.length != VALID_ARG_NUM) {
            System.out.println(IO_ERROR_CODE);
            System.err.println(ERROR_CODE + ARG_ERR_MSG);
            return;
        }

        GlobalScope globalScope = new GlobalScope();
        if (!firstPass(globalScope, args[0])) {
            return;
        }

        System.out.println(VALID_CODE);

    }

    private static boolean isEmptyLine(String line) {
        Pattern p = Pattern.compile(SPACE_REGEX);
        Matcher m = p.matcher(line);
        return m.matches();
    }

    private static boolean isComment(String line) {
        Pattern p = Pattern.compile(COMMENT_REGEX);
        Matcher m = p.matcher(line);
        return m.matches();
    }

    private static void printError(Exception e, int lineIndex) {
        System.out.println(INVALID_CODE);
        System.err.printf(e + LINE_CODE, lineIndex);
        System.out.println();
    }

    private static boolean firstPass(GlobalScope globalScope, String fileName) {
        String line;
        int lineIndex = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                lineIndex++;
                if (isComment(line) || isEmptyLine(line)) {
                    continue;
                }
                try {
                    new LineValidator(globalScope).validate(line);
                } catch (InvalidLineEndException | InvalidVarTypeException | VarNameInitializedException |
                         ValueMismatchException |
                         InvalidVarDeclarationException e) {
                    printError(e, lineIndex);
                    return false;
                }
            }

        } catch (IOException e) {
            System.out.println(IO_ERROR_CODE);
            System.err.println(IO_ERR_MSG);
            return false;
        }
        return true;
    }
}
