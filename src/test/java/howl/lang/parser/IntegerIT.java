package howl.lang.parser;

import howl.lang.HwlRunner;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.rules.ExpectedException.*;

public class IntegerIT {
    private ByteArrayOutputStream captureOut;
    private PrintStream nativeOut;

    @Rule
    public ExpectedException exception = none();

    @Before
    public void captureSystemOut() {
        nativeOut = System.out;
        captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
    }

    @After
    public void restoreSystemOut() {
        System.setOut(nativeOut);
    }

    @Test
    public void checkInt() throws IOException {
        runFile("int-test-file.hwl");
        assertPrinted("5");
        assertPrinted("8");
        assertPrinted("10");
        assertPrinted("39");
        assertPrinted("4");
        assertPrinted("0");
        assertPrinted("25");
        assertPrinted("36");
        assertPrinted("7");
        assertPrinted("16");
        assertPrinted("-11");
        assertPrinted("12");
        assertPrinted("14");
        assertPrinted("125");
        assertPrinted("262144");
        assertPrinted("true");
        assertPrinted("false");
        assertPrinted("24");
        assertPrinted("49");
        assertPrinted("-1");
        assertPrinted("98");
        assertPrinted("100");
    }

    @Test
    public void devBy0() throws IOException {
        exception.expect(ArithmeticException.class);
        exception.expectMessage("/ by zero");
        runFile("devBy0.hwl");
    }

    private void runFile(String filename) throws IOException {
        File file = new File(UndefinedIT.class.getClassLoader().getResource("howl/lang/parser/integer_test/" + filename).getFile());
        HwlRunner.main(file.getCanonicalPath());
    }

    private Matcher<String> hasPrinted(String substring) {
        return containsString(substring + System.lineSeparator());
    }

    private void assertPrinted(String substring) {
        assertThat(captureOut.toString(), hasPrinted(substring));
    }
}
