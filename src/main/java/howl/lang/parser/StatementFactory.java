package howl.lang.parser;

import howl.lang.parser.statements.Argument;
import howl.lang.parser.statements.PrintStatement;

class StatementFactory {
    static Statement getStatement(String verb, Argument... arguments) {
        switch (verb) {
            case "print":
                return new PrintStatement(arguments[0]);
            default:
                throw new NoSuchStatement();
        }
    }

    private static class NoSuchStatement extends RuntimeException {
    }
}
