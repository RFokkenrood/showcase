package howl.lang.parser;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import howl.lang.HwlRunner;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

@RunWith(HierarchicalContextRunner.class)
public class BooleanIT {
    private ByteArrayOutputStream captureOut;
    private PrintStream nativeOut;

    @Before
    public void captureSystemOut() {
        nativeOut = System.out;
        captureOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captureOut));
    }

    @Test
    public void checkTrue() throws IOException {
        executeFile("true.hwl");
        assertPrinted("true");
    }

    @Test
    public void checkFalse() throws IOException {
        executeFile("false.hwl");
        assertPrinted("false");
    }

    @Test
    public void checkBrackets() throws IOException {
        executeFile("brackets.hwl");
        assertPrinted("false");
    }

    public class checkNot{
        @Test
        public void checkNotTrue() throws IOException {
            executeFile("not/notTrue.hwl");
            assertPrinted("false");
        }

        @Test
        public void checkNotFalse() throws IOException {
            executeFile("not/notFalse.hwl");
            assertPrinted("true");
        }
    }

    public class checkAnd {
        @Test
        public void checkTrueAndTrue() throws IOException {
            executeFile("and/trueANDtrue.hwl");
            assertPrinted("true");
        }

        @Test
        public void checkFalseAndTrue() throws IOException {
            executeFile("and/falseANDtrue.hwl");
            assertPrinted("false");
        }

        @Test
        public void checkFalseAndFalse() throws IOException {
            executeFile("and/falseANDfalse.hwl");
            assertPrinted("false");
        }

        @Test
        public void checkTrueAndFalse() throws IOException {
            executeFile("and/trueANDfalse.hwl");
            assertPrinted("false");
        }
    }

    public class checkOr{
        @Test
        public void checkTrueAndTrue() throws IOException {
            executeFile("or/trueORtrue.hwl");
            assertPrinted("true");
        }

        @Test
        public void checkFalseAndTrue() throws IOException {
            executeFile("or/falseORtrue.hwl");
            assertPrinted("true");
        }

        @Test
        public void checkFalseAndFalse() throws IOException {
            executeFile("or/falseORfalse.hwl");
            assertPrinted("false");
        }

        @Test
        public void checkTrueAndFalse() throws IOException {
            executeFile("or/trueORfalse.hwl");
            assertPrinted("true");
        }
    }

    public class checkEquals{
        @Test
        public void checkTrueEqualsTrue() throws IOException {
            executeFile("equals/trueEQUALStrue.hwl");
            assertPrinted("true");
        }

        @Test
        public void checkFalseEqualsTrue() throws IOException {
            executeFile("equals/falseEQUALStrue.hwl");
            assertPrinted("false");
        }

        @Test
        public void checkFalseEqualsFalse() throws IOException {
            executeFile("equals/falseEQUALSfalse.hwl");
            assertPrinted("true");
        }

        @Test
        public void checkTrueEqualsFalse() throws IOException {
            executeFile("equals/trueEQUALSfalse.hwl");
            assertPrinted("false");
        }
    }

    @After
    public void restoreSystemOut(){
        System.setOut(nativeOut);
    }

    private void executeFile(String fileName) throws IOException {
        File file;
        try {
            file = new File(UndefinedIT.class.getClassLoader().getResource("howl/lang/parser/boolean_test/" + fileName).getFile());
        } catch (NullPointerException e){
            throw new FileNotFoundException("howl/lang/parser/boolean_test/" + fileName);
        }
        HwlRunner.main(file.getCanonicalPath());
    }

    private Matcher<String> hasPrinted(String substring) {
        return containsString(substring + System.lineSeparator());
    }

    private void assertPrinted(String substring) {
        assertThat(captureOut.toString(), hasPrinted(substring));
    }
}
