package howl.lang.basetypes;

import howl.lang.parser.statements.Argument;

public class HwlUndefined implements Argument<Object>{
    @Override
    public Object value() {
        return null;
    }

    @Override
    public HwlBoolean hwlEquals(Argument other) {
        return new HwlBoolean(other instanceof HwlUndefined);
    }

    @Override
    public HwlString hwlToString() {
        return new HwlString("undefined");
    }
}
