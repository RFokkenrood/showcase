package howl.lang.parser.statements;

import howl.lang.basetypes.HwlString;
import howl.lang.parser.Statement;

public class PrintStatement implements Statement{
    private String messageToPrint;
    public PrintStatement(Argument argument){
        if(argument.getClass().equals(HwlString.class)){
            messageToPrint = (String) argument.value();
        } else {
            messageToPrint = argument.hwlToString().value();
        }
    }

    public void execute() {
        System.out.println(messageToPrint);
    }
}
