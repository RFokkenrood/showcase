package howl.lang.basetypes;

import howl.lang.parser.statements.Argument;

import static java.util.Arrays.stream;

public class HwlString implements Argument<String> {
    private final String value;

    public HwlString(String argumentString) {
        if (argumentString.startsWith("@raw@")) {
            value = parseFromRaw(argumentString);
        } else {
            String quotationType = getQuotationType(argumentString);
            if (quotationType != null) {
                value = parseFromQuoted(argumentString, quotationType);
            } else {
                value = argumentString;
            }
        }
    }

    private String parseFromQuoted(String argumentString, String quotationType) {
        return argumentString
                .replace(quotationType, "")
                .replace("\\", quotationType);
    }

    private String parseFromRaw(String argumentString) {
        return argumentString.substring(5, argumentString.length()-8).replace("\\@endraw@", "@endraw@");
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public HwlBoolean hwlEquals(Argument other) {
        return new HwlBoolean(value.equals(other.value()));
    }

    @Override
    public HwlString hwlToString() {
        return new HwlString(value);
    }

    private String getQuotationType(String string) {
        return stream(new String[]{"\"", "'"})
                .filter(string::startsWith)
                .findFirst()
                .orElse(null);
    }
}
