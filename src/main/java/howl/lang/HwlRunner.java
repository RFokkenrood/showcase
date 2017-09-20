package howl.lang;

import howl.lang.parser.FileReader;
import howl.lang.parser.LineParser;
import howl.lang.parser.StatementExecuter;

public class HwlRunner {
    public static void main(String... args){
        FileReader.getLines(args).map(LineParser::parse).forEach(StatementExecuter::execute);
    }
}
