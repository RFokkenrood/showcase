package howl.lang.parser;

import howl.lang.HwlRunner;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class StringIT {
    private ByteArrayOutputStream captureOut;
    private PrintStream nativeOut;

    @Before
    public void captureSystemOut() {
        nativeOut = System.out;
        captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
    }

    @Test
    public void checkStrings() throws IOException {
        executeFile("string-base-test-file.hwl");

        assertPrinted("I contain 'single quotes' and even escaped \"double quotes\" ");
        assertPrinted("'single quotes' and \"double quotes\" are interchangable, only the quote type for the strings needs to be quoted");
        assertPrinted("gonna mess with how (i know) the parser works");
    }

    @Test
    public void printTextContainingCode() throws IOException {
        executeFile("text-containing-code.hwl");
        assertEquals("someMethod(\"string argument\").othermethod();\n", captureOut.toString());
    }

    @After
    public void restoreSystemOut(){
        System.setOut(nativeOut);
    }

    private Matcher<String> hasPrinted(String substring) {
        return containsString(substring + System.lineSeparator());
    }

    private void assertPrinted(String substring) {
        assertThat(captureOut.toString(), hasPrinted(substring));
    }

    private void executeFile(String fileName) throws IOException {
        File file;
        try {
            file = new File(UndefinedIT.class.getClassLoader().getResource("howl/lang/parser/string_test/" + fileName).getFile());
        } catch (NullPointerException e){
            throw new FileNotFoundException("howl/lang/parser/string_test/" + fileName);
        }
        HwlRunner.main(file.getCanonicalPath());
    }

}
