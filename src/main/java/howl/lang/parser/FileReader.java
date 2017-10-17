package howl.lang.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;

public class FileReader {
    private static String accumulate = null;
    private static boolean ongoingString = false;

    public static Stream<String> getLines(String[] args) {
        return Arrays.stream(args).flatMap(FileReader::FilePathToLines);
    }

    private static Stream<String> FilePathToLines(String path) {
        try {
            return Files.lines(new File(path).toPath())
                    .filter(s -> !s.isEmpty())
                    .map(FileReader::groupStatementsOverLineEndings)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .flatMap(FileReader::splitStatements);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file. Does it exist?", e);
        }
    }

    private static Stream<String> splitStatements(String s) {
        List<String> statements = new ArrayList<>();
        boolean inString = false;
        int previousMatch = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '"'){
                if(s.charAt(i-1) != '\\'){
                    inString = !inString;
                }
            } else if(c == ';'){
                if(!inString){
                    statements.add(s.substring(previousMatch, i+1).trim());
                    previousMatch = i+1;
                }
            }
        }
        return statements.stream();
    }

    private static Optional<String> groupStatementsOverLineEndings(String s) {
        if (accumulate == null) {
            accumulate = s;
        } else {
            accumulate += lineSeparator() + s;
        }
        checkOngongString(s);
        if (s.endsWith(");") && !ongoingString) {
            String response = accumulate;
            accumulate = null;
            return Optional.of(response);
        } else {
            return Optional.empty();
        }
    }

    private static void checkOngongString(String s) {
        if (s.matches(".+\\(@raw@.+") && !s.matches(".+[^\\\\]?@endraw@")) {
            ongoingString = true;
        } else if (s.matches(".+[^\\\\]?@endraw@")) {
            ongoingString = false;
        }
    }
}
