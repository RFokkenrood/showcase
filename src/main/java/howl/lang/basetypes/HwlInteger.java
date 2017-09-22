package howl.lang.basetypes;


import howl.lang.parser.HwlNumber;
import howl.lang.parser.statements.Argument;

import static java.lang.Math.pow;

public class HwlInteger implements Argument<Long>, HwlNumber<HwlInteger> {

    private final long value;

    private HwlInteger(long number) {
        value = number;
    }

    public HwlInteger(int value) {
        this.value = value;
    }

    public Long value() {
        return value;
    }

    @Override
    public HwlBoolean hwlEquals(Argument other) {
        return new HwlBoolean(other.value().equals(value));
    }

    @Override
    public HwlString hwlToString() {
        return new HwlString("" + value);
    }

    public HwlInteger plus(HwlInteger otherNumber) {
        return new HwlInteger(value + otherNumber.value);
    }

    public HwlInteger minus(HwlInteger otherNumber) {
        return new HwlInteger(value - otherNumber.value);
    }

    public HwlInteger times(HwlInteger otherNumber) {
        return new HwlInteger(value * otherNumber.value);
    }

    public HwlInteger over(HwlInteger otherNumber) {
        return new HwlInteger(value / otherNumber.value);
    }

    public HwlInteger modulo(HwlInteger otherNumber) {
        return new HwlInteger(value % otherNumber.value);
    }

    public HwlInteger power(HwlInteger otherNumber) {
        return new HwlInteger((long) pow(value, otherNumber.value));
    }
}

