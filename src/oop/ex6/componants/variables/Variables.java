package oop.ex6.componants.variables;

import oop.ex6.componants.VarType;
import oop.ex6.componants.methods.Scope;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variables {
    private static final String NAME_REGEX = "([a-zA-Z]+[a-zA-Z0-9_]*|_+[a-zA-Z0-9_]+)";
    private static final String FINAL_REGEX = "\\s*final\\s+";
    private static final Pattern FINAL_PATTERN = Pattern.compile(FINAL_REGEX);
    private static final Pattern TYPE_PATTERN = Pattern.compile("\\s*([a-zA-Z]+)\\s+(.*)");
    private static final Pattern ASSIGMENT_PATTERN = Pattern.compile(
            "\\s*" + NAME_REGEX + "\\s*=\\s*(\\S*)\\s*");
    private static boolean isFinal;
    private Scope scope;


    public Variables(String line, Scope scope)
            throws InvalidVarTypeException, InvalidVarDeclarationException, ValueMismatchException, VarNameInitializedException, IllegalFinalVarAssigmentException {
        // Check final
        isFinal = isFinal(line);
        this.scope = scope;
        if (isFinal) {
            line = stripFinal(line);
        }

        // Get type
        if (isInitialization(line)) {
            Matcher m = TYPE_PATTERN.matcher(line);
            if (!m.lookingAt()) {
                throw new InvalidVarTypeException();
            }
            VarType type = VarTypeFactory.getType(m.group(1));
            String restOfLine = m.group(2);

            // Get different vars
            String[] vars = restOfLine.split(",");
            if (vars.length < 1) { // No variables
                throw new InvalidVarDeclarationException();
            }
            for (String var : vars) {
                Variable variable = new Variable(type, var, scope, isFinal);
                if (!scope.addVarToScopeMap(variable)) {
                    throw new VarNameInitializedException();
                }
            }
        } else {
            String[] vars = line.split(",");
            if (vars.length < 1) { // No variables
                throw new InvalidVarDeclarationException();
            }
            for (String varStr : vars) {
                Matcher m = ASSIGMENT_PATTERN.matcher(varStr);
                if (!m.matches()) {
                    throw new InvalidVarDeclarationException();
                }
                String name = m.group(1);
                String value = m.group(2);
                Variable var = scope.getVarFromMap(name);
                if (var == null) {
                    throw new InvalidVarDeclarationException();
                }
                if (var.isFinal()) {
                    throw new IllegalFinalVarAssigmentException();
                }
                var.checkAssigment(value);
            }
        }
    }

    public static boolean isFinal(String line) {
        Matcher m = FINAL_PATTERN.matcher(line);
        return m.lookingAt();
    }

    public static String stripFinal(String line) {
        return line.replaceAll(FINAL_REGEX, "");
    }

    private boolean isInitialization(String line) {
        Pattern p = Pattern.compile("\\s*" + VarTypeFactory.getTypeRegex());
        Matcher m = p.matcher(line);
        return m.lookingAt();
    }
}
