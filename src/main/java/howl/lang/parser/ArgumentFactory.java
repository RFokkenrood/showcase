package howl.lang.parser;

import howl.lang.basetypes.*;
import howl.lang.parser.statements.Argument;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

class ArgumentFactory {

    static Argument getArgument(String in) {
        String argumentString = in.trim();
        if (argumentString.startsWith("\"") || argumentString.startsWith("'")) {
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
            return new HwlDecimal(argumentString);
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

    private static HwlInteger getHwlInt(String input) {
        String argumentString = input.trim();
        if (argumentString.matches("[0-9]+")) {
            return new HwlInteger(parseInt(argumentString));
        } else if (argumentString.contains("+")) {
            return getHwlInt(argumentString.substring(0, argumentString.indexOf("+"))).plus(getHwlInt(argumentString.substring(argumentString.indexOf("+") + 1)));
        } else if (argumentString.contains("-")) {
            return getHwlInt(argumentString.substring(0, argumentString.lastIndexOf("-"))).minus(getHwlInt(argumentString.substring(argumentString.lastIndexOf("-") + 1)));
        } else if (argumentString.contains("*")) {
            return getHwlInt(argumentString.substring(0, argumentString.indexOf("*"))).times(getHwlInt(argumentString.substring(argumentString.indexOf("*") + 1)));
        } else if (argumentString.contains("/")) {
            return getHwlInt(argumentString.substring(0, argumentString.lastIndexOf("/"))).over(getHwlInt(argumentString.substring(argumentString.lastIndexOf("/") + 1)));
        } else if (argumentString.contains("%")) {
            return getHwlInt(argumentString.substring(0, argumentString.indexOf("%"))).modulo(getHwlInt(argumentString.substring(argumentString.indexOf("%") + 1)));
        } else if (argumentString.contains("^")) {
            return getHwlInt(argumentString.substring(0, argumentString.indexOf("^"))).power(getHwlInt(argumentString.substring(argumentString.indexOf("^") + 1)));
        } else {
            throw new OperationNotImplemented();
        }
    }

    private static class OperationNotImplemented extends RuntimeException {
    }
}
