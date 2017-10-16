package howl.lang.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;

public class FileReader {

    public static Stream<String> getLines(String[] args) {
        return Arrays.stream(args).flatMap(FileReader::FilePathToLines);
    }

    private static Stream<String> FilePathToLines(String path) {
        try {
            return Files.lines(new File(path).toPath())
                    .filter(s -> !s.isEmpty())
                    .map(new Function<String, String>() {
                        String accumulate = null;
                        boolean ongoingString = false;

                        @Override
                        public String apply(String s) {
                            if (accumulate == null) {
                                accumulate = s;
                            } else {
                                accumulate += lineSeparator() + s;
                            }
                            checkOngongString(s);
                            if (s.endsWith(");")&&!ongoingString) {
                                String response = accumulate;
                                accumulate = null;
                                return response;
                            } else {
                                return null;
                            }
                        }

                        private void checkOngongString(String s) {
                            if(s.matches(".+\\(@raw@.+") && ! s.matches(".+[^\\\\]?@endraw@")){
                                ongoingString = true;
                            }else if(s.matches(".+[^\\\\]?@endraw@")){
                                ongoingString = false;
                            }
                        }
                    })
                    .filter(Objects::nonNull);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file. Does it exist?", e);
        }
    }
}
