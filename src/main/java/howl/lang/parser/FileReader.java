package howl.lang.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileReader {

    public static Stream<String> getLines(String[] args) {
        return Arrays.stream(args).flatMap(FileReader::FilePathToLines);
    }

    private static Stream<String> FilePathToLines(String path) {
        try {
            return Files.lines(new File(path).toPath()).filter(s -> !s.isEmpty());
        } catch (IOException e) {
            throw new RuntimeException("Could not read file. Does it exist?", e);
        }
    }
}
