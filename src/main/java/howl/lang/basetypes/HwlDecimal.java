package howl.lang.basetypes;

import howl.lang.parser.statements.Argument;

import java.math.BigDecimal;

public class HwlDecimal implements Argument<BigDecimal>{
    private final BigDecimal value;
    public HwlDecimal(String argumentString) {
        value = new BigDecimal(argumentString);
    }

    @Override
    public BigDecimal value() {
        return value;
    }

    @Override
    public HwlBoolean hwlEquals(Argument hwlDecimal) {
        return new HwlBoolean(value.equals(hwlDecimal.value()));
    }

    @Override
    public HwlString hwlToString() {
        return new HwlString(value.toString());
    }
}
