package howl.lang.basetypes;

import howl.lang.parser.statements.Argument;

import static java.util.Arrays.stream;

public class HwlString implements Argument<String>{
    private final String value;

    public HwlString(String argumentString) {

        String quotationType = getQuotationType(argumentString);
        if (quotationType != null){
            value = argumentString
                    .replace(quotationType, "")
                    .replace("\\", quotationType);
        } else {
            value = argumentString;
        }
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

    private String getQuotationType(String string){
        return stream(new String[]{"\"", "'"})
                .filter(string::startsWith)
                .findFirst()
                .orElse(null);
    }
}
