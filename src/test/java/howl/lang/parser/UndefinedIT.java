package howl.lang.parser;

import howl.lang.HwlRunner;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class UndefinedIT {
    private ByteArrayOutputStream captureOut;
    private PrintStream nativeOut;

    @Before
    public void captureSystemOut() throws IOException {
        nativeOut = System.out;
        captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
        File file = new File(UndefinedIT.class.getClassLoader().getResource("howl/lang/parser/undefined-test-file.hwl").getFile());
        HwlRunner.main(file.getCanonicalPath());
    }

    @Test
    public void checkUndefined(){
        assertPrinted("undefined");
    }

    private Matcher<String> hasPrinted(String substring) {
        return containsString(substring + System.lineSeparator());
    }

    private void assertPrinted(String substring) {
        assertThat(captureOut.toString(), hasPrinted(substring));
    }

    @After
    public void restoreSystemOut(){
        System.setOut(nativeOut);
    }
}
