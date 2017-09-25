package howl.lang.parser;

import java.util.function.Function;

import static howl.lang.parser.ArgumentFactory.getHwlInt;

class MathLiteralParser<T extends HwlNumber<T>> {
    private Function<String, T> numberCreator;
    private String numberTypeRegex;
    MathLiteralParser(String numberTypeRegex, Function<String, T> numberCreator){
        this.numberCreator = numberCreator;
        this.numberTypeRegex = numberTypeRegex;
    }

    T getFor(String input){
        String argumentString = input.trim();
        argumentString = argumentString.replaceAll("-\\s*?-", "+");
        if (argumentString.matches(numberTypeRegex)) {
            return numberCreator.apply(argumentString);
        } else if (argumentString.contains("+")) {
            return getFor(argumentString.substring(0, argumentString.lastIndexOf("+"))).plus(getFor(argumentString.substring(argumentString.lastIndexOf("+") + 1)));
        } else if (argumentString.contains("-")) {
            return getFor(argumentString.substring(0, argumentString.lastIndexOf("-"))).minus(getFor(argumentString.substring(argumentString.lastIndexOf("-") + 1)));
        } else if (argumentString.contains("*")) {
            return getFor(argumentString.substring(0, argumentString.lastIndexOf("*"))).times(getFor(argumentString.substring(argumentString.lastIndexOf("*") + 1)));
        } else if (argumentString.contains("/")) {
            return getFor(argumentString.substring(0, argumentString.lastIndexOf("/"))).over(getFor(argumentString.substring(argumentString.lastIndexOf("/") + 1)));
        } else if (argumentString.contains("%")) {
            return getFor(argumentString.substring(0, argumentString.lastIndexOf("%"))).modulo(getFor(argumentString.substring(argumentString.lastIndexOf("%") + 1)));
        } else if (argumentString.contains("^")) {
            return getFor(argumentString.substring(0, argumentString.indexOf("^"))).power(getHwlInt(argumentString.substring(argumentString.indexOf("^") + 1)));
        } else {
            throw new OperationNotImplemented();
        }
    }
}

class OperationNotImplemented extends RuntimeException {}
