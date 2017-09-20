package howl.lang.basetypes;

import howl.lang.parser.statements.Argument;

public class HwlBoolean implements Argument<Boolean> {
    private final boolean value;

    public HwlBoolean(boolean bool) {
        value = bool;
    }

    @Override
    public Boolean value() {
        return value;
    }

    @Override
    public HwlBoolean hwlEquals(Argument bool) {
        return new HwlBoolean(value == ((HwlBoolean) bool).value());
    }

    @Override
    public HwlString hwlToString() {
        return new HwlString("" + value);
    }

    public HwlBoolean and(HwlBoolean other) {
        return new HwlBoolean(value && other.value);
    }

    public HwlBoolean or(HwlBoolean other) {
        return new HwlBoolean(value || other.value);
    }

    public HwlBoolean not() {
        return new HwlBoolean(!value);
    }

}
