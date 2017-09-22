package howl.lang.parser;

public interface HwlNumber<T> {
    T plus(T number);

    T minus(T aFor);

    T times(T aFor);

    T over(T aFor);

    T modulo(T aFor);

    T power(T aFor);
}
