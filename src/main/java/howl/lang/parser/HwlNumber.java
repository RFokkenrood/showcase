package howl.lang.parser;

import howl.lang.basetypes.HwlInteger;

public interface HwlNumber<T> {
    T plus(T number);

    T minus(T number);

    T times(T number);

    T over(T number);

    T modulo(T number);

    T power(HwlInteger integer);
}
