package howl.lang.parser;

import howl.lang.HwlRunner;
import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ParserIT {
    private static ByteArrayOutputStream captureOut;
    private static PrintStream nativeOut;

    @BeforeClass
    public static void captureSystemOut() throws IOException {
        nativeOut = System.out;
        captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
        File file = new File(ParserIT.class.getClassLoader().getResource("howl/lang/parser/print-test-file.hwl").getFile());
        HwlRunner.main(file.getCanonicalPath());
    }

    @Test
    public void checkUndefined(){
        assertPrinted("undefined");
    }

    @Test
    public void checkStrings() {
        assertPrinted("passed");
        assertPrinted("I contain 'single quotes' and even escaped \"double quotes\" ");
        assertPrinted("'single quotes' and \"double quotes\" are interchangable, only the quote type for the strings needs to be quoted");
        assertPrinted("gonna mess with how (i know) the parser works");
    }

    @Test
    public void checkDecimal(){
        assertPrinted("4.0");
    }

    private Matcher<String> hasPrinted(String substring) {
        return containsString(substring + System.lineSeparator());
    }

    private void assertPrinted(String substring) {
        assertThat(captureOut.toString(), hasPrinted(substring));
    }

    @AfterClass
    public static void restoreSystemOut(){
        System.setOut(nativeOut);
    }
}
