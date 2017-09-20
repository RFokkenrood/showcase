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

public class IntegerIT {
    private static ByteArrayOutputStream captureOut;
    private static PrintStream nativeOut;

    @BeforeClass
    public static void captureSystemOut() throws IOException {
        nativeOut = System.out;
        captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
        File file = new File(ParserIT.class.getClassLoader().getResource("howl/lang/parser/int-test-file.hwl").getFile());
        HwlRunner.main(file.getCanonicalPath());
    }

    @Test
    public void checkInt(){
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
