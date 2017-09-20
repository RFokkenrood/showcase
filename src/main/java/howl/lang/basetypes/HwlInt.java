package howl.lang.basetypes;


import howl.lang.parser.statements.Argument;

import static java.lang.Math.pow;

public class HwlInt implements Argument<Long> {

    private final long value;

    private HwlInt(long number) {
        value = number;
    }

    public HwlInt(int value) {
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

    public HwlInt plus(HwlInt otherNumber) {
        return new HwlInt(value + otherNumber.value);
    }

    public HwlInt minus(HwlInt otherNumber) {
        return new HwlInt(value - otherNumber.value);
    }

    public HwlInt times(HwlInt otherNumber) {
        return new HwlInt(value * otherNumber.value);
    }

    public HwlInt over(HwlInt otherNumber) {
        return new HwlInt(value / otherNumber.value);
    }

    public HwlInt modulo(HwlInt otherNumber) {
        return new HwlInt(value % otherNumber.value);
    }

    public HwlInt power(HwlInt otherNumber) {
        return new HwlInt((long) pow(value, otherNumber.value));
    }
}

