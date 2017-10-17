package howl.lang.parser;

import howl.lang.HwlRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

public class LineParserIT {
    private ByteArrayOutputStream captureOut;
    private PrintStream nativeOut;
    private String ln = lineSeparator();

    @Before
    public void captureSystemOut() {
        nativeOut = System.out;
        captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
    }

    @Test
    public void checkOneStatement() throws IOException {
        executeFile("one-statement.hwl");

        assertEquals("a" + ln, captureOut.toString());
    }

    @Test
    public void twoStatementsOnTwoLines() throws IOException {
        executeFile("two-statements-two-lines.hwl");

        assertEquals("a" + ln + "b" + ln, captureOut.toString());
    }

    @Test
    public void twoStatementsOnOneLine() throws IOException {
        executeFile("two-statements-one-line.hwl");

        assertEquals("a" + ln + "b" + ln, captureOut.toString());
    }

    @After
    public void restoreSystemOut(){
        System.setOut(nativeOut);
    }

    private void executeFile(String fileName) throws IOException {
        File file;
        try {
            file = new File(UndefinedIT.class.getClassLoader().getResource("howl/lang/parser/line_parser/" + fileName).getFile());
        } catch (NullPointerException e){
            throw new FileNotFoundException("howl/lang/parser/line_parser/" + fileName);
        }
        HwlRunner.main(file.getCanonicalPath());
    }
}
