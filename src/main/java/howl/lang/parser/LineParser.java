package howl.lang.parser;

import howl.lang.parser.statements.Argument;

public class LineParser {
    public static Statement parse(String statementString) {
        return StatementFactory.getStatement(getVerb(statementString), getArgument(statementString));
    }

    private static Argument getArgument(String statementString) {
        return ArgumentFactory.getArgument(statementString.substring(statementString.indexOf("(") +1, statementString.lastIndexOf(");")));
    }

    private static String getVerb(String statementString) {
        return statementString.substring(0, statementString.indexOf('('));
    }
}
