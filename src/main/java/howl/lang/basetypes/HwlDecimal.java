package howl.lang.basetypes;

import howl.lang.parser.HwlNumber;
import howl.lang.parser.statements.Argument;

import java.math.BigDecimal;

public class HwlDecimal implements Argument<BigDecimal>,HwlNumber<HwlDecimal> {
    private final BigDecimal value;

    public HwlDecimal(BigDecimal bigDecimal) {
        value = bigDecimal;
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

    @Override
    public HwlDecimal times(HwlDecimal hwlDec) {
        return new HwlDecimal(value.multiply(hwlDec.value));
    }

    @Override
    public HwlDecimal over(HwlDecimal aFor) {
        return null;
    }

    @Override
    public HwlDecimal modulo(HwlDecimal aFor) {
        return null;
    }

    @Override
    public HwlDecimal power(HwlDecimal aFor) {
        return null;
    }

    @Override
    public HwlDecimal minus(HwlDecimal hwlDec) {
        return new HwlDecimal(value.subtract(hwlDec.value));
    }

    @Override
    public HwlDecimal plus(HwlDecimal hwlDec) {
        return new HwlDecimal(value.add(hwlDec.value));
    }
}
