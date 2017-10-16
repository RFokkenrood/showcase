package howl.lang.parser;

import howl.lang.basetypes.*;
import howl.lang.parser.statements.Argument;

import java.math.BigDecimal;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

class ArgumentFactory {

    static Argument getArgument(String in) {
        String argumentString = in.trim();
        if (argumentString.startsWith("\"") || argumentString.startsWith("'") || argumentString.startsWith("@raw")) {
            return new HwlString(argumentString);
        } else if (argumentString.contains("(")) {
            String partInBrackets = argumentString.substring(argumentString.indexOf("(") + 1, argumentString.indexOf(")"));
            return getArgument(argumentString.replace("(" + partInBrackets + ")", getArgument(partInBrackets).hwlToString().value()));
        } else if (argumentString.contains("==")) {
            return getArgument(argumentString.substring(0, argumentString.indexOf("=="))).hwlEquals(getArgument(argumentString.substring(argumentString.indexOf("==") + 2)));
        } else if (argumentString.matches("(?:true|false|&|\\s|\\||!)+")) {
            return getHwlBoolean(argumentString);
        } else if (argumentString.matches("[0-9*/%+\\-()\\s^]+")) {
            return getHwlInt(argumentString);
        } else if (argumentString.matches("[0-9*/%+\\-()\\s^.]+")) {
            return getHwlDec(argumentString);
        } else {
            return new HwlUndefined();
        }
    }

    private static HwlBoolean getHwlBoolean(String input) {
        String argumentString = input.trim();
        if (argumentString.equals("true") || argumentString.equals("false")) {
            return new HwlBoolean(parseBoolean(argumentString.trim()));
        } else if (argumentString.contains("&")) {
            return getHwlBoolean(argumentString.substring(0, argumentString.indexOf("&"))).and(getHwlBoolean(argumentString.substring(argumentString.indexOf("&") + 1)));
        } else if (argumentString.contains("|")) {
            return getHwlBoolean(argumentString.substring(0, argumentString.indexOf("|"))).or(getHwlBoolean(argumentString.substring(argumentString.indexOf("|") + 1)));
        } else if (argumentString.contains("!")) {
            return getHwlBoolean(argumentString.substring(argumentString.indexOf("!") + 1)).not();
        } else {
            throw new OperationNotImplemented();
        }
    }

    static HwlInteger getHwlInt(String input) {
        return new MathLiteralParser<>("-?[0-9]+", s -> new HwlInteger(parseInt(s))).getFor(input);

    }

    private static HwlDecimal getHwlDec(String input) {
        return new MathLiteralParser<>("-?[0-9.]+", s -> new HwlDecimal(new BigDecimal(s))).getFor(input);
    }

    private static class OperationNotImplemented extends RuntimeException {
    }
}
