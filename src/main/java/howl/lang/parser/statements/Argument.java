package howl.lang.parser.statements;

import howl.lang.basetypes.HwlBoolean;
import howl.lang.basetypes.HwlString;

public interface Argument<JavaType> {
    JavaType value();

    HwlBoolean hwlEquals(Argument hwlType);

    HwlString hwlToString();
}