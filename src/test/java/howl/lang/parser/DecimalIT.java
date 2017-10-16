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

public class DecimalIT {
    private static ByteArrayOutputStream captureOut;
    private static PrintStream nativeOut;

    @BeforeClass
    public static void captureSystemOut() throws IOException {
        nativeOut = System.out;
        captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
        File file = new File(UndefinedIT.class.getClassLoader().getResource("howl/lang/parser/dec-test-file.hwl").getFile());
        HwlRunner.main(file.getCanonicalPath());
    }

    @Test
    public void checkDecimal(){
        assertPrinted("3.5");
        assertPrinted("-4.2");
        assertPrinted("3.141527");
        assertPrinted("9.00");
        assertPrinted("12.5");
        assertPrinted("15.0");
        assertPrinted("2.55");
        assertPrinted("0.2");
        assertPrinted("1.21");
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
